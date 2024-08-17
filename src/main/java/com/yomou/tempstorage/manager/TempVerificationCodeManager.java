package com.yomou.tempstorage.manager;

import com.yomou.tempstorage.dto.VerificationCodeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class TempVerificationCodeManager {
    //Map<userId, 認証コード>
    private final ConcurrentHashMap<Long, VerificationCodeDto> tempUserVerificationCodeMap = new ConcurrentHashMap<>();

    /**
     * userIdを渡すと、認証コードを生成して返す。すでにある場合はコードは再生成され、新しいものになる
     * @param userId Long
     * @return String
     */
    public String getVerificationCode(Long userId) {
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
    public boolean verifyCode(String verificationCode, Long userId) {
        VerificationCodeDto dto = this.tempUserVerificationCodeMap.get(userId);
        if(Objects.isNull(dto)) {
            return false;
        }

        Instant expiration = dto.getExpiration();

        if(Objects.isNull(expiration)) {
            return false;
        }

        return expiration.isAfter(Instant.now());
    }

    public void removeCode(Long userId) {
        this.tempUserVerificationCodeMap.remove(userId);
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

    @Scheduled(fixedRate = 1800000)
    private void removeCodeScheduled() {
        Instant now = Instant.now();
        this.tempUserVerificationCodeMap.entrySet().removeIf(entry -> now.isAfter(entry.getValue().getExpiration()));
    }
}
