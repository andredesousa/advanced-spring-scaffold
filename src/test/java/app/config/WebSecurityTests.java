package app.config;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.filter.AuthorizationFilter;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@DisplayName("WebSecurity")
@ExtendWith(MockitoExtension.class)
public class WebSecurityTests {

    @Mock
    transient AuthorizationFilter jwtTokenFilter;

    @Mock
    transient BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    transient WebSecurity webSecurity;

    @Test
    @DisplayName("#configure calls UserDetailsService and use BCryptPasswordEncoder")
    void configureAuthenticationManagerBuilder() throws Exception {
        AuthenticationManagerBuilder auth = mock(AuthenticationManagerBuilder.class);
        DaoAuthenticationConfigurer dao = mock(DaoAuthenticationConfigurer.class);
        when(auth.userDetailsService(any())).thenReturn(dao);

        webSecurity.configure(auth);

        verify(auth).userDetailsService(any());
        verify(dao).passwordEncoder(passwordEncoder);
    }

    @Test
    @DisplayName("#configure sets http security configuration")
    void configureHttpSecurity() throws Exception {
        ObjectPostProcessor<Object> objectPostProcessor = mock(ObjectPostProcessor.class);
        AuthenticationManagerBuilder authenticationBuilder = mock(AuthenticationManagerBuilder.class);
        Map<Class<?>, Object> sharedObjects = new HashMap<Class<?>, Object>();

        HttpSecurity httpSecurity = spy(new HttpSecurity(objectPostProcessor, authenticationBuilder, sharedObjects));

        webSecurity.configure(httpSecurity);

        verify(httpSecurity).cors();
        verify(httpSecurity).csrf();
        verify(httpSecurity).sessionManagement();
        verify(httpSecurity).authorizeRequests();
        verify(httpSecurity).addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
