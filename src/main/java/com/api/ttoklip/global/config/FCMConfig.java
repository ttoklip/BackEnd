package com.api.ttoklip.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FCMConfig {

    @Value("${firebase.config.path:/app/config/firebase-config.json}")
    private String firebaseConfigPath;

    @PostConstruct
    public void init() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(firebaseConfigPath);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        FirebaseApp.initializeApp(options);
    }
}
