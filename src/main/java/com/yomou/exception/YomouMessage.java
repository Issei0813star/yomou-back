package com.yomou.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum YomouMessage {

    USER_NOT_FOUND("ユーザーが存在しません。", HttpStatus.NOT_FOUND),
    INCORRECT_PASSWORD("パスワードが間違っています。", HttpStatus.UNAUTHORIZED),
    EMAIL_NOT_UNIQUE("指定されたメールアドレスは既に登録されています。", HttpStatus.CONFLICT),
    USER_NAME_NOT_UNIQUE("指定されたユーザー名は既に存在します。別のユーザー名で登録してください。", HttpStatus.CONFLICT),
    USER_NOT_VERIFIED("メールアドレス認証がされていないユーザーです。メールアドレス認証後に再度ログインしてください。", HttpStatus.UNAUTHORIZED),
    VERIFICATION_CODE_ARE_INVALID("認証コードが正しくありません。", HttpStatus.UNAUTHORIZED),
    SEND_MAIL_FAILURE("メールの送信に失敗しました。", HttpStatus.INTERNAL_SERVER_ERROR);

    private String message;
    private HttpStatus status;

    private YomouMessage(String message, HttpStatus status){
        this.message = message;
        this.status = status;
    }

}
