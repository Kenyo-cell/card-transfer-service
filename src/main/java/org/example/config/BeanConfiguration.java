package org.example.config;

import org.example.repository.MoneyTransferRepository;
import org.example.util.CodeGenerator;
import org.example.util.FrontCodeGenerator;
import org.example.util.RestCodeGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "application", value = "profile", havingValue = "front")
    public CodeGenerator frontCodeGenerator() {
        return new FrontCodeGenerator();
    }

    @Bean
    @ConditionalOnProperty(prefix = "application", value = "profile", havingValue = "rest")
    public CodeGenerator restCodeGenerator() {
        return new RestCodeGenerator();
    }
}
