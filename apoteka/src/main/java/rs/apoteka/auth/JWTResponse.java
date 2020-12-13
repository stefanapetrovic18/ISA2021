package rs.apoteka.auth;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JWTResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private Collection<? extends GrantedAuthority> authorities;

    public JWTResponse(String token, String username, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.username = username;
        this.authorities = authorities;
    }
}
