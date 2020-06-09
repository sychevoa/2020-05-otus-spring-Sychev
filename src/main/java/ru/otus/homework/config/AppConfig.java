package ru.otus.homework.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Getter
@PropertySource("classpath:application.properties")
@Configuration
public class AppConfig {

    @Value("${questions.toPass}")
    private int toPass;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){

        return new PropertySourcesPlaceholderConfigurer();
    }

}
