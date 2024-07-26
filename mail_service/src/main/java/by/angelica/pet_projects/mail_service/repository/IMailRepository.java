package by.angelica.pet_projects.mail_service.repository;

import by.angelica.pet_projects.mail_service.core.enums.EMailStatus;
import by.angelica.pet_projects.mail_service.model.MailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMailRepository extends JpaRepository<MailEntity,String> {
    List<MailEntity> findAllByStatus(EMailStatus status);
    Optional<MailEntity> findByUuid(UUID uuid);
    Page<MailEntity> findAllByFrom(Pageable pageable, UUID from);
    Page<MailEntity> findAllByTo(Pageable pageable, UUID to);
}
