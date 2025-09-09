package config;

import domain.port.output.BankAccountRepositoryPort;
import domain.service.BankAccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankAccountConfiguration {

    @Bean
    public BankAccountService bankAccountService(BankAccountRepositoryPort bankAccountRepositoryPort) {
        return new BankAccountService(bankAccountRepositoryPort);
    }

}
