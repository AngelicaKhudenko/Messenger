package by.angelica.pet_projects.mail_service.model;

import by.angelica.pet_projects.mail_service.core.enums.EMailStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="mails", schema = "app")
public class MailEntity {

    @Id
    @Column(name = "id")
    private UUID uuid;

    @Column(name = "dt_create")
    private LocalDateTime creation;

    @Version
    @Column(name = "dt_update")
    private LocalDateTime update;

    @Enumerated(EnumType.STRING)
    private EMailStatus status;

    @Column(name = "sender")
    private String from;

    private String topic;

    @Column(name = "addressee")
    private String to;

    private String text;

    public MailEntity() {
    }

    public MailEntity(UUID uuid,
                      LocalDateTime creation,
                      LocalDateTime update,
                      EMailStatus status,
                      String from,
                      String topic,
                      String to,
                      String text) {

        this.uuid = uuid;
        this.creation = creation;
        this.update = update;
        this.status = status;
        this.from = from;
        this.topic = topic;
        this.to = to;
        this.text = text;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getCreation() {
        return creation;
    }

    public void setCreation(LocalDateTime creation) {
        this.creation = creation;
    }

    public LocalDateTime getUpdate() {
        return update;
    }

    public void setUpdate(LocalDateTime update) {
        this.update = update;
    }

    public EMailStatus getStatus() {
        return status;
    }

    public void setStatus(EMailStatus status) {
        this.status = status;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
