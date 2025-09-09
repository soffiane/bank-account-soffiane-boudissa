package domain.service;

import domain.WithdrawException;
import domain.dto.BankAccount;
import domain.port.input.CreateBankAccountUseCase;
import domain.port.input.DepositToBankAccountUseCase;
import domain.port.input.WithdrawFromBankAccountUseCase;
import domain.port.output.BankAccountRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;

@Service
public class BankAccountService implements CreateBankAccountUseCase, WithdrawFromBankAccountUseCase, DepositToBankAccountUseCase {

    @Autowired
    private final BankAccountRepositoryPort bankAccountRepositoryPort;

    public BankAccountService(BankAccountRepositoryPort bankAccountRepositoryPort) {
        this.bankAccountRepositoryPort = bankAccountRepositoryPort;
    }

    @Override
    public BankAccount createBankAccount(BankAccount bankAccount) {
        return bankAccountRepositoryPort.createBankAccount(bankAccount);
    }

    @Override
    public void deposit(Long accountId, double amount) throws AccountNotFoundException {
        bankAccountRepositoryPort.deposit(accountId,amount);

    }

    @Override
    public void withdraw(Long accountId, double amount) throws AccountNotFoundException, WithdrawException {
        bankAccountRepositoryPort.withdraw(accountId,amount);
    }

}
