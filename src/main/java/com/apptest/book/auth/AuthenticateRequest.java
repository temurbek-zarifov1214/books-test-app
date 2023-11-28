package com.apptest.book.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateRequest {
    @NotBlank(message = "password cannot be null or empty")
    private String password;
    @NotBlank(message = "email cannot be null or empty")
    private String email;
}
