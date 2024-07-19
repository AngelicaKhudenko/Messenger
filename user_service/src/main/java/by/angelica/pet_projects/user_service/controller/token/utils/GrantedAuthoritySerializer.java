package by.angelica.pet_projects.user_service.controller.token.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.Collection;

public class GrantedAuthoritySerializer extends JsonSerializer<Collection<? extends GrantedAuthority>> {
    @Override
    public void serialize(Collection<? extends GrantedAuthority> grantedAuthorities,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
            throws IOException {

        for (GrantedAuthority authority : grantedAuthorities) {
            jsonGenerator.writeString(authority.getAuthority());
        }
    }
}