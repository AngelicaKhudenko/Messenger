package by.angelica.pet_projects.mail_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableJpaRepositories
@EnableWebMvc
@EnableFeignClients(basePackages = {"by.angelica.pet_projects.mail_service.service.feign"})
public class MailServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(MailServiceApplication.class,args);
    }
}
