package by.angelica.pet_projects.user_service.service.api;

import by.angelica.pet_projects.user_service.core.dto.MailDTO;

public interface IMailService {

    void create(MailDTO mail);

    MailDTO generateVerificationMail(String firstName, String secondName, String urlVerification);
}
