package ru.otus.homework.test.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import ru.otus.homework.service.PersonService;
import ru.otus.homework.service.IOService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Методы PersonService должны")
@SpringBootTest
public class PersonServiceTest {

    @MockBean
    private MessageSource messageSource;

    @MockBean
    private IOService ioService;

    @Autowired
    private PersonService personService;

    @BeforeEach
    void setUp() {
        given(messageSource.getMessage(any(), any(), any())).willReturn("some text");
    }

    @Test
    @DisplayName("два раза прочитать данные от пользователя")
    public void shouldInvokeIOServiceForRead() {
        personService.askAndReturnPersonData();
        verify(ioService, times(2)).readTextFromConsole();
    }

    @Test
    @DisplayName("два раза задать вопрос пользователю")
    public void shouldInvokeIOServiceForOut() {
        personService.askAndReturnPersonData();
        verify(ioService, times(2)).out("some text");
    }
}
