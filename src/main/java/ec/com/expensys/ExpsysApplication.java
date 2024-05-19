package ec.com.expensys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ExpsysApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpsysApplication.class, args);
    }

}
