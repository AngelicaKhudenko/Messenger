package by.angelica.pet_projects.user_service.repository;

import by.angelica.pet_projects.user_service.core.enums.EMailStatus;
import by.angelica.pet_projects.user_service.model.MailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMailRepository extends JpaRepository<MailEntity,String> {
    List<MailEntity> findAllByStatus(EMailStatus status);
    Optional<MailEntity> findByUuid(UUID uuid);
}
