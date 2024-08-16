package com.yomou.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.yomou.entity.UserEntity;
import com.yomou.tempStorageManager.TempVerificationCodeManager;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SendGridService {

    private final SendGrid sendGrid;
    private final TempVerificationCodeManager verificationCodeManager;

    public SendGridService(@Value("${spring.sendgrid.api-key}") String sendGridApiKey, TempVerificationCodeManager verificationCodeManager) {
        this.sendGrid = new SendGrid(sendGridApiKey);
        this.verificationCodeManager = verificationCodeManager;
    }

    public void sendEmail(UserEntity user) {
        //TODO 送信元メールアドレスを本番用に
        Email from = new Email("issei0813star.dev@gmail.com");
        String subject = "メールアドレス認証";
        Email to = new Email(user.getEmail());
        String verificationCode = verificationCodeManager.getTempVerificationCode(user.getId());
        if(StringUtils.isBlank(verificationCode)) {
            //TODO 予期せぬ例外
            return;
        }
        Content content = new Content("text/plain", "認証コード:" + verificationCode);
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sendGrid.api(request);
        }
        catch (IOException ex) {
            //TODO fix exception
            ex.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
