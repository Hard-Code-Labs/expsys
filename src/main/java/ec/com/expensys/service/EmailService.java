package ec.com.expensys.service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import ec.com.expensys.web.exception.MessageCode;
import ec.com.expensys.web.exception.MailSenderException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final FreeMarkerConfigurer configurer;

    @Value("${constant.resendApiKey}")
    private String RESEND_API_KEY;

    private Resend resend;

    @PostConstruct
    public void init(){
         resend = new Resend(RESEND_API_KEY);
    }

    public void sendEmail(String to, String subject, Map<String, Object> data, String mailTemplateName) {

        String text = getMailTemplate(data, mailTemplateName);

        try{
            CreateEmailOptions emailRequest = CreateEmailOptions.builder()
                    .from("Acme <onboarding@resend.dev>")
                    .to("hardcode.labs.v1@gmail.com")//para pruebas solo se puede enviar al correo de mi cuenta en resend
                    .html(text)
                    .subject(subject)
                    .build();

            CreateEmailResponse mr = resend.emails().send(emailRequest);

            log.info("Send mail ID: {} to {}", mr.getId(),to);

        }catch (ResendException e){
            throw new MailSenderException(MessageCode.SEND_MAIL_ERROR.getCode(),
                    "Email could not be sent using the Resend API.",
                    EmailService.class.getName(),
                    e.getMessage());
        }
    }

    public String getMailTemplate(Map<String, Object> data, String mailTemplateName) {
        try{
            Template template = configurer.getConfiguration().getTemplate(mailTemplateName);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template,data);

        }catch (IOException | TemplateException e){

            throw new MailSenderException(MessageCode.SEND_MAIL_ERROR.getCode(),
                    "Error reading freemarker template " + mailTemplateName,
                    EmailService.class.getName(),
                    e.getMessage());
        }
    }
}
