package application;

import domain.WithdrawException;
import domain.dto.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {

    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        bankAccount = new BankAccount(100.0, 50.0); // Initial balance: 100, overdraft: 50
    }

    @Test
    void deposit_positiveAmount_shouldIncreaseBalance() {
        bankAccount.deposit(50.0);
        assertEquals(150.0, bankAccount.getBalance());
    }

    @Test
    void deposit_zeroAmount_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(0.0));
    }

    @Test
    void deposit_negativeAmount_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-10.0));
    }

    @Test
    void withdraw_withSufficientFunds_shouldDecreaseBalance() {
        bankAccount.withdraw(30.0);
        assertEquals(70.0, bankAccount.getBalance());
    }

    @Test
    void withdraw_withOverdraft_shouldAllowWithdrawal() {
        bankAccount.withdraw(120.0); // 100 - 120 = -20 (within overdraft limit of 50)
        assertEquals(-20.0, bankAccount.getBalance());
    }

    @Test
    void withdraw_exceedingOverdraft_shouldThrowException() {
        assertThrows(WithdrawException.class, () -> bankAccount.withdraw(160.0));
    }

    @Test
    void withdraw_zeroAmount_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(0.0));
    }

    @Test
    void withdraw_negativeAmount_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-10.0));
    }

    @Test
    void canWithdraw_withSufficientFunds_shouldReturnTrue() {
        assertTrue(bankAccount.canWithdraw(100.0));
    }

    @Test
    void canWithdraw_withInsufficientFunds_shouldReturnFalse() {
        assertFalse(bankAccount.canWithdraw(200.0));
    }

    @Test
    void canWithdraw_withOverdraft_shouldReturnTrue() {
        assertTrue(bankAccount.canWithdraw(120.0));
    }

    @Test
    void canWithdraw_exceedingOverdraft_shouldReturnFalse() {
        assertFalse(bankAccount.canWithdraw(160.0));
    }

    @Test
    void getInitialBalance_shouldReturnCorrectValue() {
        assertEquals(100.0, bankAccount.getBalance());
    }

    @Test
    void getOverdraftAuthorization_shouldReturnCorrectValue() {
        assertEquals(50.0, bankAccount.getOverdraftAuthorization());
    }
}
