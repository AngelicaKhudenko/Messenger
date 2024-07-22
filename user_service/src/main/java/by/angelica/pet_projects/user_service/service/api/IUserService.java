package by.angelica.pet_projects.user_service.service.api;

import by.angelica.pet_projects.user_service.core.dto.UserCUDTO;
import by.angelica.pet_projects.user_service.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IUserService {

    /**
     * Creates user in the app.
     * @param user - a user for creating with fields for UserCUDTO.
     * @return UserEntity - created user in accordance with the database requirements.
     */
    UserEntity create(UserCUDTO user);

    /**
     * Returns a page of users.
     * @param pageable - an object for pagination.
     * @return Page<UserEntity> - a page of users.
     */
    Page<UserEntity> get(Pageable pageable);

    /**
     * Returns the user with the specified id.
     * @param uuid - an id for searching.
     * @return Optional<UserEntity> - the user with the specified id.
     */
    Optional<UserEntity> get(UUID uuid);

    /**
     * Returns the user with the specified mail.
     * @param mail - a mail for searching.
     * @return Optional<UserEntity> - the user with the specified mail.
     */
    Optional<UserEntity> get(String mail);

    /**
     * Updates the user.
     * @param uuid - the user id.
     * @param updateDate - last update date.
     * @param user - data on the user for updating.
     */
    void update(UUID uuid, Long updateDate, UserCUDTO user);

    /**
     * Updates the user.
     * @param entity - user in accordance with the database requirements.
     * @return UserEntity - the updated user in accordance with the database requirements.
     */
    UserEntity update(UserEntity entity);

    /**
     * Returns a page of friends of the specified user.
     * @param user - a user for searching friends.
     * @param pageable - an object for pagination.
     * @return Page<UserEntity> -  a page of friends.
     */
    Page<UserEntity> getFriends(UUID user, Pageable pageable);

    /**
     * Adds the friend in the user's set of friends.
     * @param uuidUser - an id of the user.
     * @param uuidFriend - an id of the friend.
     */
    void addFriend(UUID uuidUser, UUID uuidFriend);

    /**
     * Deletes the friend from the user's set of friends.
     * @param uuidUser - an id of the user.
     * @param uuidFriend - an id of the friend.
     */
    void deleteFriend(UUID uuidUser,UUID uuidFriend);

    /**
     * Changes the user's password.
     * @param password - a new password.
     * @param mail - the user's mail.
     */
    void changePassword(String password, String mail);
}
