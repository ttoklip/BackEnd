//package com.api.ttoklip.global.distributed;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.file.Files;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//public class LocalSignupApiTest {
//
//    //    private static final String REQUEST_URL = "https://toychip.click/api/v1/auth/signup";
//    private static final String REQUEST_URL = "http://localhost:8080/api/v1/auth/signup";
//    private static final int THREAD_COUNT = 5;
//
//    public static void main(String[] args) throws InterruptedException {
//        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
//
//        for (int i = 0; i < THREAD_COUNT; i++) {
//            executorService.submit(() -> {
//                try {
//                    sendSignupRequest();
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
//    private static void sendSignupRequest() throws IOException {
//        URL url = new URL(REQUEST_URL);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("POST");
//        connection.setDoOutput(true);
//        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=boundary");
//
//        String boundary = "boundary";
//        String profileImageUrl = "실제사진경로";
//        String body = "--" + boundary + "\r\n" +
//                "Content-Disposition: form-data; name=\"email\"\r\n\r\n" +
//                "test@gmail.com\r\n" +
//                "--" + boundary + "\r\n" +
//                "Content-Disposition: form-data; name=\"password\"\r\n\r\n" +
//                "password\r\n" +
//                "--" + boundary + "\r\n" +
//                "Content-Disposition: form-data; name=\"originName\"\r\n\r\n" +
//                "originName\r\n" +
//                "--" + boundary + "\r\n" +
//                "Content-Disposition: form-data; name=\"nickname\"\r\n\r\n" +
//                "nickname\r\n" +
//                "--" + boundary + "\r\n" +
//                "Content-Disposition: form-data; name=\"independentYear\"\r\n\r\n" +
//                "1\r\n" +
//                "--" + boundary + "\r\n" +
//                "Content-Disposition: form-data; name=\"independentMonth\"\r\n\r\n" +
//                "2\r\n" +
//                "--" + boundary + "\r\n" +
//                "Content-Disposition: form-data; name=\"street\"\r\n\r\n" +
//                "주소\r\n" +
//                "--" + boundary + "\r\n" +
//                "Content-Disposition: form-data; name=\"agreeTermsOfService\"\r\n\r\n" +
//                "true\r\n" +
//                "--" + boundary + "\r\n" +
//                "Content-Disposition: form-data; name=\"agreePrivacyPolicy\"\r\n\r\n" +
//                "true\r\n" +
//                "--" + boundary + "\r\n" +
//                "Content-Disposition: form-data; name=\"agreeLocationService\"\r\n\r\n" +
//                "true\r\n" +
//                "--" + boundary + "\r\n" +
//                "Content-Disposition: form-data; name=\"categories\"\r\n\r\n" +
//                "HOUSEWORK\r\n" +
//                "--" + boundary + "\r\n" +
//                "Content-Disposition: form-data; name=\"profileImage\"; filename=\"image.png\"\r\n" +
//                "Content-Type: image/png\r\n\r\n" +
//                new String(Files.readAllBytes(
//                        new File(profileImageUrl).toPath())) +
//                "\r\n--" + boundary + "--\r\n";
//
//        connection.getOutputStream().write(body.getBytes());
//        connection.getOutputStream().flush();
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
//
//}
