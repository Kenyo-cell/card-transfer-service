package org.example.config;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
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

import java.util.Map;

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
    public Writer csvWriter() {
        return new CSVWriterImpl()
                .ofMappers(
                        Map.of(
                                WriteData.class,
                                new CsvMapper().writerFor(WriteData.class)
                        )
                );
    }
}
