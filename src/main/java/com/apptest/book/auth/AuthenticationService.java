package com.apptest.book.auth;


import com.apptest.book.config.JwtService;
import com.apptest.book.crud.ErrorDto;
import com.apptest.book.crud.ResponseDto;
import com.apptest.book.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserValidation userValidation;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
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

    public ResponseDto<UserDto> get(Integer id) {
        Optional<User> optional = this.userRepository.findByIdAndDeletedAtIsNull(id);
        if (optional.isEmpty()) {
            return ResponseDto.<UserDto>builder()
                    .code(-1)
                    .message("User is not found")
                    .build();
        }
        return ResponseDto.<UserDto>builder()
                .success(true)
                .message("Ok")
                .data(this.userMapper.toDtoWithBook(optional.get()))
                .build();
    }

    public ResponseDto<List<UserDto>> getAllUsers() {
        List<User> list = this.userRepository.getAllUSers();
        if (list.isEmpty()) {
            return ResponseDto.<List<UserDto>>builder()
                    .code(-1)
                    .message("users are not")
                    .build();
        }
        return ResponseDto.<List<UserDto>>builder()
                .success(true)
                .message("Ok")
                .data(list.stream().map(this.userMapper::toDtoWithBook).toList())
                .build();
    }
}
