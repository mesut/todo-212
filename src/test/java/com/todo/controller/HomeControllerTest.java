package com.todo.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

    @InjectMocks
    private HomeController homeController;

    @Test
    public void shouldRedirectTodayCalendar() {
        assertEquals(homeController.home(), "redirect:/todos/today");
    }
}