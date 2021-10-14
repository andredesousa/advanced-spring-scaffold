package app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class UserDetailsDto implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    private String accessToken;

    private Collection<GrantedAuthority> authorities;

    public UserDetailsDto(String username, String password, Collection<GrantedAuthority> authorities) {
        this.authorities = new ArrayList<GrantedAuthority>(authorities);
        this.username = username;
        this.password = password;
    }

    public UserDetailsDto(String username, String password, Collection<GrantedAuthority> authorities, String token) {
        this.authorities = new ArrayList<GrantedAuthority>(authorities);
        this.accessToken = token;
        this.username = username;
        this.password = password;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = new ArrayList<GrantedAuthority>(authorities);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<GrantedAuthority>(authorities);
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
