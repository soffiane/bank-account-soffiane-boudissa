package application.dto;

import java.math.BigDecimal;

public class BankAccountResponse {

    private Long accountId;
    private Double balance;
    private Double overdraftAuthorization;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getOverdraftAuthorization() {
        return overdraftAuthorization;
    }

    public void setOverdraftAuthorization(Double overdraftAuthorization) {
        this.overdraftAuthorization = overdraftAuthorization;
    }

}
