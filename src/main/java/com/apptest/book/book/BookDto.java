package com.apptest.book.book;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Integer id;
    private Integer userId;
    @NotBlank(message = "author cannot be null or empty")
    private String author;
    private String title;

    private LocalDate published;
    private LocalDate deletedAt;
}
