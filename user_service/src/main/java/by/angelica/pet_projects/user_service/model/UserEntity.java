package by.angelica.pet_projects.user_service.model;

import by.angelica.pet_projects.user_service.core.enums.EUserRole;
import by.angelica.pet_projects.user_service.core.enums.EUserStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="users", schema = "app")
public class UserEntity {
    @Id
    @Column(name = "id")
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime creation;
    @Version
    @Column(name = "dt_update")
    private LocalDateTime update;
    private String mail;
    @Column(name = "key_word")
    private String key;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "second_name")
    private String secondName;
    @Enumerated(EnumType.STRING)
    private EUserRole role;
    @Enumerated(EnumType.STRING)
    private EUserStatus status;
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_friends",
            schema = "app",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<UserEntity> friends;

    public UserEntity() {
    }

    public UserEntity(UUID uuid,
                      LocalDateTime creation,
                      LocalDateTime update,
                      String mail,
                      String key,
                      String firstName,
                      String secondName,
                      EUserRole role,
                      EUserStatus status,
                      String password,
                      Set<UserEntity> friends) {

        this.uuid = uuid;
        this.creation = creation;
        this.update = update;
        this.mail = mail;
        this.key = key;
        this.firstName = firstName;
        this.secondName = secondName;
        this.role = role;
        this.status = status;
        this.password = password;
        this.friends = friends;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public EUserRole getRole() {
        return role;
    }

    public void setRole(EUserRole role) {
        this.role = role;
    }

    public EUserStatus getStatus() {
        return status;
    }

    public void setStatus(EUserStatus status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserEntity> getFriends() {
        return friends;
    }

    public void setFriends(Set<UserEntity> friends) {
        this.friends = friends;
    }
}
