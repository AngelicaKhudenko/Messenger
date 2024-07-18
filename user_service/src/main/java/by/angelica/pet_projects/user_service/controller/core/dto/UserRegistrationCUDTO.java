package by.angelica.pet_projects.user_service.controller.core.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationCUDTO {

    @NotEmpty
    @NotNull
    private String mail;

    @NotEmpty
    @NotNull
    private String firstName;

    @NotEmpty
    @NotNull
    private String secondName;

    @NotEmpty
    @NotNull
    private String password;

    @NotEmpty
    @NotNull
    private String key;
}
