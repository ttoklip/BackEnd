package com.api.ttoklip.domain.email.service;

import com.api.ttoklip.domain.email.dto.request.EmailVerifyRequest;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.util.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;
    private final SpringTemplateEngine templateEngine;

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

    private String setContext(String authCode) {
        Context context = new Context();
        context.setVariable("authCode", authCode);
        return templateEngine.process("mail", context);
    }

    // 이메일 폼 생성
    private MimeMessage createEmailForm(String email) {
        String authCode = createCode();

        MimeMessage message;
        try {
            message = javaMailSender.createMimeMessage();
            message.addRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("안녕하세요. 똑립 인증 번호입니다.");
            message.setFrom(new InternetAddress(senderEmail, "똑립"));
            message.setText(setContext(authCode), "utf-8", "html");
        } catch (MessagingException e) {
            log.error("MessagingException while creating email form: {}", e.getMessage(), e);
            throw new ApiException(ErrorType.EMAIL_FORM_CREATION_ERROR);
        } catch (IOException e) {
            log.error("IOException while creating email form: {}", e.getMessage(), e);
            throw new ApiException(ErrorType.EMAIL_SENDING_ERROR);
        }

        // Redis 에 해당 인증코드 인증 시간 설정
        setRedisData(email, authCode);

        return message;
    }


    private void setRedisData(final String email, final String authCode) {
        try {
            redisUtil.setDataExpire(email, authCode, 60 * 30L);
        } catch (Exception e) {
            log.error("Failed to set Redis data: {}", e.getMessage(), e);
            throw new ApiException(ErrorType.REDIS_SAVE_ERROR);
        }
    }

    @Async
    // 인증코드 이메일 발송
    public void sendEmail(String toEmail) {
        try {
            validEmailHasText(toEmail);
            validRedisHasEmail(toEmail);

            // 이메일 폼 생성
            createEmail(toEmail);
        } catch (Exception e) {
            log.error("Exception in sendEmail: {}", e.getMessage(), e);
            throw e;
        }
    }

    private void validRedisHasEmail(final String toEmail) {
        if (redisUtil.existData(toEmail)) {
            redisUtil.deleteData(toEmail);
        }
    }

    private void createEmail(final String toEmail) {
        try {
            MimeMessage emailForm = createEmailForm(toEmail);
            sendEmail(emailForm);
        } catch (ApiException e) {
            log.error("ApiException during email creation: {}", e.getMessage(), e);
            throw e; // 이미 ApiException을 던지므로 다시 던짐
        } catch (Exception e) {
            log.error("Unexpected error during email creation: {}", e.getMessage(), e);
            throw new ApiException(ErrorType.EMAIL_SENDING_ERROR);
        }
    }

    private void sendEmail(final MimeMessage emailForm) {
        try {
            // 이메일 발송
            javaMailSender.send(emailForm);
        } catch (Exception e) {
            log.error("Failed to send email: {}", e.getMessage(), e);
            throw new ApiException(ErrorType.EMAIL_SENDING_ERROR);
        }
    }

    // 코드 검증
    public void verifyEmailCode(EmailVerifyRequest request) {
        validEmailHasText(request.getEmail());
        validCode(request.getEmail(), request.getVerifyCode());
    }

    private void validEmailHasText(String email) {
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
            log.error("Failed to retrieve data from Redis: {}", e.getMessage(), e);
            throw new ApiException(ErrorType.REDIS_EMAIL_NOT_FOUND);
        }
    }

    private void validAuthenticationCode(String code, String codeFoundByEmail) {
        if (!codeFoundByEmail.equals(code)) {
            throw new ApiException(ErrorType.INVALID_MAIL_CODE);
        }
    }
}
