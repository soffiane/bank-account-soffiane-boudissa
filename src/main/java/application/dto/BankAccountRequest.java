package application.dto;

import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

public class BankAccountRequest {

    private BigDecimal initialBalance;

    private BigDecimal overdraftAuthorization;

    // Getters and Setters
    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public BigDecimal getOverdraftAuthorization() {
        return overdraftAuthorization;
    }

    public void setOverdraftAuthorization(BigDecimal overdraftAuthorization) {
        this.overdraftAuthorization = overdraftAuthorization;
    }
}
