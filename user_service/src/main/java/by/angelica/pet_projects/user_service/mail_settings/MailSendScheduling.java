package by.angelica.pet_projects.user_service.mail_settings;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class MailSendScheduling {

    private final MailSendJob mailSendJob;

    public MailSendScheduling(MailSendJob mailSendJob) {
        this.mailSendJob = mailSendJob;
    }

    @Scheduled(timeUnit = TimeUnit.SECONDS,fixedDelay = 50)
    public void start() {

        this.mailSendJob.execute();
    }
}
