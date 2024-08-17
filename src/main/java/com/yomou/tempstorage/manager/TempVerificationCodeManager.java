package com.yomou.tempstorage.manager;

import com.yomou.tempstorage.dto.VerificationCodeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class TempVerificationCodeManager {
    // Map<userId, 認証コード>
    private final ConcurrentHashMap<Long, VerificationCodeDto> tempUserVerificationCodeMap = new ConcurrentHashMap<>();

    /**
     * userIdを渡すと、認証コードを生成して返す。すでにある場合はコードは再生成され、新しいものになる
     * @param userId Long
     * @return String
     */
    public synchronized String getVerificationCode(Long userId) {
        String verificationCode = this.createVerificationCode();
        VerificationCodeDto dto = new VerificationCodeDto(verificationCode, this.getExpiration());
        this.tempUserVerificationCodeMap.put(userId, dto);
        return verificationCode;
    }

    /**
     * 認証コードが正しいかをチェック
     * @param verificationCode String
     * @param userId Long
     * @return boolean
     */
    public synchronized boolean verifyCode(String verificationCode, Long userId) {
        VerificationCodeDto dto = this.tempUserVerificationCodeMap.get(userId);
        if (dto == null) {
            return false;
        }
        return verificationCode.equals(dto.getVerificationCode()) && dto.getExpiration().isAfter(Instant.now());
    }

    public synchronized void removeCode(Long userId) {
        this.tempUserVerificationCodeMap.remove(userId);
    }

    private String createVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    private Instant getExpiration() {
        Instant now = Instant.now();
        return now.plus(Duration.ofMinutes(30));
    }

    @Scheduled(fixedRate = 1800000)
    private synchronized void removeCodeScheduled() {
        Instant now = Instant.now();
        this.tempUserVerificationCodeMap.entrySet().removeIf(entry -> now.isAfter(entry.getValue().getExpiration()));
    }
}
