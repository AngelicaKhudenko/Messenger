package by.angelica.pet_projects.user_service.service.api;

import by.angelica.pet_projects.user_service.core.dto.*;
import by.angelica.pet_projects.user_service.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ICabinetService {
    void create(UserRegistrationCUDTO user);
    String login(LoginDTO login);
    void password(PasswordCUDTO password);
    void verify(VerificationDTO verification);
    UserEntity getInfoOnMe();
    Page<UserEntity> getFriends(Pageable pageable);

    void addFriend(UUID friend);

    void deleteFriend(UUID friend);
}
