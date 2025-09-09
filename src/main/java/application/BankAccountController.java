package application;

import application.dto.BankAccountRequest;
import application.dto.BankAccountResponse;
import domain.bankaccount.BankAccount;
import domain.bankaccount.BankAccountPort;
import domain.bankaccount.WithdrawException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/bank-account")
public class BankAccountController {

    private BankAccountPort bankAccountPort;

    @Autowired
    public BankAccountController(BankAccountPort bankAccountPort) {
        this.bankAccountPort = bankAccountPort;
    }


    @PostMapping
    public ResponseEntity<BankAccountResponse> createBankAccount(@RequestBody BankAccountRequest request){
        BankAccount bankAccount = bankAccountPort.createBankAccount();
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponse(bankAccount));
    }

    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<String> deposit(Long accountId, double amount){
        try {
            bankAccountPort.deposit(accountId, amount);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No account found");
        }
        return ResponseEntity.ok("Deposit successful");
    }


    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<String> withdraw(Long accountId, double amount){
        try {
            bankAccountPort.withdraw(accountId, amount);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No account found");
        } catch (WithdrawException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have exceed your withdraw limit");
        }
        return ResponseEntity.ok("Withdraw successful");
    }

    private BankAccountResponse mapToResponse(BankAccount account) {
        BankAccountResponse response = new BankAccountResponse();
        response.setAccountId(account.getId());
        response.setBalance(BigDecimal.valueOf(account.getBalance()));
        response.setOverdraftAuthorization(
                account.getOverdraftAuthorization() != null
                        ? BigDecimal.valueOf(account.getOverdraftAuthorization())
                        : null
        );
        return response;
    }
}
