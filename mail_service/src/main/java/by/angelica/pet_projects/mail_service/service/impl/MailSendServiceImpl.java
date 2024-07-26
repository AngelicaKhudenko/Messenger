package by.angelica.pet_projects.mail_service.service.impl;

import by.angelica.pet_projects.user_service.model.MailEntity;
import by.angelica.pet_projects.user_service.service.api.IMailSendService;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSendServiceImpl implements IMailSendService {

    private final JavaMailSender javaMailSender;

    public MailSendServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void send(MailEntity entity) throws MailException {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(entity.getFrom());
        message.setTo(entity.getTo());
        message.setSubject(entity.getTopic());
        message.setText(entity.getText());

        this.javaMailSender.send(message);
    }
}
