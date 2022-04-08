package wallet.topup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wallet.topup.entity.GLEntity;

@Repository
public interface GLRepository extends JpaRepository<GLEntity, Long> {
}
