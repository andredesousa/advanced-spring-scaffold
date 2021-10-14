package app.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@DisplayName("WebApi")
@ExtendWith(MockitoExtension.class)
public class WebApiTests {

    @InjectMocks
    transient WebApi webAPI;

    @Test
    @DisplayName("#passwordEncoder returns a BCryptPasswordEncoder instance")
    void passwordEncoder() {
        assertThat(webAPI.passwordEncoder()).usingRecursiveComparison().isEqualTo(new BCryptPasswordEncoder());
    }

    @Test
    @DisplayName("#requestLoggingFilter returns a CommonsRequestLoggingFilter instance")
    void requestLoggingFilter() {
        assertThat(webAPI.requestLoggingFilter()).isInstanceOf(CommonsRequestLoggingFilter.class);
    }
}
