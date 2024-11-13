package com.api.email.infrastructure;

import com.api.email.application.Sender;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SenderImpl implements Sender {

    private final JavaMailSender sender;

    @Override
    public void send(final MimeMessage mimeMessage) {
        sender.send(mimeMessage);
    }

    @Override
    public MimeMessage createMimeMessage() {
        return sender.createMimeMessage();
    }
}
