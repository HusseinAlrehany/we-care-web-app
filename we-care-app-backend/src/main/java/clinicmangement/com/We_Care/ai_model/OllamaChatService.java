package clinicmangement.com.We_Care.ai_model;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

@Service
public class OllamaChatService {


    private final ChatClient chatClient;
    private final Set<String> medicalKeywords = new HashSet<>();

    public OllamaChatService(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @PostConstruct
    public void readMedicalKeyWords(){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
             getClass().getResourceAsStream("/medicalkeywords.txt"),
                StandardCharsets.UTF_8
        ))){

            String line;
            while((line = reader.readLine())!= null){
                 medicalKeywords.add(line.trim().toLowerCase());
            }

            System.out.println("number of words read" + medicalKeywords.size());

        }catch(Exception ex){
            throw new RuntimeException("Failed to read medical keywords file", ex);
        }
    }

    public boolean isMedicalWord(String question){
        if(question == null || question.isBlank()) return  false;

        String questionLower = question.toLowerCase();

       return medicalKeywords.stream().anyMatch(questionLower::contains);
    }

    public String askDeepSeek(String message) {

        return chatClient.prompt(message).call().content();
    }


    public Flux<String> askDeepSeekWithStream(String question){
        return chatClient.prompt(question)
                .stream()
                .content();
    }

    public String cleanedResponse(String response) {

        if(response == null) return "";

        String cleaned = response.replaceAll("(?s)<think>.*?</think>", "");

        //Remove any tags
        cleaned = cleaned.replaceAll("<[^>]+>", "");

        //Remove excessive newlines, tabs, spaces
        cleaned = cleaned.replaceAll("\\s{2,}", " ");  // collapse multiple spaces
        cleaned = cleaned.replaceAll("(\\n|\\r)+", " "); // remove newlines

        //Trim and normalize spaces
        return cleaned.trim();
    }
}
