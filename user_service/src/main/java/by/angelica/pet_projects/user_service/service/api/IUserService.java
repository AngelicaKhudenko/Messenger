package by.angelica.pet_projects.user_service.service.api;

import by.angelica.pet_projects.user_service.core.dto.UserCUDTO;
import by.angelica.pet_projects.user_service.model.UserEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    void create(UserCUDTO userCUDTO);

    Optional<UserEntity> getByMail(String mail);

    UserEntity get(UUID uuid);

    void update(UserEntity user);

    Page<UserEntity> getFriends(UUID user);

    Optional<UserEntity> get(String mail);

    void changePassword(String password);

    void addFriend(UUID friend);

    void deleteFriend(UUID friend);
}
