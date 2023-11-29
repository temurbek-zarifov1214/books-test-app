package com.apptest.book.auth;

import com.apptest.book.crud.ResponseDto;
import com.apptest.book.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {

        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticateRequest request) {

        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/get")
    public ResponseDto<UserDto> get(@RequestParam Integer id) {
        return this.authenticationService.get(id);
    }

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDto<List<UserDto>> getAllUsers() {
        return this.authenticationService.getAllUsers();
    }
}
