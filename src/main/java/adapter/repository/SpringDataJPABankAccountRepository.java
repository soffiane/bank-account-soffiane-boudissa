package adapter.repository;

import adapter.entity.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataJPABankAccountRepository extends JpaRepository<BankAccountEntity, Long> {
}
