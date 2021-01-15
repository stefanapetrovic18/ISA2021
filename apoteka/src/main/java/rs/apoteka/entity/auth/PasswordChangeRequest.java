package rs.apoteka.entity.auth;

public class PasswordChangeRequest {
    String password;
    String repeatPassword;

    public PasswordChangeRequest() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
