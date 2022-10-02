package app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import app.dto.AuthDto;
import app.dto.UserDetailsDto;
import app.service.AuthService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@DisplayName("AuthController")
@ExtendWith(MockitoExtension.class)
public class AuthControllerTests {

    @Mock
    transient AuthService authService;

    @InjectMocks
    transient AuthController authController;

    @Test
    @DisplayName("#login returns the user session")
    void login() {
        AuthDto auth = new AuthDto("username", "password");
        UserDetailsDto userSession = new UserDetailsDto("username", "password", List.of());

        when(authService.login(auth)).thenReturn(userSession);

        assertThat(authController.login(auth)).isEqualTo(userSession);
    }

    @Test
    @DisplayName("#refresh returns a new token")
    void refresh() {
        Authentication auth = new UsernamePasswordAuthenticationToken("username", "password", List.of());
        String token = "xxx.yyy.zzz";

        when(authController.refresh(auth)).thenReturn(token);

        assertThat(authController.refresh(auth)).isEqualTo(token);
    }
}
