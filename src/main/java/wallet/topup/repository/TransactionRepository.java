package wallet.topup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wallet.topup.entity.TransactionEntity;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {

    TransactionEntity findByChargeIdAndStatus(String chargeId, String status);
}
