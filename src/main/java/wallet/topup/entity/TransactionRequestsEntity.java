package wallet.topup.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "transaction_requests")
public class TransactionRequestsEntity extends AbstractAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "request_data")
    private String requestData;

    @Column(name = "response_data")
    private String responseData;

    @Column(name = "error_code")
    private int errorCode;

    @Column(name = "error_msg")
    private String errorMsg;
}
