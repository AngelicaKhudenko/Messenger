package by.angelica.pet_projects.user_service.core.converters;

import by.angelica.pet_projects.user_service.core.dto.UserCUDTO;
import by.angelica.pet_projects.user_service.core.dto.UserRegistrationCUDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationCUDTOtoUserCUDTOConverter implements Converter<UserRegistrationCUDTO, UserCUDTO> {
    @Override
    public UserCUDTO convert(UserRegistrationCUDTO item) {

        UserCUDTO userCUDTO = UserCUDTO
                .builder()
                .mail(item.getMail())
                .firstName(item.getFirstName())
                .secondName(item.getSecondName())
                .key(item.getKey())
                .password(item.getPassword())
                .build();

        return userCUDTO;
    }
}
