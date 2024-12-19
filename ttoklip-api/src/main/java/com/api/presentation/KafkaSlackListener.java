//package com.api.presentation;
//
//import com.common.event.Modules;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class KafkaSlackListener {
//
//    private final SlackService slackService;
//
//    // @KafkaListener 애노테이션에서 groupId를 설정함으로써 해당 컨슈머 그룹에 자동으로 가입하게 되고, 지정된 토픽의 메시지를 소비하게 된다.
//
//    @KafkaListener(topics = "${spring.kafka. template. default-topic}", groupId = "${spring.kafka.consumer.group-id}")
//    public void listen(String message) {
//        // 카프카 메시지를 받았을 때 처리
//        log.info("Received message in group error-handler-group: " + message);
//
//        // 메시지를 Slack으로 전송
//        slackService.sendErrorMessage((String title, Throwable e, Modules module));
//    }
//}