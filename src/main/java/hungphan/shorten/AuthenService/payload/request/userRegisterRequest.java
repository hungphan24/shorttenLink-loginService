package hungphan.shorten.AuthenService.payload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class userRegisterRequest {

    @NotNull(message = "password not null")
    @Column(name = "password")
    private String password;

    @NotNull(message = "email not null")
    @Email(message = "Please enter correct format")
    @Column(name = "email")
    private String email;

    @NotNull(message = "username not null")
    @Column(name = "username")
    private String username;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
