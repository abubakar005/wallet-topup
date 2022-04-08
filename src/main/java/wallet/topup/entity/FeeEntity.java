package wallet.topup.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "fee")
public class FeeEntity extends AbstractAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "currency")
    private String currency;
}
