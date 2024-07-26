package by.angelica.pet_projects.mail_service.core.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailDTO {

    private UUID uuid;

    private String from;

    private String topic;

    private String to;

    private String text;

    private LocalDateTime creation;

    private LocalDateTime update;
}
