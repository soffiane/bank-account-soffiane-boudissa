package domain.port.input;

import domain.dto.BankAccount;

public interface CreateBankAccountUseCase {
    BankAccount createBankAccount(BankAccount bankAccount);
}
