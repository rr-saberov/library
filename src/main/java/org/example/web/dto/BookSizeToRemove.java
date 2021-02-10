package org.example.web.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BookSizeToRemove {

    @NotNull
    private Integer size;
}
