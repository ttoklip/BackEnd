//package com.api.ttoklip.global.distributed;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//public class OauthSignupApiTest {
//
//    private static final String REQUEST_URL = "http://localhost:8080/api/v1/oauth";
//    private static final int THREAD_COUNT = 10;
//
//    public static void main(String[] args) throws InterruptedException {
//        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
//
//        for (int i = 0; i < THREAD_COUNT; i++) {
//            executorService.submit(() -> {
//                try {
//                    sendOAuthRequest();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//
//        executorService.shutdown();
//        executorService.awaitTermination(10, TimeUnit.SECONDS);
//    }
//
//    private static void sendOAuthRequest() throws IOException {
//        URL url = new URL(REQUEST_URL);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("POST");
//        connection.setDoOutput(true);
//        connection.setRequestProperty("Content-Type", "application/json");
//
//        String jsonBody = "{\n" +
//                "    \"accessToken\": \"실제토큰\",\n"
//                +
//                "    \"provider\": \"naver\"\n" +
//                "}";
//
//        try (OutputStream os = connection.getOutputStream()) {
//            byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
//            os.write(input, 0, input.length);
//        }
//
//        int responseCode = connection.getResponseCode();
//        System.out.println("Response Code: " + responseCode);
//
//        // 응답 본문 읽기
//        if (responseCode >= 200 && responseCode < 300) {
//            // 정상 응답
//            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
//                String line;
//                StringBuilder response = new StringBuilder();
//                while ((line = in.readLine()) != null) {
//                    response.append(line);
//                }
//                System.out.println("Response Body: " + response.toString());
//            }
//        } else {
//            // 에러 응답 (500 등)
//            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
//                String line;
//                StringBuilder response = new StringBuilder();
//                while ((line = in.readLine()) != null) {
//                    response.append(line);
//                }
//                System.out.println("Error Body: " + response.toString());
//            }
//        }
//    }
//}
