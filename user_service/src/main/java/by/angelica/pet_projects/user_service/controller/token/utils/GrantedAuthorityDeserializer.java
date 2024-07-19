package by.angelica.pet_projects.user_service.controller.token.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;

public class GrantedAuthorityDeserializer extends JsonDeserializer<SimpleGrantedAuthority> {

    @Override
    public SimpleGrantedAuthority deserialize(JsonParser jsonParser,
                                              DeserializationContext deserializationContext)
            throws IOException, JacksonException {

        String authority = jsonParser.getValueAsString();
        return new SimpleGrantedAuthority(authority);
    }
}
