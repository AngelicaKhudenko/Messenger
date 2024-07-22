package by.angelica.pet_projects.user_service.core.converters;

import by.angelica.pet_projects.user_service.core.dto.UserDTO;
import by.angelica.pet_projects.user_service.model.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToDTOConverter implements Converter<UserEntity, UserDTO> {
    @Override
    public UserDTO convert(UserEntity item) {

        return UserDTO.builder()
                .uuid(item.getUuid())
                .creation(item.getCreation())
                .update(item.getUpdate())
                .mail(item.getMail())
                .firstName(item.getFirstName())
                .secondName(item.getSecondName())
                .role(item.getRole())
                .status(item.getStatus())
                .build();
    }
}
