package ru.netology.web;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateService {
    public String date (int plusDays, String pattern) {
        return LocalDate.now().plusDays(plusDays).format(DateTimeFormatter.ofPattern(pattern));
    }
}
