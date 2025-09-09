package application;

import domain.bankaccount.BankAccount;
import domain.bankaccount.BankAccountPort;
import domain.bankaccount.WithdrawException;
import infrastructure.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;

@Service
public class BankAccountAdapter implements BankAccountPort {

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountAdapter(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public void deposit(Long accountId, double amount) throws AccountNotFoundException {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId+" doesnt exists"));
        account.deposit(amount);
        bankAccountRepository.save(account);
    }

    @Override
    public void withdraw(Long accountId, double amount) throws WithdrawException, AccountNotFoundException {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId+" doesnt exists"));
        account.withdraw(amount);
        bankAccountRepository.save(account);
    }


    @Override
    public BankAccount createBankAccount() {
        return bankAccountRepository.save(new BankAccount());
    }
}
