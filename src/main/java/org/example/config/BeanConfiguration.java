package org.example.config;

import lombok.SneakyThrows;
import org.example.entity.request.transfer.TransferData;
import org.example.entity.writer.WriteData;
import org.example.util.generator.CodeGenerator;
import org.example.util.generator.FrontCodeGenerator;
import org.example.util.generator.OperationIdGenerator;
import org.example.util.generator.RestCodeGenerator;
import org.example.util.writer.CSVWriterImpl;
import org.example.util.writer.Writer;
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

    @Bean
    public OperationIdGenerator operationIdGenerator() {
        return new OperationIdGenerator();
    }

    @Bean
    @SneakyThrows
    public Writer csvWriter() {
        return new CSVWriterImpl(WriteData.class, System.out);
    }
}
