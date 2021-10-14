package app.controller;

import app.dto.AuthDto;
import app.dto.UserDetailsDto;
import app.service.AuthService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private transient AuthService authService;

    @PostMapping("/login")
    public UserDetailsDto login(@Valid @RequestBody AuthDto auth) {
        return authService.login(auth);
    }

    @PostMapping("/refresh")
    public String refresh(Authentication auth) {
        return authService.refresh((String) auth.getPrincipal(), auth.getAuthorities());
    }
}
