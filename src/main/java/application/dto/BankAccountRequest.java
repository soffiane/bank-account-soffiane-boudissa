package application.dto;

import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

public class BankAccountRequest {

    @NotNull(message = "Initial balance is required")
    @DecimalMin(value = "0.0", message = "Initial balance cannot be negative")
    private BigDecimal initialBalance;

    @DecimalMin(value = "0.0", message = "Overdraft authorization cannot be negative")
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
