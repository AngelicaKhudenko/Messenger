package by.angelica.pet_projects.mail_service.config;

import by.angelica.pet_projects.mail_service.core.converters.MailCUDTOToEntityConverter;
import by.angelica.pet_projects.mail_service.core.converters.MailEntityToDTOConverter;
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
    public MailEntityToDTOConverter mailEntityToDTOConverter() {
        return new MailEntityToDTOConverter();
    }

    @Bean
    public MailCUDTOToEntityConverter mailDTOtoEntityConverter() {
        return new MailCUDTOToEntityConverter();
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
        converters.add(mailEntityToDTOConverter());
        converters.add(mailDTOtoEntityConverter());

        return converters;
    }
    
    @Bean
    public ConversionServiceFactoryBean conversionServiceFactoryBean() {
        return new ConversionServiceFactoryBean();
    }
}
