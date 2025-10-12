package clinicmangement.com.We_Care.ai_model;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/we-care-ai-model")
@RequiredArgsConstructor
public class OllamaChatController {

    private final OllamaChatService ollamaChatService;


    @GetMapping("/chat")
    public ResponseEntity<Map<String, Object>> chatWithDeepSeek(@RequestParam (name = "message") String message){

        if(!ollamaChatService.isMedicalWord(message)){
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", message);
            jsonResponse.put("answer", "I’m sorry, I don’t know. I can only answer medical-related questions.");

            return ResponseEntity.ok(jsonResponse);
        }

        String systemPrompt = """
          You are a medical assistant. 
          You must only answer medical-related questions (e.g., diseases, symptoms, medications, anatomy, healthcare).
          If the user asks anything not related to medicine, reply exactly with:
          "I’m sorry, I don’t know. I can only answer medical-related questions."
                """;

          String fullPrompt = systemPrompt + "\n\nUser: " + message;
          String response = ollamaChatService.askDeepSeek(message);

        //removing think tags
        String regex = "(?s)<think>.*?</think>";
        String cleanedResponse = response.replaceAll(regex, "").trim();


        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("message", message);
        jsonResponse.put("answer", cleanedResponse);


        return ResponseEntity.ok(jsonResponse);

    }

//@GetMapping(value = "/chat", produces = MediaType.APPLICATION_NDJSON_VALUE)
public Flux<Map<String, String>> askDeepSeekWithStream(@RequestParam String question){

    String regexThink = "(?s)<think>.*?</think>";

    return ollamaChatService.askDeepSeekWithStream(question)
            .map(chunk-> chunk.replaceAll(regexThink, "").trim())
            .filter(cleaned-> !cleaned.isBlank())
            .map(cleaned-> {
                Map<String, String>json = new HashMap<>();
                json.put("question", question);
                json.put("answer", cleaned);

                return json;
            });
}


}
