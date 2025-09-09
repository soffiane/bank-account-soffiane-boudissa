package domain.dto;

import domain.WithdrawException;

public class BankAccount {

    private Long id;
    private Double balance;
    private Double overdraftAuthorization;

    public BankAccount() {
        this.balance = 0.0;
        this.overdraftAuthorization = null;
    }

    public BankAccount(Double balance, Double overdraftAuthorization) {
        this.balance = balance;
        this.overdraftAuthorization = overdraftAuthorization;
    }

    public BankAccount(Long id, Double balance, Double overdraftAuthorization) {
        this.id = id;
        this.balance = balance;
        this.overdraftAuthorization = overdraftAuthorization;
    }

    public Long getId() {
        return id;
    }

    public Double getBalance() {
        return balance;
    }

    public Double getOverdraftAuthorization() {
        return overdraftAuthorization;
    }

    public void setId(long id){
        this.id = id;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        if (canWithdraw(amount)) {
            this.balance -= amount;
        } else {
            throw new WithdrawException("Insufficient funds for withdrawal");
        }
    }

    public boolean canWithdraw(double amount) {
        if (overdraftAuthorization != null) {
            return (balance - amount) >= -overdraftAuthorization;
        }
        return amount <= balance;
    }
}
