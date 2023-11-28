package com.apptest.book.user;

import com.apptest.book.book.Book;
import com.apptest.book.book.BookDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Integer id;
    @NotBlank(message = "firstname cannot be null or empty")
    private String firstName;
    @NotBlank(message = "lastname cannot be null or empty")
    private String lastName;
    @Column(unique = true)
    @NotBlank(message = "email cannot be null or empty")
    private String email;
    @NotBlank(message = "password cannot be null or empty")
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    private List<BookDto> books;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

}
