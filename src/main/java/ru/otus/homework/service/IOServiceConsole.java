package ru.otus.homework.service;

import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceConsole implements IOService {

    private final PrintStream out;
    private final Scanner scanner;

    public IOServiceConsole() {
        this.out = System.out;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String read() {

        return scanner.nextLine();
    }

    @Override
    public void out(String text) {
        out.println(text);
    }
}
