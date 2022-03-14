package org.example.config;

import org.example.util.generator.CodeGenerator;
import org.example.util.generator.FrontCodeGenerator;
import org.example.util.generator.OperationIdGenerator;
import org.example.util.generator.RestCodeGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

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

    @Bean
    @Scope("singleton")
    public OperationIdGenerator operationIdGenerator() {
        return new OperationIdGenerator();
    }
}
