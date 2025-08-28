package clinicmangement.com.We_Care.ai_model;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/we-care-ai-model")
public class CohereModelController {


    private final CohereClient cohereClient;
    private final Map<String, List<Map<String, String>>> chatHistories = new HashMap<>();

    public CohereModelController(CohereClient cohereClient) {
        this.cohereClient = cohereClient;
    }

    @GetMapping("/chat")
    public ResponseEntity<Map<String, Object>> suggestRecipe(
            @RequestParam(
                    name = "message",
                    defaultValue = "suggest medical information"
            ) String message, @RequestParam(defaultValue = "default-session") String sessionId) throws JsonProcessingException {

        chatHistories.putIfAbsent(sessionId, new ArrayList<>());
        List<Map<String, String>> history = chatHistories.get(sessionId);

        // Add user message to history
        history.add(Map.of("role", "USER", "message", message));

        // Call Cohere and get full JSON response
        Map<String, Object> fullResponse = cohereClient.generateRawResponseWithMemory(message, history);

        // Extract fields
        String responseId = (String) fullResponse.get("response_id");
        String text = (String) fullResponse.get("text");

        // Clean the text (remove \n\n)
        if (text != null) {
            text = text.replace("\n\n", " ").replace("\n", " "); // replace with space
            history.add(Map.of("role", "CHATBOT", "message", text));
        }

        // Return only response_id and cleaned text
        Map<String, Object> simplifiedResponse = new HashMap<>();
        simplifiedResponse.put("response_id", responseId);
        simplifiedResponse.put("text", text);

        return ResponseEntity.ok(simplifiedResponse);
    }

}
