package com.api.ttoklip.global.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FCMConfig {

    @Qualifier("jasyptStringEncryptor")
    private final StringEncryptor stringEncryptor;

    @Value("${google.firebase.fcm.resource.path}")
    private String encryptedJsonContent;

    public FCMConfig(@Qualifier("jasyptStringEncryptor") StringEncryptor stringEncryptor) {
        this.stringEncryptor = stringEncryptor;
    }

    @PostConstruct
    public void firebaseMessaging() throws IOException {
        try {
            log.info("Encrypted JSON Content Path: " + encryptedJsonContent); // 디버깅 로그 추가

            // JSON 파일 읽기
            Resource resource = new ClassPathResource(encryptedJsonContent);
            InputStream inputStream = resource.getInputStream();
            byte[] jsonData = inputStream.readAllBytes();
            String jsonString = new String(jsonData);

            log.info("--------- flag1 ---------");
            log.info("JSON String: " + jsonString); // 디버깅 로그 추가

            // Jackson ObjectMapper 사용
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            // JSON 필드 각각을 복호화
            decryptJsonFields(jsonNode);

            String decryptedJsonContent = objectMapper.writeValueAsString(jsonNode);
            log.info("Decrypted JSON Content: " + decryptedJsonContent); // 디버깅 로그 추가

            log.info("--------- flag4 ---------");
            InputStream serviceAccount = new ByteArrayInputStream(decryptedJsonContent.getBytes());

            log.info("--------- flag5 ---------");
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(serviceAccount);

            log.info("--------- flag6 ---------");
            FirebaseOptions options = FirebaseOptions.builder().setCredentials(googleCredentials)
                    .setProjectId("ttoklip").build();

            log.info("--------- flag7 ---------");
            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace(); // 디버깅을 위해 스택 트레이스 출력
            throw e; // 예외 다시 던지기
        }
    }

    private void decryptJsonFields(JsonNode jsonNode) {
        if (jsonNode.isObject()) {
            jsonNode.fields().forEachRemaining(entry -> {
                JsonNode value = entry.getValue();
                if (value.isTextual() && value.asText().startsWith("ENC(") && value.asText().endsWith(")")) {
                    String encryptedValue = value.asText();
                    String cleanedEncryptedValue = encryptedValue.replaceAll("ENC\\(", "").replaceAll("\\)", "");
                    String decryptedValue = stringEncryptor.decrypt(cleanedEncryptedValue);
                    ((ObjectNode) jsonNode).put(entry.getKey(), decryptedValue);
                } else {
                    decryptJsonFields(value);
                }
            });
        } else if (jsonNode.isArray()) {
            for (JsonNode arrayElement : jsonNode) {
                decryptJsonFields(arrayElement);
            }
        }
    }
}
