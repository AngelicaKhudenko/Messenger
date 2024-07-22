package by.angelica.pet_projects.user_service.service.impl;

import by.angelica.pet_projects.user_service.core.dto.MailDTO;
import by.angelica.pet_projects.user_service.core.enums.EMailStatus;
import by.angelica.pet_projects.user_service.model.MailEntity;
import by.angelica.pet_projects.user_service.repository.IMailRepository;
import by.angelica.pet_projects.user_service.service.api.IMailService;
import jakarta.persistence.OptimisticLockException;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class MailServiceImpl implements IMailService {

    private final ConversionService conversionService;
    private final IMailRepository mailRepository;

    public MailServiceImpl(ConversionService conversionService,
                           IMailRepository mailRepository) {

        this.conversionService = conversionService;
        this.mailRepository = mailRepository;
    }

    @Transactional
    @Override
    public MailEntity create(MailDTO mail) {

        MailEntity entity = this.conversionService.convert(mail, MailEntity.class);

        entity.setUuid(UUID.randomUUID());
        entity.setStatus(EMailStatus.LOADED);
        entity.setCreation(LocalDateTime.now());

        return this.mailRepository.saveAndFlush(entity);
    }

    @Override
    public List<MailEntity> getByStatus(EMailStatus status) {

        return this.mailRepository.findAllByStatus(status);
    }

    @Transactional
    @Override
    public MailEntity update(MailEntity entity) {

        Optional<MailEntity> optional = this.mailRepository.findByUuid(entity.getUuid());

        if (optional.isEmpty()) {
            throw new IllegalStateException("Письмо с таким id не зарегистрировано");
        }

        MailEntity entityDB = optional.get();

        if (!entityDB.getUpdate().equals(entity.getUpdate())) {
            throw new OptimisticLockException("Несоответствие версий. Данные были обновлены другим пользователем");
        }

        return this.mailRepository.saveAndFlush(entity);
    }

    @Transactional
    @Override
    public MailDTO generateVerificationMail(String mail, String firstName, String secondName, String urlVerification) {

        StringBuilder builder = new StringBuilder();

        builder.append("Здравствуйте, ");
        builder.append(firstName);
        builder.append(" ");
        builder.append(secondName);
        builder.append("! Мы рады, что вы стали пользователем нашего приложения! Для верификации вашего аккаунта перейдите по ссылке: ");
        builder.append(urlVerification);

        String text = builder.toString();

        MailDTO mailDTO = MailDTO
                .builder()
                .from(System.getenv("MAIL_LOGIN"))
                .to(mail)
                .topic("Верификация")
                .text(text)
                .build();

        return mailDTO;
    }
}
