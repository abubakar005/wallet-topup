package wallet.topup.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity
@Table(name = "customer")
public class CustomerEntity extends AbstractAuditing {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "identity_value")
    private String identity;

    @Column(name = "gender")
    private String gender;

    @Column(name = "is_active")
    private Character active;

    @Column(name = "gl_code")
    private Long glCode;

    @PrePersist
    private void ensureId(){
        this.setId(UUID.randomUUID().toString());
    }

    public void setActive(boolean active) {
        this.active = active ? 'Y' : 'N';
    }

    public boolean isActive() {
        return active == 'Y' ? true : false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerEntity)) return false;
        CustomerEntity that = (CustomerEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(mobileNo, that.mobileNo) &&
                Objects.equals(identity, that.identity) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(active, that.active) &&
                Objects.equals(glCode, that.glCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, mobileNo, identity, gender, active, glCode);
    }
}
