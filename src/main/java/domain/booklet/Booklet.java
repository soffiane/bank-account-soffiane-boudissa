package domain.booklet;

import domain.bankaccount.BankAccount;
import jakarta.persistence.Entity;

@Entity
public class Booklet extends BankAccount {
    private final Double limit;

    public Booklet(Double balance, Double limit) {
        super(balance, 0.0);
        this.limit = limit;
    }
}
