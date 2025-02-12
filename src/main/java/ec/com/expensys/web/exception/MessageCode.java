package ec.com.expensys.web.exception;

import lombok.Getter;

@Getter
public enum MessageCode {

    ALREADY_EXIST(4001,"Value already exists."),
    BAD_ARGUMENT(4002,"Value is not valid."),
    DECRYPT_ERROR(4003,"Error on decrypt using private key."),
    NOT_FOUND(4004,"Object not found on database."),
    SEND_MAIL_ERROR(4005,"Error on sending mail."),
    TOKEN_EXPIRED(4006,"Token expired."),
    TOKEN_INVALID(4007,"Error on processing token."),
    UNAUTHORIZED(4008,"Not authorized."),
    FORBIDDEN(4009,"Access denied."),
    CREATED(2001,"Created."),
    SUCCESS(2000,"Success."),
    SERVER_ERROR(5000,"Server internal error."),
    PERSIST_ERROR(5001,"Database persist error."),;

    private final int code;
    private final String messageTemplate;

    MessageCode(int code, String messageTemplate) {
        this.code = code;
        this.messageTemplate = messageTemplate;
    }
}
