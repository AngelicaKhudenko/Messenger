package by.angelica.pet_projects.mail_service.core.converters;

import by.angelica.pet_projects.mail_service.core.dto.MailCUDTO;
import by.angelica.pet_projects.mail_service.model.MailEntity;
import org.springframework.core.convert.converter.Converter;

public class MailCUDTOToEntityConverter implements Converter<MailCUDTO, MailEntity> {

    @Override
    public MailEntity convert(MailCUDTO mail) {

        MailEntity entity = new MailEntity();

        entity.setTopic(mail.getTopic());
        entity.setTo(mail.getTo());
        entity.setText(mail.getText());

        return entity;
    }
}
