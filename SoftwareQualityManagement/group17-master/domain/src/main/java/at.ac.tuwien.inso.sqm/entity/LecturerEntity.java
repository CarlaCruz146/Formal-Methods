package at.ac.tuwien.inso.sqm.entity;

import org.jboss.aerogear.security.otp.api.Base32;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class LecturerEntity extends UisUserEntity {

    @ManyToMany(mappedBy = "lecturers")
    private final List<Subject> subjects = new ArrayList<>();

    private String twoFactorSecret;

    protected LecturerEntity() {

    }

    public LecturerEntity(String identificationNumber, String name,
            String email) {
        this(identificationNumber, name, email, null);
    }

    public LecturerEntity(String identificationNumber, String name,
            String email, UserAccountEntity account) {
        super(identificationNumber, name, email, account);
        this.twoFactorSecret = Base32.random();
    }

    @Override
    protected void adjustRole(UserAccountEntity account) {
        account.setRole(Role.LECTURER);
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public String getTwoFactorSecret() {
        return twoFactorSecret;
    }

    public void setTwoFactorSecret(String twoFactorSecret) {
        this.twoFactorSecret = twoFactorSecret;
    }
}
