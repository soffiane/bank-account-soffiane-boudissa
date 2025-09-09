package adapter.output;

import adapter.entity.BankAccountEntity;
import adapter.repository.SpringDataJPABankAccountRepository;
import domain.WithdrawException;
import domain.dto.BankAccount;
import domain.port.output.BankAccountRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.AccountNotFoundException;

@Repository
public class JPABankAccountRepository implements BankAccountRepositoryPort {

    @Autowired
    private SpringDataJPABankAccountRepository repository;

    @Override
    public BankAccount deposit(Long accountId, double amount) throws AccountNotFoundException {
        BankAccountEntity bankAccountEntity = repository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        BankAccount bankAccount = mapToDomain(bankAccountEntity);
        bankAccount.deposit(amount);
        BankAccountEntity save = repository.save(mapToEntity(bankAccount));
        return mapToDomain(save);
    }

    @Override
    public BankAccount withdraw(Long accountId, double amount) throws WithdrawException, AccountNotFoundException {
        BankAccountEntity bankAccountEntity = repository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        BankAccount bankAccount = mapToDomain(bankAccountEntity);
        bankAccount.withdraw(amount);
        BankAccountEntity save = repository.save(mapToEntity(bankAccount));
        return mapToDomain(save);
    }

    @Override
    public BankAccount createBankAccount(BankAccount bankAccount) {
        BankAccountEntity save = repository.save(mapToEntity(bankAccount));
        return mapToDomain(save);
    }

    private BankAccountEntity mapToEntity(BankAccount account) {
        BankAccountEntity entity = new BankAccountEntity();
        entity.setId(account.getId());
        entity.setBalance(account.getBalance());
        entity.setOverdraftAuthorization(
                account.getOverdraftAuthorization() != null
                        ? account.getOverdraftAuthorization()
                        : null
        );
        return entity;
    }

    private BankAccount mapToDomain(BankAccountEntity request) {
        return new BankAccount(request.getId(), request.getBalance(), request.getOverdraftAuthorization());
    }
}
