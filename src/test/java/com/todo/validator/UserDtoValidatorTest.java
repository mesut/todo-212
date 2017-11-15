package com.todo.validator;

import com.todo.dto.UserDto;
import com.todo.model.User;
import com.todo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.Errors;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDtoValidatorTest {

    @InjectMocks
    private UserDtoValidator userDtoValidator;

    @Mock
    private UserService userService;

    @Mock
    private Errors errors;

    @Test
    public void shouldNotValidatePasswordWhenPasswordsNoMathc() {
        userDtoValidator.validate(new UserDto("a", "p1", "p2"), errors);

        verify(errors).rejectValue("passwordConfirm", "password.no_match", "Passwords do not match!");
    }

    @Test
    public void shouldNotValidateWhenUserAlreadyExist() {
        when(userService.findUserByUsername("username")).thenReturn(new User());
        userDtoValidator.validate(new UserDto("username", "p1", "p1"), errors);
        verify(errors).rejectValue("username", "username.exists", "Username already exists!");
    }

    @Test
    public void shouldValidateSuccessfully() {
        UserDto user = new UserDto("a", "p1", "p1");
        userDtoValidator.validate(user, errors);

        when(userService.findUserByUsername("a")).thenReturn(null);

        userDtoValidator.validate(user, errors);

        verifyZeroInteractions(errors);


    }
}