package application.dto;

import java.math.BigDecimal;

public class BankAccountResponse {

    private Long accountId;
    private BigDecimal balance;
    private BigDecimal overdraftAuthorization;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getOverdraftAuthorization() {
        return overdraftAuthorization;
    }

    public void setOverdraftAuthorization(BigDecimal overdraftAuthorization) {
        this.overdraftAuthorization = overdraftAuthorization;
    }

}
