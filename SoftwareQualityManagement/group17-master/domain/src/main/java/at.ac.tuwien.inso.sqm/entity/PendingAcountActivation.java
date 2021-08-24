package at.ac.tuwien.inso.sqm.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
public class PendingAcountActivation {

    @Id
    private String id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private UisUserEntity forUser;

    protected PendingAcountActivation() {

    }

    public PendingAcountActivation(UisUserEntity forUser) {
        this(UUID.randomUUID().toString(), forUser);
    }

    public PendingAcountActivation(String id, UisUserEntity forUser) {
        this.id = id;
        this.forUser = forUser;
    }

    public String getId() {
        return id;
    }

    public UisUserEntity getForUser() {
        return forUser;
    }

    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PendingAcountActivation that = (PendingAcountActivation) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        return forUser != null ? forUser.equals(that.forUser) :
                that.forUser == null;

    }

    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (forUser != null ? forUser.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "PendingAcountActivation{" + "ID='" + id + '\'' + ", forUser=" +
                forUser + '}';
    }
}
