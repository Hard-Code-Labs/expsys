package ec.com.expensys.service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import ec.com.expensys.web.exception.ErrorCode;
import ec.com.expensys.web.exception.MailSenderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    //TODO pasar a variable de entorno
    Resend resend = new Resend("re_PM918hcR_ENDdp39xHcq5vT8snjtU2cCN");

    public void sendEmail(String to, String subject, String text) {
        try{
            CreateEmailOptions emailRequest = CreateEmailOptions.builder()
                    .from("Acme <onboarding@resend.dev>")
                    .to("hardcode.labs.v1@gmail.com")//para pruebas solo se puede enviar al correo de mi cuenta en resend
                    .html(text)
                    .subject(subject)
                    .build();

            CreateEmailResponse mr = resend.emails().send(emailRequest);
            log.debug("Mail send: {}", mr.getId());

        }catch (ResendException e){
            throw new MailSenderException(ErrorCode.SEND_MAIL_ERROR.getCode(),
                    "Email could not be sent using the Resend API.",
                    EmailService.class.getName(),
                    e.getMessage());
        }
    }
}
