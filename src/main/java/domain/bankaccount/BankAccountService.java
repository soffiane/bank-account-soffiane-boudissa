package domain.bankaccount;

public interface BankAccountService {
    public void withdraw(String accountId, double amount);
    public void deposit(String accountId, double amount);
    Long createBankAccount();
}
