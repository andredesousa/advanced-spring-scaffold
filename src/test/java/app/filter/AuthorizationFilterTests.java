package app.filter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import app.service.AuthService;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("AuthorizationFilter")
@ExtendWith(MockitoExtension.class)
public class AuthorizationFilterTests {

    @Mock
    transient AuthService authService;

    @InjectMocks
    transient AuthorizationFilter authFilter;

    @Test
    @DisplayName("#doFilter have been called")
    void doFilter() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        authFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }
}
