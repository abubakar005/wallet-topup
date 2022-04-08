package wallet.topup.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "transaction")
public class TransactionEntity extends AbstractAuditing {

    @Id
    @Column(name = "uuid")
    private String id;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "payer_id")
    private CustomerEntity payer;

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "payee_id")
    private CustomerEntity payee;

    @OneToOne
    @JoinColumn(name = "fee_id")
    private FeeEntity fee;

    @Column(name = "status")
    private String status;

    @Column(name = "currency")
    private String currency;

    @Column(name = "charge_id")
    private String chargeId;

    @Column(name = "txn_detail")
    private String txnDetail;
}
