package by.angelica.pet_projects.mail_service.service.impl;

import by.angelica.pet_projects.mail_service.controller.token.UserDetailsExpanded;
import by.angelica.pet_projects.mail_service.controller.token.dto.UserDTO;
import by.angelica.pet_projects.mail_service.core.dto.MailCUDTO;
import by.angelica.pet_projects.mail_service.core.enums.EMailStatus;
import by.angelica.pet_projects.mail_service.core.exceptions.FieldsIncorrectException;
import by.angelica.pet_projects.mail_service.model.MailEntity;
import by.angelica.pet_projects.mail_service.repository.IMailRepository;
import by.angelica.pet_projects.mail_service.service.api.IMailService;
import jakarta.persistence.OptimisticLockException;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    public MailEntity create(MailCUDTO mail) {

        UserDTO userDTO = getUserByToken();

        MailEntity entity = this.conversionService.convert(mail, MailEntity.class);

        entity.setFrom(userDTO.getMail());
        entity.setUuid(UUID.randomUUID());
        entity.setStatus(EMailStatus.LOADED);
        entity.setCreation(LocalDateTime.now());

        return this.mailRepository.saveAndFlush(entity);
    }

    @Override
    public Page<MailEntity> getSent(Pageable pageable) {

        UserDTO userDTO = getUserByToken();
        UUID sender = userDTO.getUuid();

        return this.mailRepository.findAllByFrom(pageable, sender);
    }

    @Override
    public Page<MailEntity> getInbox(Pageable pageable) {

        UserDTO userDTO = getUserByToken();
        UUID addressee = userDTO.getUuid();

        return this.mailRepository.findAllByTo(pageable,addressee);
    }

    @Override
    public Optional<MailEntity> get(UUID uuid) {

        return this.mailRepository.findByUuid(uuid);
    }

    @Transactional
    @Override
    public void update(UUID uuid, Long updateDate, MailCUDTO mail) {

        if (uuid == null) {
            throw new FieldsIncorrectException("uuid","Не передан id сообщения");
        }

        if (updateDate == null) {
            throw new FieldsIncorrectException("dt_update","Не передана дата прошлого обновления");
        }

        Optional<MailEntity> optional = this.mailRepository.findByUuid(uuid);

        if (optional.isEmpty()) {
            throw new FieldsIncorrectException("uuid","Сообщение с таким id отсутствует");
        }

        MailEntity entity = optional.get();

        Instant instant = Instant.ofEpochMilli(updateDate);
        LocalDateTime updateDateLocal = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

        if (!entity.getUpdate().equals(updateDateLocal)) {
            throw new OptimisticLockException("Несоответствие версий. Данные были обновлены другим пользователем");
        }

        entity.setTopic(mail.getTopic());
        entity.setTo(mail.getTo());
        entity.setText(mail.getText());

        this.mailRepository.saveAndFlush(entity);
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
    public void delete(UUID uuid, Long updateDate) {

        if (uuid == null) {
            throw new FieldsIncorrectException("uuid","Не передан id сообщения");
        }

        if (updateDate == null) {
            throw new FieldsIncorrectException("dt_update","Не передана дата прошлого обновления");
        }

        Optional<MailEntity> optional = this.mailRepository.findByUuid(uuid);

        if (optional.isEmpty()) {
            throw new FieldsIncorrectException("uuid","Сообщение с таким id не найдено");
        }

        MailEntity mail = optional.get();

        Instant instant = Instant.ofEpochMilli(updateDate);
        LocalDateTime updateDateLocal = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

        if (!mail.getUpdate().equals(updateDateLocal)) {
            throw new OptimisticLockException("Несоответствие версий. Данные были обновлены другим пользователем");
        }

        this.mailRepository.delete(mail);
    }

    @Override
    public List<MailEntity> getByStatus(EMailStatus status) {

        return this.mailRepository.findAllByStatus(status);
    }

    private UserDTO getUserByToken () {

        UserDetailsExpanded userDetailsExpanded = (UserDetailsExpanded) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDTO userDTO = userDetailsExpanded.getUser();

        if (userDTO == null) {
            throw new IllegalStateException("Ошибка при обработке токена");
        }

        return userDTO;
    }
}
