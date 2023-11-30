package com.apptest.book.auth;


import com.apptest.book.book.BookMapper;
import com.apptest.book.config.JwtService;
import com.apptest.book.crud.ErrorDto;
import com.apptest.book.crud.ResponseDto;
import com.apptest.book.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserValidation userValidation;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookMapper bookMapper;
    private final JwtService jwtService;

    public AuthenticationResponse register(RegisterRequest request) {

        List<ErrorDto> error = this.userValidation.validate(request);
        if (!error.isEmpty()) {
            return null;
        }

        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .createdAt(LocalDateTime.now())
                .build();
        this.userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    public ResponseEntity<User> get(Integer id) {
        Optional<User> optional = this.userRepository.findByIdAndDeletedAtIsNull(id);
        if (optional.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        User user = optional.get();
        user.getBooks().stream().map(this.bookMapper::toDto);
        return ResponseEntity.ok().body(user);
    }

    public ResponseEntity<List<User>> getAllUsers() {
        List<User> list = this.userRepository.getAllUSers();
        List<User> userList = new ArrayList<>();
        for (User user : list) {
            userList.add(user);
        }
        return ResponseEntity.ok().body(userList);
    }
}
