package by.angelica.pet_projects.mail_service.mail_settings;

import by.angelica.pet_projects.mail_service.core.enums.EMailStatus;
import by.angelica.pet_projects.mail_service.model.MailEntity;
import by.angelica.pet_projects.mail_service.service.api.IMailSendService;
import by.angelica.pet_projects.mail_service.service.api.IMailService;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MailSendJob {

    private final IMailService mailService;
    private final IMailSendService mailSendService;

    public MailSendJob(IMailService mailService,
                       IMailSendService mailSendService) {

        this.mailService = mailService;
        this.mailSendService = mailSendService;
    }

    public void execute() {

        List<MailEntity> loaded = this.mailService.getByStatus(EMailStatus.LOADED);

        for (MailEntity mail : loaded) {

            try {
                mail.setStatus(EMailStatus.OK);
                this.mailSendService.send(mail);
            } catch (MailException e) {
                mail.setStatus(EMailStatus.ERROR);
            } finally {
                this.mailService.update(mail);
            }
        }
    }
}
