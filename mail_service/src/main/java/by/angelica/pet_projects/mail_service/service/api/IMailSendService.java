package by.angelica.pet_projects.mail_service.service.api;

import by.angelica.pet_projects.user_service.model.MailEntity;

public interface IMailSendService {

    /**
     * Sends the mail to the addressee.
     * @param entity - the mail for sending.
     */
    void send(MailEntity entity);
}
