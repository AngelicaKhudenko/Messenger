package by.angelica.pet_projects.user_service.service.impl;

import by.angelica.pet_projects.user_service.config.properties.URLProperty;
import by.angelica.pet_projects.user_service.controller.token.UserDetailsExpanded;
import by.angelica.pet_projects.user_service.controller.token.UserHolder;
import by.angelica.pet_projects.user_service.core.dto.*;
import by.angelica.pet_projects.user_service.core.enums.EUserRole;
import by.angelica.pet_projects.user_service.core.enums.EUserStatus;
import by.angelica.pet_projects.user_service.core.exceptions.FieldsIncorrectException;
import by.angelica.pet_projects.user_service.core.utils.JwtTokenHandler;
import by.angelica.pet_projects.user_service.model.UserEntity;
import by.angelica.pet_projects.user_service.model.VerificationEntity;
import by.angelica.pet_projects.user_service.repository.IVerificationRepository;
import by.angelica.pet_projects.user_service.service.api.ICabinetService;
import by.angelica.pet_projects.user_service.service.api.IMailService;
import by.angelica.pet_projects.user_service.service.api.IUserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class CabinetServiceImpl implements ICabinetService {

    private final ConversionService conversionService;
    private final IUserService userService;
    private final IMailService mailService;
    private final IVerificationRepository verificationRepository;
    private final URLProperty urlProperty;
    private final PasswordEncoder encoder;
    private final JwtTokenHandler jwtHandler;
    private final UserHolder userHolder;

    public CabinetServiceImpl(ConversionService conversionService,
                              IUserService userService,
                              IMailService mailService,
                              IVerificationRepository verificationRepository,
                              URLProperty urlProperty,
                              PasswordEncoder encoder,
                              JwtTokenHandler jwtHandler,
                              UserHolder userHolder) {

        this.conversionService = conversionService;
        this.userService = userService;
        this.mailService = mailService;
        this.verificationRepository = verificationRepository;
        this.urlProperty = urlProperty;
        this.encoder = encoder;
        this.jwtHandler = jwtHandler;
        this.userHolder = userHolder;
    }

    @Transactional
    @Override
    public UserEntity create(UserRegistrationCUDTO userRegistrationCUDTO) {

        UserCUDTO userCUDTO = this.conversionService.convert(userRegistrationCUDTO,UserCUDTO.class);

        userCUDTO.setRole(EUserRole.USER);
        userCUDTO.setStatus(EUserStatus.WAITING_ACTIVATION);

        UserEntity user = this.userService.create(userCUDTO);

        String verificationCode = generateVerificationCode();

        VerificationEntity verification = new VerificationEntity();

        verification.setMail(user.getMail());
        verification.setCode(verificationCode);

        this.verificationRepository.saveAndFlush(verification);

        String urlVerification = generateUrl(verificationCode,user.getMail());

        MailDTO mail = this.mailService.generateVerificationMail(user.getMail(),
                user.getFirstName(), user.getSecondName(), urlVerification);

        this.mailService.create(mail);

        return user;
    }

    @Transactional
    @Override
    public void verify(VerificationDTO verification) {

        Optional<VerificationEntity> optional = this.verificationRepository.findById(verification.getMail());

        if (optional.isEmpty()) {
            throw new FieldsIncorrectException("mail","Неверный адрес электронной почты");
        }

        VerificationEntity verificationEntity = optional.get();

        if (!verification.getCode().equals(verificationEntity.getCode())) {
            throw new FieldsIncorrectException("code","Неверный код авторизации");
        }

        Optional<UserEntity> optionalUser = this.userService.get(verification.getMail());

        if (optionalUser.isEmpty()) {
            throw new FieldsIncorrectException("mail","Неверный адрес электронной почты");
        }

        UserEntity user = optionalUser.get();
        user.setStatus(EUserStatus.ACTIVATED);

        this.userService.update(user);
    }

    @Transactional
    @Override
    public String login(LoginDTO login) {

        String mail = login.getMail();
        String password = login.getPassword();

        Optional<UserEntity> optional = this.userService.get(mail);

        if (optional.isEmpty()) {
            throw new FieldsIncorrectException("mail","Пользователь с указанной почтой отсутствует");
        }

        UserEntity entity = optional.get();

        if (entity.getStatus() != EUserStatus.ACTIVATED) {
            throw new FieldsIncorrectException("mail","Пользователь не активирован");
        }

        if (!this.encoder.matches(password, entity.getPassword())) {
            throw new FieldsIncorrectException("password","Неверный логин или пароль");
        }

        return this.jwtHandler.generateAccessToken(entity.getUuid(),entity.getRole());
    }

    @Override
    public UserEntity getInfo() {

        UserDetailsExpanded details = this.userHolder.getUser();

        Optional<UserEntity> optional = this.userService.get(UUID.fromString(details.getUsername()));

        if (optional.isEmpty()) {
            throw new IllegalStateException("Пользователь не авторизован");
        }

        return optional.get();
    }

    @Override
    public Page<UserEntity> getFriends(Pageable pageable) {

        UserDetailsExpanded details = this.userHolder.getUser();

        UUID user = UUID.fromString(details.getUsername());

        return this.userService.getFriends(user,pageable);
    }

    @Transactional
    @Override
    public void addFriend(UUID uuidFriend) {

        UserDetailsExpanded details = this.userHolder.getUser();
        UUID user = UUID.fromString(details.getUsername());

        this.userService.addFriend(user,uuidFriend);
    }

    @Transactional
    @Override
    public void deleteFriend(UUID uuidFriend) {

        UserDetailsExpanded details = this.userHolder.getUser();
        UUID user = UUID.fromString(details.getUsername());

        this.userService.deleteFriend(user,uuidFriend);
    }

    @Transactional
    @Override
    public void changePassword(PasswordCUDTO password) {

        Optional<UserEntity> optional = this.userService.get(password.getMail());

        if (optional.isEmpty()) {
            throw new FieldsIncorrectException("mail","Неверно указан адрес электронной почты");
        }

        UserEntity user = optional.get();

        if (!this.encoder.matches(password.getKey(), user.getKey())) {
            throw new FieldsIncorrectException("key","Неверно указан адрес электронной почты или ключа");
        }

        this.userService.changePassword(password.getPassword(),password.getMail());
    }

    private String generateVerificationCode() {

        UUID uuid = UUID.randomUUID();

        return uuid.toString().replace("-","");
    }

    private String generateUrl(String code, String mail) {

        StringBuilder builder = new StringBuilder();

        builder.append("http://");
        builder.append(System.getenv("SERVER"));
        builder.append(this.urlProperty.getVerification());
        builder.append("?code=");
        builder.append(code);
        builder.append("&mail=");
        builder.append(mail);

        return builder.toString();
    }
}
