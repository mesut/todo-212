package com.todo.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private Model model;

    @Test
    public void shouldLoginSuccessWhenNoError() {

        assertEquals(loginController.login(null, model), "login");
    }

    @Test
    public void shouldLoginFailWhenErrorOccured() {
        String returnVal = loginController.login("error", model);

        verify(model).addAttribute("error", "error");
        assertEquals(returnVal, "login");
    }

}