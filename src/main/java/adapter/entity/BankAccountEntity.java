package adapter.entity;

import jakarta.persistence.*;

@Entity
public class BankAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "balance", nullable = false)
    private Double balance;
    @Column(name = "overdraft_authorization")
    private Double overdraftAuthorization;

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getOverdraftAuthorization() {
        return overdraftAuthorization;
    }

    public void setOverdraftAuthorization(Double overdraftAuthorization) {
        this.overdraftAuthorization = overdraftAuthorization;
    }
}

