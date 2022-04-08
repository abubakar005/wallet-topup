package wallet.topup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wallet.topup.entity.FeeEntity;

@Repository
public interface FeeRepository extends JpaRepository<FeeEntity, Long> {
}
