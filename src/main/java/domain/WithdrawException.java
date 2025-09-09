package domain;

public class WithdrawException extends RuntimeException{
    public WithdrawException(String insufficientFundsForWithdrawal) {
        super(insufficientFundsForWithdrawal);
    }
}
