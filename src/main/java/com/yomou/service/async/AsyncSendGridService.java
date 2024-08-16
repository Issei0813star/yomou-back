package com.yomou.service.async;

import com.yomou.entity.UserEntity;
import com.yomou.service.SendGridService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AsyncSendGridService {

    private final SendGridService sendGridService;

    public CompletableFuture<Void> asyncSendEmail(UserEntity user) {
        return CompletableFuture.runAsync(() -> sendGridService.sendEmail(user));
    }
}
