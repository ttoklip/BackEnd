package com.api.email.application;

import jakarta.mail.internet.MimeMessage;

public interface Sender {
    void send(MimeMessage mimeMessage);

    MimeMessage createMimeMessage();
}
