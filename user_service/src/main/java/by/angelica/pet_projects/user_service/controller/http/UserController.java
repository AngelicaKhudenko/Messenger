package by.angelica.pet_projects.user_service.controller.http;

import by.angelica.pet_projects.user_service.controller.token.UserHolder;
import by.angelica.pet_projects.user_service.core.dto.PageDTO;
import by.angelica.pet_projects.user_service.core.dto.UserCUDTO;
import by.angelica.pet_projects.user_service.core.dto.UserDTO;
import by.angelica.pet_projects.user_service.core.exceptions.FieldsIncorrectException;
import by.angelica.pet_projects.user_service.model.UserEntity;
import by.angelica.pet_projects.user_service.service.api.IUserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;
    private final ConversionService conversionService;
    private final UserHolder userHolder;

    public UserController(IUserService userService,
                          ConversionService conversionService,
                          UserHolder userHolder) {

        this.userService = userService;
        this.conversionService = conversionService;
        this.userHolder = userHolder;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserCUDTO user) {

        this.userService.create(user);
    }

    @GetMapping
    public PageDTO<UserDTO> get(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<UserEntity> entities = this.userService.get(pageable);

        Page<UserDTO> userDTOS = entities.map(entity -> conversionService.convert(entity, UserDTO.class));

        return new PageDTO<>(userDTOS);
    }

    @GetMapping(value = "/{uuid}")
    public UserDTO getById(@PathVariable(value = "uuid") UUID uuid) {

        Optional<UserEntity> optional = this.userService.get(uuid);

        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Передан некорректный id пользователя");
        }

        UserEntity entity = optional.get();

        return this.conversionService.convert(entity,UserDTO.class);
    }

    @PutMapping(value = "/{uuid}/dt_update/{dt_update}")
    public void update(@PathVariable(value = "uuid") UUID uuid,
                       @PathVariable(value = "dt_update") Long updateDate,
                       @RequestBody UserCUDTO user) {

        this.userService.update(uuid, updateDate, user);
    }

    @GetMapping(value = "/details")
    public UserDetails details(){

        return this.userHolder.getUser();
    }
}
