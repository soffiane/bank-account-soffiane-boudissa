package domain.booklet;

import domain.bankaccount.BankAccount;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;

@Entity
public class Booklet extends BankAccount {
    @Column(name = "deposit_limit")
    private final Double depositLimit;

    public Booklet(Double balance, Double depositLimit) {
        super(balance, 0.0);
        this.depositLimit = depositLimit;
    }
}
