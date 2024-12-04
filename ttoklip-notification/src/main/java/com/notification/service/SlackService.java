package com.notification.service;

import com.notification.consumer.ErrorMessage;
import org.springframework.stereotype.Service;

@Service
public class SlackService {

    // ToDo 추후 구현
    public void sendErrorMessage(final ErrorMessage errorMessage) {
        if (errorMessage.isSyncError()) {

        } else {

        }
    }
}
