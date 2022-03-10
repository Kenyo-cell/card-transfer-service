package org.example.config;

import org.example.repository.FrontMoneyTransferRepository;
import org.example.repository.MoneyTransferRepository;
import org.example.repository.RestMoneyTransferRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "application", value = "profile", havingValue = "front")
    public MoneyTransferRepository frontMoneyTransferRepository() {
        System.out.println("front");
        return new FrontMoneyTransferRepository();
    }

    @Bean
    @ConditionalOnProperty(prefix = "application", value = "profile", havingValue = "rest")
    public MoneyTransferRepository restMoneyTransferRepository() {
        return new RestMoneyTransferRepository();
    }
}
