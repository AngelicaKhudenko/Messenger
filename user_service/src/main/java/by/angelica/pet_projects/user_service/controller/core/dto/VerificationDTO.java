package by.angelica.pet_projects.user_service.controller.core.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerificationDTO {

    @NotEmpty
    @NotNull
    private String mail;

    @NotEmpty
    @NotNull
    private String code;
}
