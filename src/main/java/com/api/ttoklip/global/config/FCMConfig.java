package com.api.ttoklip.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FCMConfig {

    @Value("${google.firebase.fcm.resource.path}")
    private String resourcePath;

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        ClassPathResource resource = new ClassPathResource(resourcePath);
        try (InputStream refreshToken = resource.getInputStream()) {

            Optional<FirebaseApp> firebaseApp = findFirebaseApp();

            if (firebaseApp.isEmpty()) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(refreshToken))
                        .build();

                firebaseApp = Optional.of(FirebaseApp.initializeApp(options));
            }

            return FirebaseMessaging.getInstance(firebaseApp.get());
        }
    }

    private Optional<FirebaseApp> findFirebaseApp() {
        List<FirebaseApp> firebaseAppList = FirebaseApp.getApps();
        return firebaseAppList.stream()
                .filter(app -> app.getName().equals(FirebaseApp.DEFAULT_APP_NAME))
                .findFirst();
    }
}
