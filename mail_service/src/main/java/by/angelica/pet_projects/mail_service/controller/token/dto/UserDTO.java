package by.angelica.pet_projects.mail_service.controller.token.dto;

import by.angelica.pet_projects.mail_service.controller.token.enums.EUserRole;
import by.angelica.pet_projects.mail_service.controller.token.enums.EUserStatus;
import by.angelica.pet_projects.mail_service.controller.utils.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private UUID uuid;

    @JsonProperty("dt_create")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime creation;

    @JsonProperty("dt_update")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime update;

    private String mail;

    private String firstName;

    private String secondName;

    private EUserRole role;

    private EUserStatus status;
}
