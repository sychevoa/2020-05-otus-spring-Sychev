package ru.otus.homework.test.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.homework.service.IOService;
import ru.otus.homework.service.IOServiceConsole;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("класс IOServiceConsole")
@SpringBootTest
public class IOServiceConsoleTest {

    private IOService ioService;

    private final static String TEST_ONE = "Some text";

    private ByteArrayOutputStream bos;

    @BeforeEach
    void setUp() {
        bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));
        ioService = new IOServiceConsole();
    }

    @Test
    @DisplayName("должен корректно выводить информацию в консоль")
    void shouldOutToConsole(){
        ioService.out(TEST_ONE);
        assertThat(bos.toString()).isEqualTo(TEST_ONE + "\n");
    }
}
