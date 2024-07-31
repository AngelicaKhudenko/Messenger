package by.angelica.pet_projects.mail_service.core.converters;

import by.angelica.pet_projects.mail_service.core.dto.MailDTO;
import by.angelica.pet_projects.mail_service.model.MailEntity;
import org.springframework.core.convert.converter.Converter;

public class MailEntityToDTOConverter implements Converter<MailEntity, MailDTO> {

    @Override
    public MailDTO convert(MailEntity item) {

        return MailDTO.builder()
                .uuid(item.getUuid())
                .from(item.getFrom())
                .text(item.getTopic())
                .to(item.getTo())
                .topic(item.getText())
                .creation(item.getCreation())
                .update(item.getUpdate())
                .build();
    }
}