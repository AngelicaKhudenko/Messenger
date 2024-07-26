package by.angelica.pet_projects.mail_service.controller.token.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;

public class GrantedAuthorityDeserializer extends JsonDeserializer<SimpleGrantedAuthority> {
    @Override
    public SimpleGrantedAuthority deserialize(JsonParser p,
                                              DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        String authority = p.getValueAsString();
        return new SimpleGrantedAuthority(authority);
    }
}
