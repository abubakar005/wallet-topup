package wallet.topup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wallet.topup.entity.CustomerEntity;
import wallet.topup.entity.WalletEntity;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, String> {

    WalletEntity findByActiveAndCustomerIdAndId(Character active, String customerId, String id);

    WalletEntity findByCustomer(CustomerEntity customer);
}
