package hungphan.shorten.AuthenService.payload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class userLoginRequest {
    @NotNull(message = "password not null")
    @Column(name = "password")
    private String password;

    @NotNull(message = "email not null")
    @Email(message = "Please enter correct format")
    @Column(name = "email")
    private String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
