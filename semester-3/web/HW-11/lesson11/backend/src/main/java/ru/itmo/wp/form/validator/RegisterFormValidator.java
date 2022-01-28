package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.RegisterForm;
import ru.itmo.wp.service.UserService;

/**
 * @author Pavel Lymar
 */
@Component
public class RegisterFormValidator implements Validator {
    private final UserService userService;

    public RegisterFormValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean supports(Class<?> clazz) {
        return RegisterForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            RegisterForm registerForm = (RegisterForm) target;
            if (!userService.isLoginVacant(registerForm.getLogin())) {
                errors.rejectValue("login", "login.is-in-use", "login is in use already");
            }
            if (registerForm.getPassword().length() < 4) {
                errors.rejectValue("password", "password.is-empty", "password is empty");
            }
        }
    }
}
