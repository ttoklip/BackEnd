package com.api.ttoklip.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FCMConfig {

    @Value("${FIREBASE_CONFIG}")
    private String firebaseConfig;

    private static final String PATH_NAME = "/app/config/firebase-config.json";

    @PostConstruct
    public void init() throws IOException {
        byte[] decodedConfig = Base64.getDecoder().decode(firebaseConfig);

        File configFile = new File(PATH_NAME);
        try (FileOutputStream fos = new FileOutputStream(configFile)) {
            fos.write(decodedConfig);
        }

        FileInputStream serviceAccount = new FileInputStream(configFile);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        FirebaseApp.initializeApp(options);
    }
}
