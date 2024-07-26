package by.angelica.pet_projects.mail_service.service.api;


import by.angelica.pet_projects.mail_service.core.dto.MailCUDTO;
import by.angelica.pet_projects.mail_service.core.enums.EMailStatus;
import by.angelica.pet_projects.mail_service.model.MailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMailService {

    /**
     * Creates a mail. The mail has the "LOADED" status.
     * @param mail - a mail for creating in accordance with the database requirements.
     * @return MailEntity - a created mail in accordance with the database requirements.
     */
    MailEntity create(MailCUDTO mail);

    /**
     * Returns a page of sent mails.
     * @param pageable - an object for pagination.
     * @return Page<MailEntity> - a page of sent mails.
     */
    Page<MailEntity> getSent(Pageable pageable);

    /**
     * Returns a page of inbox mails.
     * @param pageable - an object for pagination.
     * @return Page<MailEntity> - a page of inbox mails.
     */
    Page<MailEntity> getInbox(Pageable pageable);

    /**
     * Returns the mail with the specified id.
     * @param uuid - an id for searching.
     * @return Optional<MailEntity> - the mail with the specified id.
     */
    Optional<MailEntity> get(UUID uuid);

    /**
     * Updates the mail.
     * @param uuid - the mail id.
     * @param updateDate - last update date.
     * @param mail - data on the mail for updating.
     */
    void update(UUID uuid, Long updateDate, MailCUDTO mail);

    /**
     * Updates the mail.
     * @param entity - the mail with updated fields.
     * @return MailEntity - the updated mail in accordance with the database requirements.
     */
    MailEntity update (MailEntity entity);

    /**
     * Deletes the mail.
     * @param uuid - the mail id.
     * @param updateDate - last update date.
     */
    void delete(UUID uuid, Long updateDate);

    /**
     * Returns a list of mails with the specified status.
     * @param status - a status for searching.
     * @return List<MailEntity> - a list of mails with the specified status in accordance with the database requirements.
     */
    List<MailEntity> getByStatus(EMailStatus status);
}
