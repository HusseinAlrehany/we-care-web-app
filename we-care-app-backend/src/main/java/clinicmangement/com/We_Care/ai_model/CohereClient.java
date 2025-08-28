package clinicmangement.com.We_Care.ai_model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CohereClient {


    private final WebClient webClient;


    // In-memory session-to-history map
    private final Map<String, List<Map<String, String>>> sessionHistories = new HashMap<>();

    private static final String SYSTEM_MESSAGE = "Suggest Medical information. " +
            "If someone asks about anything else, just say 'I don't know'." +
            "do not say anything else at all even if the user intend to send many requests out of provided context";

    public CohereClient(@Value("${cohere.api-key}") String apiKey) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.cohere.ai/v1")
                .defaultHeader(org.springframework.http.HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        System.out.println("Loaded API Key: " + apiKey);

    }

    public String generateResponseWithMemory(String userMessage, List<Map<String, String>> chatHistory) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "command-r");
        requestBody.put("temperature", 0.7);
        requestBody.put("preamble", "You are a recipe assistant. Only suggest seafood recipes. If someone asks anything else, say: 'I don't know.'");

        requestBody.put("message", userMessage); // latest message

        // Transform chat history to match Cohere’s expected format
        List<Map<String, String>> formattedHistory = chatHistory.stream()
                .map(entry -> Map.of(
                        "role", entry.get("role").toUpperCase(),   // USER / CHATBOT
                        "message", entry.get("message")
                ))
                .collect(Collectors.toList());

        requestBody.put("chat_history", formattedHistory);

        try {
            return webClient.post()
                    .uri("/chat")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .map(response -> {
                        Object text = response.get("text");
                        if (text == null) text = response.get("response");
                        return text != null ? text.toString() : "Empty response from Cohere.";
                    })
                    .onErrorReturn("Error calling Cohere API.")
                    .block();
        } catch (WebClientResponseException e) {
            System.err.println("Error calling Cohere API: " + e.getResponseBodyAsString());
            return "Error calling Cohere API: " + e.getMessage();
        }
    }

    public String generateResponse(String prompt) {
        String systemMessage = "Suggest seafood recipes. " +
                "If someone asks about something else, just say 'I don't know'.";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("message", prompt);
        requestBody.put("model", "command-r");
        requestBody.put("temperature", 0.7);
        requestBody.put("chat_history", List.of(
                Map.of("role", "SYSTEM", "message", systemMessage)
        ));

        return webClient.post()
                .uri("/chat")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    Object text = response.get("text");
                    if (text == null) {
                        text = response.get("response");
                    }
                    return text != null ? text.toString() : "Empty response from Cohere.";
                })
                .onErrorReturn("Error calling Cohere API.")
                .block();
    }

    public Map<String, Object> generateRawResponseWithMemory(String userMessage, List<Map<String, String>> chatHistory) throws JsonProcessingException {
        if (userMessage == null || userMessage.isBlank()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "command-r");
        requestBody.put("temperature", 0.7);
        requestBody.put("preamble", SYSTEM_MESSAGE);
        requestBody.put("message", userMessage);

        List<Map<String, String>> formattedHistory = chatHistory != null
                ? chatHistory.stream()
                .map(entry -> Map.of(
                        "role", entry.get("role").toUpperCase(),
                        "message", entry.get("message")
                ))
                .collect(Collectors.toList())
                : List.of();

        requestBody.put("chat_history", formattedHistory);

        System.out.println("Sending to Cohere: " + new ObjectMapper().writeValueAsString(requestBody));
        try {
            return webClient.post()
                    .uri("/chat")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .onErrorResume(error -> {
                        Map<String, Object> errorMap = new HashMap<>();
                        errorMap.put("error", "Cohere API error");
                        errorMap.put("details", error.getMessage());
                        return Mono.just(errorMap);
                    })
                    .block();
        } catch (WebClientResponseException e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("error", "HTTP error");
            errorMap.put("details", e.getResponseBodyAsString());
            return errorMap;
        }
    }

}
