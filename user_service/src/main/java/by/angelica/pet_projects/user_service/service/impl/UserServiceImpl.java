package by.angelica.pet_projects.user_service.service.impl;

import by.angelica.pet_projects.user_service.core.dto.UserCUDTO;
import by.angelica.pet_projects.user_service.core.enums.EUserRole;
import by.angelica.pet_projects.user_service.core.enums.EUserStatus;
import by.angelica.pet_projects.user_service.core.exceptions.FieldsIncorrectException;
import by.angelica.pet_projects.user_service.model.UserEntity;
import by.angelica.pet_projects.user_service.repository.IUserRepository;
import by.angelica.pet_projects.user_service.service.api.IUserService;
import jakarta.persistence.OptimisticLockException;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements IUserService {

    private final ConversionService conversionService;
    private final PasswordEncoder encoder;
    private final IUserRepository userRepository;

    public UserServiceImpl(ConversionService conversionService,
                           PasswordEncoder encoder,
                           IUserRepository userRepository) {

        this.conversionService = conversionService;
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserEntity create(UserCUDTO user) {

        if (EUserRole.getByName(user.getRole().name()).isEmpty()) {
            throw new FieldsIncorrectException("role","Переданы некорректные значения констант");
        }

        if (EUserStatus.getByName(user.getStatus().name()).isEmpty()) {
            throw new FieldsIncorrectException("status","Переданы некорректные значения констант");
        }

        Optional<UserEntity> optional = this.userRepository.findByMail(user.getMail());

        if (optional.isPresent()) {
            throw new FieldsIncorrectException("mail","Пользователь с таким адресом электронной почты уже существует");
        }

        UserEntity entity = this.conversionService.convert(user,UserEntity.class);

        entity.setUuid(UUID.randomUUID());

        LocalDateTime creation = LocalDateTime.now();
        entity.setCreation(creation);

        entity.setPassword(this.encoder.encode(user.getPassword()));
        entity.setKey(this.encoder.encode(user.getKey()));

        return this.userRepository.saveAndFlush(entity);
    }

    @Override
    public Page<UserEntity> get(Pageable pageable) {

        return this.userRepository.findAll(pageable);
    }

    @Override
    public Optional<UserEntity> get(UUID uuid) {

        return this.userRepository.findByUuid(uuid);
    }

    @Override
    public Optional<UserEntity> get(String mail) {

        if (mail == null || mail.isBlank()) {
            throw new FieldsIncorrectException("mail","Переданный адрес электронной почты пуст");
        }

        return this.userRepository.findByMail(mail);
    }

    @Transactional
    @Override
    public void update(UUID uuid, Long updateDate, UserCUDTO user) {

        if (uuid == null) {
            throw new FieldsIncorrectException("uuid","Не передан id");
        }

        if (updateDate == null) {
            throw new FieldsIncorrectException("dt_update","Не передана дата прошлого обновления");
        }

        if (EUserRole.getByName(user.getRole().name()).isEmpty()) {
            throw new FieldsIncorrectException("role","Переданы некорректные значения констант");
        }

        if (EUserStatus.getByName(user.getStatus().name()).isEmpty()) {
            throw new FieldsIncorrectException("status","Переданы некорректные значения констант");
        }

        Optional<UserEntity> optional = this.userRepository.findByUuid(uuid);

        if (optional.isEmpty()) {
            throw new FieldsIncorrectException("uuid","Пользователь с таким id не зарегистрирвоан");
        }

        UserEntity entity = optional.get();

        Instant instant = Instant.ofEpochMilli(updateDate);
        LocalDateTime updateDateLocal = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

        if (!entity.getUpdate().equals(updateDateLocal)) {
            throw new OptimisticLockException("Несоответствие версий. Данные были обновлены другим пользователем");
        }

        entity.setMail(user.getMail());
        entity.setFirstName(user.getFirstName());
        entity.setSecondName(user.getSecondName());
        entity.setKey(this.encoder.encode(user.getKey()));
        entity.setRole(user.getRole());
        entity.setStatus(user.getStatus());
        entity.setPassword(this.encoder.encode(user.getPassword()));

        this.userRepository.saveAndFlush(entity);
    }

    @Transactional
    @Override
    public UserEntity update(UserEntity entity) {

        if(entity == null) {
            throw new IllegalArgumentException("Не передан пользователь для обновления");
        }

        Optional<UserEntity> optional = this.userRepository.findByUuid(entity.getUuid());

        if (optional.isEmpty()) {
            throw new FieldsIncorrectException("uuid","Пользователь с таким id не зарегистрирован");
        }

        UserEntity entityDB = optional.get();

        if (!entityDB.getUpdate().equals(entity.getUpdate())) {
            throw new OptimisticLockException("Несоответствие версий. Данные были обновлены другим пользователем");
        }

        this.userRepository.saveAndFlush(entity);
        return entityDB;
    }

    @Override
    public Page<UserEntity> getFriends(UUID user, Pageable pageable) {

        if (user == null) {
            throw new FieldsIncorrectException("uuid","Не передан id");
        }

        Optional<UserEntity> optional = this.userRepository.findByUuid(user);

        if (optional.isEmpty()) {
            throw new FieldsIncorrectException("uuid","Пользователь с таким id не зарегистрирован");
        }

        return this.userRepository.findFriendsByUserId(user,pageable);
    }

    @Transactional
    @Override
    public void addFriend(UUID uuidUser, UUID uuidFriend) {

        if (uuidUser == null) {
            throw new FieldsIncorrectException("uuid","Не передан id пользователя");
        }

        if (uuidFriend == null) {
            throw new FieldsIncorrectException("friend","Не передан id для добавления друга");
        }

        Optional<UserEntity> optionalUser = this.userRepository.findByUuid(uuidUser);

        if (optionalUser.isEmpty()) {
            throw new FieldsIncorrectException("uuid","Неверно указан id пользователя");
        }

        UserEntity user = optionalUser.get();

        Optional<UserEntity> optionalFriend = this.userRepository.findByUuid(uuidFriend);

        if (optionalFriend.isEmpty()) {
            throw new FieldsIncorrectException("friend","Неверно указан id для friend");
        }

        UserEntity friend = optionalFriend.get();

        user.addFriend(friend);
        this.userRepository.saveAndFlush(user);
    }

    @Transactional
    @Override
    public void deleteFriend(UUID uuidUser, UUID uuidFriend) {

        if (uuidUser == null) {
            throw new FieldsIncorrectException("uuid","Не передан id пользователя");
        }

        if (uuidFriend == null) {
            throw new FieldsIncorrectException("friend","Не передан id для добавления друга");
        }

        Optional<UserEntity> optionalUser = this.userRepository.findByUuid(uuidUser);

        if (optionalUser.isEmpty()) {
            throw new FieldsIncorrectException("uuid","Неверно указан id пользователя");
        }

        UserEntity user = optionalUser.get();

        Optional<UserEntity> optionalFriend = this.userRepository.findByUuid(uuidFriend);

        if (optionalFriend.isEmpty()) {
            throw new FieldsIncorrectException("friend","Неверно указан id для friend");
        }

        UserEntity friend = optionalFriend.get();

        user.deleteFriend(friend);
        this.userRepository.saveAndFlush(user);
    }

    @Transactional
    @Override
    public void changePassword(String password, String mail) {

        if (password == null || password.isBlank()) {
            throw new FieldsIncorrectException("password","Переданный пароль пуст");
        }

        Optional<UserEntity> optional = this.userRepository.findByMail(mail);

        if (optional.isEmpty()) {
            throw new FieldsIncorrectException("mail","Неверно указан адрес электронной почты");
        }

        UserEntity user = optional.get();
        user.setPassword(this.encoder.encode(password));

        update(user);
    }
}
