package at.ac.tuwien.inso.sqm.controller.forms;

import at.ac.tuwien.inso.sqm.entity.UisUserEntity;
import at.ac.tuwien.inso.sqm.entity.UserAccountEntity;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.ScriptAssert;

@ScriptAssert(lang = "javascript",
        script = "_this.password === _this.repeatPassword",
        message = "{repeat.password.error}")
public class AccountActivationForm {

    @NotEmpty
    private String password;

    @NotEmpty
    private String repeatPassword;

    protected AccountActivationForm() {

    }

    public AccountActivationForm(String password, String repeatPassword) {
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    public UserAccountEntity toUserAccount(UisUserEntity user) {
        return new UserAccountEntity(user.getIdentificationNumber(), password);
    }

    public String getPassword() {
        return password;
    }

    public AccountActivationForm setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public AccountActivationForm setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AccountActivationForm that = (AccountActivationForm) o;

        if (password != null ? !password.equals(that.password) :
                that.password != null) {
            return false;
        }
        return repeatPassword != null ?
                repeatPassword.equals(that.repeatPassword) :
                that.repeatPassword == null;

    }

    public int hashCode() {
        int result = password != null ? password.hashCode() : 0;
        result = 31 * result +
                (repeatPassword != null ? repeatPassword.hashCode() : 0);
        return result;
    }

    public boolean verifyPasswordsMatch() {
        return password.equals(repeatPassword);
    }
}
