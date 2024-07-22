package by.angelica.pet_projects.user_service.core.converters;

import by.angelica.pet_projects.user_service.core.dto.UserCUDTO;
import by.angelica.pet_projects.user_service.model.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserCUDTOToEntityConverter implements Converter<UserCUDTO, UserEntity> {
    @Override
    public UserEntity convert(UserCUDTO item) {

        UserEntity entity = new UserEntity();

        entity.setMail(item.getMail());
        entity.setFirstName(item.getFirstName());
        entity.setSecondName(item.getSecondName());
        entity.setRole(item.getRole());
        entity.setStatus(item.getStatus());

        return entity;
    }
}
