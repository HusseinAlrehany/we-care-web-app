package clinicmangement.com.We_Care.firebase.firebaseconfig;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Configuration
public class FireBaseConfig {

@PostConstruct
public void init() throws IOException {
    FileInputStream  serviceAccount =new FileInputStream(
            "src/main/resources/we-care-app-c1cef-firebase-adminsdk-fbsvc-0b8a50f521.json");

    FirebaseOptions options =FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

    if(FirebaseApp.getApps().isEmpty()){
         FirebaseApp.initializeApp(options);
         System.out.println("FireBase Started Successfully");
    }



}
}
