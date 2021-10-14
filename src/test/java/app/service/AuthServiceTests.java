package app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import app.dto.AuthDto;
import app.dto.UserDetailsDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("AuthService")
@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {

    @Mock
    transient AuthenticationManager authManager;

    @InjectMocks
    transient AuthService authService;

    @BeforeEach
    void beforeEach() {
        ReflectionTestUtils.setField(authService, "jwtSecret", "<ApiSecretKey>");
        ReflectionTestUtils.setField(authService, "jwtExpiration", 3600L);
    }

    @Test
    @DisplayName("#login returns the user session")
    void login() {
        AuthDto credentials = new AuthDto("username", "password");
        UserDetailsDto userSession = new UserDetailsDto(credentials.username, credentials.password, List.of());
        Authentication auth = new UsernamePasswordAuthenticationToken(userSession, "", List.of());

        when(authManager.authenticate(any(Authentication.class))).thenReturn(auth);

        assertThat(authService.login(credentials))
            .extracting(UserDetailsDto::getUsername, UserDetailsDto::getAuthorities)
            .isEqualTo(List.of(credentials.username, List.of()));
    }

    @Test
    @DisplayName("#refresh returns a new jwt token")
    void refresh() {
        assertThat(authService.refresh("username", List.of())).isInstanceOf(String.class);
    }

    @Test
    @DisplayName("#validate returns user credentials")
    void validate() {
        Authentication auth = new UsernamePasswordAuthenticationToken("username", "", List.of());
        String bearerToken =
            "Bearer eyJhbGciOiJIUzUxMiJ9." +
            "eyJzdWIiOiJ1c2VybmFtZSIsImlhdCI6MTYzMzc5NDQwMSwiZXhwIjoxOTQ5MTU0NDAxLCJhdXRob3JpdGllcyI6W119." +
            "KR1DBB-ui8ycBhIcRhzOwhcqCNC2DTy5aDYlKeARg1_I0-Aa_KiBHvfZEJbsH4oO3vQxn5yaHmnxtIrlJOtoiQ";

        assertThat(authService.validate(bearerToken)).isEqualTo(auth);
    }
}
