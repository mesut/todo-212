package com.todo.validator;

import com.todo.dto.UserDto;
import com.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class UserDtoValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserDtoValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(UserDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto userDto = (UserDto) target;
        validatePassword(userDto, errors);
        validateUsername(userDto, errors);
    }

    private void validatePassword(UserDto userDto, Errors errors) {
        if (!userDto.getPassword().equals(userDto.getPasswordConfirm())) {
            errors.rejectValue("passwordConfirm", "password.no_match", "Passwords do not match!");
        }
    }

    private void validateUsername(UserDto userDto, Errors errors) {
        if (userService.findUserByUsername(userDto.getUsername()) != null) {
            errors.rejectValue("username", "username.exists", "Username already exists!");
        }
    }

}
