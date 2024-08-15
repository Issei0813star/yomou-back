package com.yomou.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncSendGridService {

    @Value("${spring.sendgrid.api-key}")
    private String sendGridApiKey;
    private SendGrid sendGrid;

    public AsyncSendGridService(@Value("${spring.sendgrid.api-key}") String sendGridApiKey) {
        this.sendGrid = new SendGrid(sendGridApiKey);
    }

    public CompletableFuture<Void> asyncSendEmail(String destination) {
        return CompletableFuture.runAsync(() -> {
            //TODO 送信元メールアドレスを本番用に
            Email from = new Email("issei0813star.dev@gmail.com");
            String subject = "メールアドレス認証";
            Email to = new Email(destination);
            String verificationCode = createVerificationCode();
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
        });
    }

    private String createVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }


}
