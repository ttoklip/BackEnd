package com.api.ttoklip.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class FCMConfig {

    @PostConstruct
    public void firebaseMessaging() throws IOException {
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(getCredential()))
                .setProjectId("ttoklip")
                .build();

        FirebaseApp.initializeApp(options);
    }

    private InputStream getCredential() throws IOException {
        return new ClassPathResource("firebase/ttoklip-firebase-adminsdk-trxhp-c7534ab7b1.json").getInputStream();
    }

}
