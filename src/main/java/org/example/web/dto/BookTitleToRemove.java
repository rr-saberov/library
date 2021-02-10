package org.example.web.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BookTitleToRemove {

    @NotNull
    private String title;
}
