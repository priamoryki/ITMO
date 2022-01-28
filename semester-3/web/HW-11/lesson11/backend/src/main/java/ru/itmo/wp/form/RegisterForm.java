package ru.itmo.wp.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Pavel Lymar
 */
public class RegisterForm {
    @NotEmpty
    @Size(min = 2, max = 24)
    @Pattern(regexp = "[a-zA-Z]{2,24}", message = "Expected Latin letters")
    private String login;

    @NotEmpty
    @Size(min = 4, max = 60)
    private String password;

    @NotEmpty
    @Size(min = 1, max = 100)
    @Pattern(regexp = "[a-zA-Z]{2,24}", message = "Expected Latin letters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
