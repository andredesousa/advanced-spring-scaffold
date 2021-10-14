package app.service;

import app.dto.AuthDto;
import app.dto.UserDetailsDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collection;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final String jwtRoles = "authorities";

    private static final String bearerPrefix = "Bearer ";

    @Autowired
    private transient AuthenticationManager authManager;

    @Value("${jwt.secret}")
    private transient String jwtSecret;

    @Value("${jwt.expiration}")
    private transient Long jwtExpiration;

    /**
     * Login method.
     * @param auth - The user credentials.
     * @return The user details.
     */
    public UserDetailsDto login(AuthDto auth) {
        AbstractAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(auth.username, auth.password);
        Authentication authenticate = authManager.authenticate(credentials);
        UserDetailsDto user = (UserDetailsDto) authenticate.getPrincipal();
        String accessToken = refresh(user.getUsername(), user.getAuthorities());

        return new UserDetailsDto(user.getUsername(), "", user.getAuthorities(), accessToken);
    }

    /**
     * Refresh the token.
     * @param username - The user name.
     * @param authorities - The user roles.
     * @return A new token.
     */
    public String refresh(String username, Collection<? extends GrantedAuthority> authorities) {
        return Jwts
            .builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration * 1000))
            .claim(jwtRoles, authorities)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    /**
     * Validates the token.
     * @param bearerToken - The bearer token string.
     * @return Authentication object.
     */
    public Authentication validate(String bearerToken) {
        String token = bearerToken != null ? bearerToken.substring(bearerPrefix.length()) : null;
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) claims.get(jwtRoles);

        return new UsernamePasswordAuthenticationToken(username, "", authorities);
    }
}
