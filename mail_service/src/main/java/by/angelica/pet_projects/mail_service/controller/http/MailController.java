package by.angelica.pet_projects.mail_service.controller.http;

import by.angelica.pet_projects.mail_service.core.dto.MailCUDTO;
import by.angelica.pet_projects.mail_service.core.dto.MailDTO;
import by.angelica.pet_projects.mail_service.core.dto.PageDTO;
import by.angelica.pet_projects.mail_service.model.MailEntity;
import by.angelica.pet_projects.mail_service.service.api.IMailService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/mails")
public class MailController {
    private final IMailService mailService;
    private final ConversionService conversionService;

    public MailController(IMailService mailService,
                          ConversionService conversionService) {

        this.mailService = mailService;
        this.conversionService = conversionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody MailCUDTO mail) {

        this.mailService.create(mail);
    }

    @GetMapping(value = "/sent")
    public PageDTO<MailDTO> getSent(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                    @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<MailEntity> entities = this.mailService.getSent(pageable);

        Page<MailDTO> mailDTOS = entities.map(entity -> conversionService.convert(entity, MailDTO.class));

        return new PageDTO<>(mailDTOS);
    }

    @GetMapping(value = "/inbox")
    public PageDTO<MailDTO> getInbox(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                     @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<MailEntity> entities = this.mailService.getInbox(pageable);

        Page<MailDTO> mailDTOS = entities.map(entity -> conversionService.convert(entity, MailDTO.class));

        return new PageDTO<>(mailDTOS);
    }

    @GetMapping(value = "/{uuid}")
    public MailDTO getById(@PathVariable(value = "uuid") UUID uuid) {

        Optional<MailEntity> optional = this.mailService.get(uuid);

        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Передан некорректный id сообщения");
        }

        MailEntity entity = optional.get();

        return this.conversionService.convert(entity,MailDTO.class);
    }

    @PutMapping(value = "/{uuid}/dt_update/{dt_update}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@PathVariable(value = "uuid") UUID uuid,
                       @PathVariable(value = "dt_update") Long updateDate,
                       @RequestBody MailCUDTO mail) {

        this.mailService.update(uuid, updateDate, mail);
    }

    @DeleteMapping(value = "/{uuid}/dt_update/{dt_update}")
    public void delete(@PathVariable(value = "uuid") UUID uuid,
                       @PathVariable(value = "dt_update") Long updateDate) {

        this.mailService.delete(uuid,updateDate);
    }
}
