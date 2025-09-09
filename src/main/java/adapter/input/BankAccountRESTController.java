package adapter.input;

import domain.WithdrawException;
import domain.dto.BankAccount;
import domain.port.input.CreateBankAccountUseCase;
import domain.port.input.DepositToBankAccountUseCase;
import domain.port.input.WithdrawFromBankAccountUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@RequestMapping(value = "/api/bank-account", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Bank Account API", description = "APIs for managing bank accounts")
public class BankAccountRESTController {

    private final CreateBankAccountUseCase createBankAccountUseCase;
    private final WithdrawFromBankAccountUseCase withdrawFromBankAccountUseCase;
    private final DepositToBankAccountUseCase depositToBankAccountUseCase;

    @Autowired
    public BankAccountRESTController(CreateBankAccountUseCase createBankAccountUseCase, WithdrawFromBankAccountUseCase withdrawFromBankAccountUseCase, DepositToBankAccountUseCase depositToBankAccountUseCase) {
        this.createBankAccountUseCase = createBankAccountUseCase;
        this.withdrawFromBankAccountUseCase = withdrawFromBankAccountUseCase;
        this.depositToBankAccountUseCase = depositToBankAccountUseCase;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new bank account",
            description = "Creates a new bank account with the provided details")
    @ApiResponse(responseCode = "201", description = "Successfully created bank account")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<BankAccount> createBankAccount(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Bank account details")
            @RequestBody BankAccount request) {
        BankAccount bankAccount = createBankAccountUseCase.createBankAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(bankAccount);
    }

    @PutMapping(value = "/deposit/{accountId}")
    @Operation(summary = "Deposit money into an account",
            description = "Deposits the specified amount into the account with the given ID")
    @ApiResponse(responseCode = "200", description = "Deposit successful")
    @ApiResponse(responseCode = "400", description = "Account not found")
    public ResponseEntity<String> deposit(
            @Parameter(description = "ID of the account to deposit into") @PathVariable Long accountId,
            @Parameter(description = "Amount to deposit") @RequestParam double amount) {
        try {
            depositToBankAccountUseCase.deposit(accountId, amount);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No account found");
        }
        return ResponseEntity.ok("Deposit successful");
    }


    @PutMapping(value = "/withdraw/{accountId}")
    @Operation(summary = "Withdraw money from an account",
            description = "Withdraws the specified amount from the account with the given ID")
    @ApiResponse(responseCode = "200", description = "Withdrawal successful")
    @ApiResponse(responseCode = "400", description = "Account not found or insufficient funds")
    public ResponseEntity<String> withdraw(
            @Parameter(description = "ID of the account to withdraw from") @PathVariable Long accountId,
            @Parameter(description = "Amount to withdraw") @RequestParam double amount) {
        try {
            withdrawFromBankAccountUseCase.withdraw(accountId, amount);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No account found");
        } catch (WithdrawException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have exceed your withdraw limit");
        }
        return ResponseEntity.ok("Withdraw successful");
    }
}
