package domain.bankaccount;

import javax.security.auth.login.AccountNotFoundException;

/**
 * D'une fonctionnalité de dépôt d'argent
 * D'une fonctionnalité de retrait d'argent
 */
public interface BankAccountPort {

    void deposit(Long accountId, double amount) throws AccountNotFoundException;
    void withdraw(Long accountId, double amount) throws WithdrawException, AccountNotFoundException;
    BankAccount createBankAccount();
}
