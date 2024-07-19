package by.angelica.pet_projects.user_service.core.dto;

import by.angelica.pet_projects.user_service.core.enums.EUserRole;
import by.angelica.pet_projects.user_service.core.enums.EUserStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCUDTO {

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

    @NotNull
    private EUserRole role;

    @NotNull
    private EUserStatus status;
}
