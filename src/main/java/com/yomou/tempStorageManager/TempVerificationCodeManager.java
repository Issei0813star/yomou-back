package com.yomou.tempStorageManager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class TempVerificationCodeManager {
    //Map<userId, <認証コード, 有効期限>>
    private final ConcurrentHashMap<Long, Map<String, Instant>> tempUserVerificationCodeMap = new ConcurrentHashMap<>();

    /**
     * userIdを渡すと、認証コードを生成or取得して返す
     * @param userId Long
     * @return String
     */
    public String getTempVerificationCode(Long userId) {
        Map<String, Instant> verificationCodeMap =  this.tempUserVerificationCodeMap.computeIfAbsent(userId, id -> createVerificationCodeMap());

        return verificationCodeMap.keySet().stream().findFirst().orElse("");
    }

    private Map<String, Instant> createVerificationCodeMap() {
        return Map.of(createVerificationCode(), getExpiration());
    }

    private String createVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    private Instant getExpiration () {
        Instant now = Instant.now();
        return now.plus(Duration.ofMinutes(30));
    }
}
