package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.FilmValid;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * @author ������� �������� 10.11.2022
 */
@Data
@AllArgsConstructor
/**
 * FilmValid - ��������� validator
 * */
@FilmValid
public class Film {
    Integer id; //������������� �������������
    @NotEmpty(message = "��� �� ����� ���� ������")
    String name; // ��������
    @Size(max = 200, message = "������������ ����� �������� 200 ��������")
    String description;//��������
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate releaseDate; //���� ������
    @Positive
    long duration; //����������������� ������
}
