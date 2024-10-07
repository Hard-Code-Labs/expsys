package ec.com.expensys.web.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    ALREADY_EXIST(4001,"Value already exists."),
    BAD_ARGUMENT(4002,"Value is not valid."),
    DECRYPT_ERROR(4003,"Error on decrypt using private key."),
    NOT_FOUND(4004,"Object not found on database."),
    SEND_MAIL_ERROR(4005,"Error on sending mail."),;

    private final int code;
    private final String messageTemplate;

    ErrorCode(int code, String messageTemplate) {
        this.code = code;
        this.messageTemplate = messageTemplate;
    }
}
