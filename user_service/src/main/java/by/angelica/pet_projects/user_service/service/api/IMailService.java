package by.angelica.pet_projects.user_service.service.api;

import by.angelica.pet_projects.user_service.core.dto.MailDTO;
import by.angelica.pet_projects.user_service.core.enums.EMailStatus;
import by.angelica.pet_projects.user_service.model.MailEntity;

import java.util.List;

public interface IMailService {

    /**
     * Creates a mail. The mail has the "LOADED" status.
     * @param mail - a mail for creating in accordance with the database requirements.
     * @return MailEntity - a created mail in accordance with the database requirements.
     */
    MailEntity create(MailDTO mail);

    /**
     * Returns a list of mails with the specified status.
     * @param status - a status for searching.
     * @return List<MailEntity> - a list of mails with the specified status in accordance with the database requirements.
     */
    List<MailEntity> getByStatus(EMailStatus status);

    /**
     * Updates the mail.
     * @param entity - the mail with updated fields.
     * @return MailEntity - the updated mail in accordance with the database requirements.
     */
    MailEntity update (MailEntity entity);

    /**
     * Generates a mail with fields described in MailDTO. The mail contains the text for a user with greetings
     * and a link for verification.
     * @param mail - the user's mail.
     * @param firstName - the user's first name.
     * @param secondName - the user's second name.
     * @param urlVerification - a link for verification.
     * @return MailDTO - a mail for verification.
     */
    MailDTO generateVerificationMail(String mail, String firstName, String secondName, String urlVerification);
}
