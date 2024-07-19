package by.angelica.pet_projects.user_service.core.enums;

import java.util.Optional;

public enum EUserRole {

    ADMIN("Администратор"),
    USER("Пользователь"),
    ;

    private final String description;

    EUserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Optional<EUserRole> getByName(String name){
        for (EUserRole value:values()){
            if(value.name().equals(name)){
                return Optional.of(value);
            }
        }

        return Optional.empty();
    }
}
