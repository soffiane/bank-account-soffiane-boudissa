package bootstrap;

import application.BankAccountAdapter;
import domain.bankaccount.BankAccountPort;
import infrastructure.BankAccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankAccountConfiguration {

    @Bean
    public BankAccountPort bankAccountPort(BankAccountRepository bankAccountRepository) {
        return new BankAccountAdapter(bankAccountRepository);
    }


}
