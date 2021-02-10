package org.example.web.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BookAuthorToRemove {

    @NotNull
    private String author;
}
