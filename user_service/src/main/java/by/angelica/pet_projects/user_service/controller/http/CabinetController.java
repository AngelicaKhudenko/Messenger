package by.angelica.pet_projects.user_service.controller.http;

import by.angelica.pet_projects.user_service.core.dto.*;
import by.angelica.pet_projects.user_service.model.UserEntity;
import by.angelica.pet_projects.user_service.service.api.ICabinetService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cabinet")
public class CabinetController {
    private final ICabinetService cabinetService;
    private final ConversionService conversionService;

    public CabinetController(ICabinetService cabinetService,
                              ConversionService conversionService) {

        this.cabinetService = cabinetService;
        this.conversionService = conversionService;
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserRegistrationCUDTO user) {

        this.cabinetService.create(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO login) {

        return this.cabinetService.login(login);
    }

    @GetMapping("/verification")
    public void verify(@RequestParam(value = "code") String code,
                       @RequestParam(value = "mail") String mail) {

        VerificationDTO verification = VerificationDTO
                .builder()
                .code(code)
                .mail(mail)
                .build();

        this.cabinetService.verify(verification);
    }

    @GetMapping("/me")
    public UserDTO getInfoOnMe() {

        UserEntity entity = this.cabinetService.getInfo();

        return this.conversionService.convert(entity, UserDTO.class);
    }

    @GetMapping("/friends")
    public PageDTO<UserDTO> getFriends(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                       @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<UserEntity> entities = this.cabinetService.getFriends(pageable);

        Page<UserDTO> friends = entities.map(entity -> conversionService.convert(entity, UserDTO.class));

        return new PageDTO<>(friends);
    }

    @PutMapping("/friends/{uuid}")
    public void addFriend(@PathVariable(value = "uuid") UUID friend) {

        this.cabinetService.addFriend(friend);
    }

    @DeleteMapping("/friends/{uuid}")
    public void deleteFriend(@PathVariable(value = "uuid") UUID friend) {

        this.cabinetService.deleteFriend(friend);
    }

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.CREATED)
    public void password(@RequestBody PasswordCUDTO password) {

        this.cabinetService.changePassword(password);
    }
}
