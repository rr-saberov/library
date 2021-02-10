package org.example.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Integer id;
    @Size(min = 4, max = 25)
    private String author;
    @Size(min = 4, max = 25)
    private String title;
    @Digits(integer = 4, fraction = 0)
    private Integer size;
}
