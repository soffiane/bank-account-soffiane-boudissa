package domain.port.output;

import adapter.entity.BankAccountEntity;
import domain.WithdrawException;
import domain.dto.BankAccount;

import javax.security.auth.login.AccountNotFoundException;

public interface BankAccountRepositoryPort {

    BankAccount deposit(Long accountId, double amount) throws AccountNotFoundException;
    BankAccount withdraw(Long accountId, double amount) throws WithdrawException, AccountNotFoundException;
    BankAccount createBankAccount(BankAccount bankAccount);
}
