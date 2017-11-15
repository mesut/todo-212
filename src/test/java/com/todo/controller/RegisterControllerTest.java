package com.todo.controller;

import com.todo.dto.UserDto;
import com.todo.service.UserService;
import com.todo.validator.UserDtoValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegisterControllerTest {

    @InjectMocks
    private RegisterController registerController;

    @Mock
    private UserService userService;

    @Mock
    private UserDtoValidator userDtoValidator;

    @Mock
    private Model model;

    @Mock
    private WebDataBinder webDataBinder;

    @Mock
    private BindingResult bindingResult;

    @Test
    public void shouldInitBinder() {
        registerController.initBinder(webDataBinder);
        verify(webDataBinder).addValidators(userDtoValidator);
    }

    @Test
    public void shouldRegister() {
        assertEquals(registerController.register(model), "register");
        verify(model).addAttribute(eq("userDto"), anyObject());
    }

    @Test
    public void shouldProcessRegisterSuccessWhenNoError() {
        UserDto userDto = new UserDto();
        when(bindingResult.hasErrors()).thenReturn(false);

        assertEquals(registerController.processRegister(userDto, bindingResult), "login");
        verify(userService).createAndSave(userDto);
    }

    @Test
    public void shouldProcessRegisterFailWhenErrorOccured() {
        UserDto userDto = new UserDto();
        when(bindingResult.hasErrors()).thenReturn(true);

        assertEquals(registerController.processRegister(userDto, bindingResult), "register");
        verifyNoMoreInteractions(userService);
    }

}