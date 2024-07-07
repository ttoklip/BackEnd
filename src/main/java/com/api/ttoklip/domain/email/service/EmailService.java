package com.api.ttoklip.domain.email.service;

import com.api.ttoklip.domain.email.dto.request.EmailRequest;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.util.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;

    @Value("${spring.mail.sender-email}")
    private String senderEmail;


    private String createCode() {
        int leftLimit = 48; // number '0'
        int rightLimit = 122; // alphabet 'z'
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    // 이메일 폼 생성
    private MimeMessage createEmailForm(String email) throws MessagingException {
        String authCode = createCode();

        MimeMessage message = javaMailSender.createMimeMessage();
//        message.addRecipients(MimeMessage.RecipientType.TO, email);
//        message.setSubject("안녕하세요. 인증번호입니다.");
//        message.setFrom("no-reply@ttoklip.co.kr");
//        message.setText("인증코드: " + authCode, "utf-8");

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject("안녕하세요. 인증번호입니다.");
        helper.setText("인증코드: " + authCode, true); // true indicates the content is HTML
        helper.setFrom("no-reply@ttoklip.co.kr"); // 발신자 이메일 주소 설정

        // Redis 에 해당 인증코드 인증 시간 설정

        try {
            redisUtil.setDataExpire(email, authCode, 60 * 30L);
        } catch (Exception e) {
            log.error("Redis 에 저장하는데 문제가 생겼습니다.");
            e.printStackTrace();
        }

        return message;
    }

    @Async
    // 인증코드 이메일 발송
    public void sendEmail(String toEmail) throws MessagingException {
        if (toEmail == null || toEmail.isEmpty()) {
            throw new ApiException(ErrorType.INVALID_MAIL_TYPE);
        }
        if (redisUtil.existData(toEmail)) {
            redisUtil.deleteData(toEmail);
        }

        // 이메일 폼 생성
        MimeMessage emailForm = createEmailForm(toEmail);

        try {
            // 이메일 발송
            javaMailSender.send(emailForm);
        } catch (Exception e) {
            log.error("이메일을 발송하는데 문제가 생겼습니다.");
            e.printStackTrace();
        }

//        return Message.sendEmail();
    }

    // 코드 검증
    public void verifyEmailCode(EmailRequest request) {
        validEmail(request.getEmail());
        validCode(request.getEmail(), request.getVerifyCode());
    }

    private void validEmail(String email) {
        if (!StringUtils.hasText(email)) {
            throw new ApiException(ErrorType.INVALID_MAIL_TYPE);
        }
    }

    private void validCode(String email, String code) {
        if (!StringUtils.hasText(code)) {
            throw new ApiException(ErrorType.INVALID_MAIL_EMPTY_CODE);
        }

        try {
            String codeFoundByEmail = redisUtil.getData(email);
            log.info("code found by email: " + codeFoundByEmail);
            validAuthenticationCode(code, codeFoundByEmail);
        } catch (Exception e) {
            throw new ApiException(ErrorType.REDIS_EMAIL_NOT_FOUND);
        }
    }

    private void validAuthenticationCode(String code, String codeFoundByEmail) {
        if (!codeFoundByEmail.equals(code)) {
            throw new ApiException(ErrorType.INVALID_MAIL_CODE);
        }
    }
}
