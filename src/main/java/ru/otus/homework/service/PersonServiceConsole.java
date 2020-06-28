package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.PersonData;

import java.util.Locale;

@Service
public class PersonServiceConsole implements PersonService {

    private final IOService ioService;
    private final MessageSource messageSource;

    private final String locale;

    public PersonServiceConsole(MessageSource messageSource, @Value("${locale}") String locale,
                                IOService ioService) {
        this.messageSource = messageSource;
        this.locale = locale;
        this.ioService = ioService;
    }

    @Override
    public PersonData askAndReturnPersonData() {
        String askForFirstName = messageSource.getMessage("person.fist-name", null, new Locale(locale, locale));
        ioService.out(askForFirstName);
        String firstName = ioService.readTextFromConsole();

        String askForSecondName = messageSource.getMessage("person.second-name", null, new Locale(locale, locale));
        ioService.out(askForSecondName);
        String lastName = ioService.readTextFromConsole();

        return new PersonData(firstName, lastName);
    }
}