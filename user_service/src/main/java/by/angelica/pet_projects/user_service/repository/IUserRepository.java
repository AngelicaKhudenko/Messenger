package by.angelica.pet_projects.user_service.repository;

import by.angelica.pet_projects.user_service.model.UserEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserEntity,String> {

    Optional<UserEntity> findByMail(String mail);
    Optional<UserEntity> findByUuid(UUID uuid);
    @Query("SELECT friends FROM UserEntity user JOIN user.friends friends WHERE user.uuid = :userUUID")
    Page<UserEntity> findFriendsByUserId(@Param("userUUID") UUID userUUID, Pageable pageable);
}
