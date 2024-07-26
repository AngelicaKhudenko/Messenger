package by.angelica.pet_projects.mail_service.controller.token;

import by.angelica.pet_projects.mail_service.controller.token.dto.UserDTO;
import by.angelica.pet_projects.mail_service.controller.token.enums.EUserStatus;
import by.angelica.pet_projects.mail_service.controller.token.utils.GrantedAuthorityDeserializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class UserDetailsExpanded implements UserDetails {

    private final UserDTO user;

    @JsonCreator
    public UserDetailsExpanded(@JsonProperty("user")UserDTO user) {
        this.user = user;
    }


    @JsonDeserialize(using = GrantedAuthorityDeserializer.class)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+this.user.getRole().name());

        return Collections.singleton(authority);
    }

    public UserDTO getUser() {

        return this.user;
    }

    @Override
    public String getPassword() {

        return null;
    }

    public UUID getUUID() {

        return this.user.getUuid();
    }

    @Override
    public String getUsername() {

        return this.user.getUuid().toString();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return !EUserStatus.DEACTIVATED.equals(this.user.getStatus());
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return EUserStatus.ACTIVATED.equals(this.user.getStatus());
    }
}
