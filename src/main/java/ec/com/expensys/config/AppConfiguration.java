package ec.com.expensys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class AppConfiguration {

    @Autowired
    ResourceLoader resourceLoader;

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages_es_ES");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
