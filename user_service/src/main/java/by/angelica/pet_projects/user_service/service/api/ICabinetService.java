package by.angelica.pet_projects.user_service.service.api;

import by.angelica.pet_projects.user_service.core.dto.*;
import by.angelica.pet_projects.user_service.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ICabinetService {

    /**
     * Creates user in the app.
     * @param user - a user for creating with fields for UserCUDTO.
     * @return UserEntity - created user in accordance with the database requirements.
     */
    UserEntity create(UserRegistrationCUDTO user);

    /**
     * Allows the user to verify the account. It means changing the user's status from "WAITING_ACTIVATION" to "ACTIVATED".
     * @param verification - an object with data necessary for verification (a mail, a verification code).
     */
    void verify(VerificationDTO verification);

    /**
     * Allows the user to login.
     * @param login - an object with the user's mail and password.
     * @return String - a token.
     */
    String login(LoginDTO login);

    /**
     * Allows the user to get the data on the account.
     * @return UserEntity - the data on the user in accordance with the database requirements.
     */
    UserEntity getInfo();

    /**
     * Returns a page of friends.
     * @param pageable - an object for pagination.
     * @return Page<UserEntity> -  a page of friends.
     */
    Page<UserEntity> getFriends(Pageable pageable);

    /**
     * Adds the friend in the user's set of friends.
     * @param uuidFriend - an id of the friend.
     */
    void addFriend(UUID uuidFriend);

    /**
     * Deletes the friend from the user's set of friends.
     * @param uuidFriend - an id of the friend.
     */
    void deleteFriend(UUID uuidFriend);

    /**
     * Changes the user's password.
     * @param password - an object with data necessary for changing the password(a mail, a secret key, a new password)
     */
    void changePassword(PasswordCUDTO password);
}
