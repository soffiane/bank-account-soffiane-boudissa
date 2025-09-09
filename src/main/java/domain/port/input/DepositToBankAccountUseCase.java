package domain.port.input;

import javax.security.auth.login.AccountNotFoundException;

public interface DepositToBankAccountUseCase {
    void deposit(Long accountId, double amount) throws AccountNotFoundException;
}
