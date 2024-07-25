package com.api.ttoklip.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FCMConfig {

    @Value("${FIREBASE_CONFIG}")
    private String firebaseConfig;

    @PostConstruct
    public void firebaseMessaging() throws IOException {
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(getCredential()))
                .setProjectId("ttoklip")
                .build();

        FirebaseApp.initializeApp(options);
    }

    private InputStream getCredential() {
        return new ByteArrayInputStream(firebaseConfig.getBytes(StandardCharsets.UTF_8));
    }

}
