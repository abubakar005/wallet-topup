package wallet.topup.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "wallet")
public class WalletEntity extends AbstractAuditing {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "wallet_type", nullable = false)
    private int walletType;

    @Column(name = "title")
    private String title;

    @Column(name = "is_active")
    private Character active;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @Column(name = "credit_balance")
    private Double creditBalance;

    @Column(name = "debit_balance")
    private Double debitBalance;

    @Column(name = "currency")
    private String currency;
}
