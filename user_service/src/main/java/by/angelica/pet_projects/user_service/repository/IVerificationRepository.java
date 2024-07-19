package by.angelica.pet_projects.user_service.repository;

import by.angelica.pet_projects.user_service.model.VerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVerificationRepository extends JpaRepository<VerificationEntity, String> {
}
