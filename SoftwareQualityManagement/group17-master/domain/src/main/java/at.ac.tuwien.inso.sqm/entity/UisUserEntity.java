package at.ac.tuwien.inso.sqm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class UisUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String identificationNumber;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserAccountEntity account;

    protected UisUserEntity() {

    }

    public UisUserEntity(String identificationNumber, String name, String email,
            UserAccountEntity account) {
        this.identificationNumber = identificationNumber;
        this.name = name;
        this.email = email;
        this.account = account;
    }

    public Long getId() {

        return id;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    public UserAccountEntity getAccount() {
        return account;
    }

    @Transient
    public boolean isActivated() {
        return account != null;
    }

    public final void activate(UserAccountEntity accountToActivate) {
        assert (this.account == null);

        this.account = accountToActivate;
        adjustRole(accountToActivate);
    }

    protected abstract void adjustRole(UserAccountEntity account);

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UisUserEntity uisUser = (UisUserEntity) o;

        if (getId() != null ? !getId().equals(uisUser.getId()) :
                uisUser.getId() != null) {
            return false;
        }
        if (getIdentificationNumber() != null ? !getIdentificationNumber()
                .equals(uisUser.getIdentificationNumber()) :
                uisUser.getIdentificationNumber() != null) {
            return false;
        }
        if (getName() != null ? !getName().equals(uisUser.getName()) :
                uisUser.getName() != null) {
            return false;
        }
        if (getEmail() != null ? !getEmail().equals(uisUser.getEmail()) :
                uisUser.getEmail() != null) {
            return false;
        }
        return account != null ? account.equals(uisUser.account) :
                uisUser.account == null;

    }

    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getIdentificationNumber() != null ?
                getIdentificationNumber().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "UisUserEntity{" + "id=" + id + ", identificationNumber='" +
                identificationNumber + '\'' + ", name='" + name + '\'' +
                ", email='" + email + '\'' + ", account=" + account + '}';
    }
}
