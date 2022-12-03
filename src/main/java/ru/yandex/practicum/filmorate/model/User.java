package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * @author ������� �������� 10.11.2022
 */
@Data
@AllArgsConstructor
public class User {
    Integer id; //������������� �������������
    @NotEmpty
    @Email(message = "����������� ������ Email")
    String email; // ����������� �����
    @NotEmpty
    /**
     * ��������� ��������� ����� ���������� ���������, �� �������� ������� �������� � ������.
     * */
    @Pattern(regexp = "^\\S*")
    String login; // ����� ������������
    String name; // ��� ��� �����������
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @PastOrPresent
    LocalDate birthday; // ���� ��������
}
