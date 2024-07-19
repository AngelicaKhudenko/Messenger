package by.angelica.pet_projects.user_service.controller.token;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserHolder {

    public UserDetailsExpanded getUser(){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetailsExpanded) {
            return (UserDetailsExpanded) principal;
        }

        throw new IllegalStateException("Ошибка при получении данных о пользователе из токена");
    }
}
