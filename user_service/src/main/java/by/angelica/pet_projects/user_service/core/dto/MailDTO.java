package by.angelica.pet_projects.user_service.core.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailDTO {
    @NotEmpty
    @NotNull
    private String from;
    @NotEmpty
    @NotNull
    private String topic;
    @NotEmpty
    @NotNull
    private String addressee_first_name;
    @NotEmpty
    @NotNull
    private String addressee_second_name;
    @NotEmpty
    @NotNull
    private String text;
}