package domain.port.input;

import domain.WithdrawException;

import javax.security.auth.login.AccountNotFoundException;

public interface WithdrawFromBankAccountUseCase {
    void withdraw(Long accountId, double amount) throws AccountNotFoundException, WithdrawException;
}
