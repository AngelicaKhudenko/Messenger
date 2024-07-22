package by.angelica.pet_projects.user_service.core.enums;

public enum EMailStatus {
    LOADED("Загружено"),
    OK("Отправлено"),
    ERROR("Ошибка")
    ;
    private final String description;

    EMailStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
