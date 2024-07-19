package by.angelica.pet_projects.user_service.repository;

import by.angelica.pet_projects.user_service.model.UserEntity;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUserRepository extends JpaRepository<UserEntity,String> {

    @Query("SELECT friends FROM UserEntity user JOIN user.friends friends WHERE user.id = :userId")
    Page<UserEntity> findFriendsByUserId(@Param("userId") UUID userId, Pageable pageable);
}
