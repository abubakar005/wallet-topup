package wallet.topup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wallet.topup.entity.TransactionRequestsEntity;

@Repository
public interface TransactionRequestsRepository extends JpaRepository<TransactionRequestsEntity, Long> {
}
