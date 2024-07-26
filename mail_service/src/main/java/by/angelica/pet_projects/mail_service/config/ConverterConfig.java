package by.angelica.pet_projects.mail_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ConverterConfig {

    @Bean
    public UserEntityToDTOConverter userEntityToDTOConverter() {
        return new UserEntityToDTOConverter();
    }

    @Bean
    public MailDTOtoEntityConverter mailDTOtoEntityConverter() {
        return new MailDTOtoEntityConverter();
    }

    @Bean
    public UserCUDTOToEntityConverter userCUDTOToEntityConverter() {
        return new UserCUDTOToEntityConverter();
    }

    @Bean
    public UserRegistrationCUDTOtoUserCUDTOConverter userRegistrationCUDTOtoUserCUDTOConverter() {
        return new UserRegistrationCUDTOtoUserCUDTOConverter();
    }

    @Bean
    public ConversionService conversionService() {

        ConversionServiceFactoryBean factory = new ConversionServiceFactoryBean();
        factory.setConverters(getConverters());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    private Set<Converter<?, ?>> getConverters() {

        Set<Converter<?, ?>> converters = new HashSet<>();
        converters.add(userEntityToDTOConverter());
        converters.add(mailDTOtoEntityConverter());
        converters.add(userCUDTOToEntityConverter());
        converters.add(userRegistrationCUDTOtoUserCUDTOConverter());
        return converters;
    }
    
    @Bean
    public ConversionServiceFactoryBean conversionServiceFactoryBean() {
        return new ConversionServiceFactoryBean();
    }
}
