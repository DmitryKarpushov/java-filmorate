package ru.yandex.practicum.filmorate.datavalidation;

import java.time.LocalDate;

/**
 * @author Дмитрий Карпушов 12.11.2022
 */
public class DateValidatorUsingDate{
    public static boolean isValidDate(LocalDate dateStr) {
        return dateStr.isBefore(LocalDate.of(1895, 12, 28));
    }
}
