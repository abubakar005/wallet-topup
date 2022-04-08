package wallet.topup.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "gl")
public class GLEntity extends AbstractAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "gl_code")
    private Long glCode;

    @Column(name = "is_active")
    private Character active;

    public void setActive(boolean active) {
        this.active = active ? 'Y' : 'N';
    }

    public boolean isActive() {
        return active == 'Y' ? true : false;
    }
}
