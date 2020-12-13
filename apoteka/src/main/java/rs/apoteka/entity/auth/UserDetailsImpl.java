package rs.apoteka.entity.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username; //TODO: email validacija
    @JsonIgnore
    private String password;
    private String forename;
    private String surname;
    private String address;
    private String city;
    private String country;
    private String phone; //TODO: regex

    private Boolean enabled;
    private Boolean validated;

    public Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl() {
    }

    public UserDetailsImpl(User user, List<GrantedAuthority> authorities) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.forename = user.getForename();
        this.surname = user.getSurname();
        this.address = user.getAddress();
        this.city = user.getCity();
        this.country = user.getCountry();
        this.phone = user.getPhone();
        this.enabled = user.getEnabled();
        this.validated = user.getValidated();
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(
                role -> new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());
        return new UserDetailsImpl(user, authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled & validated;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled & validated;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled & validated;
    }

    @Override
    public boolean isEnabled() {
        return enabled & validated;
    }
}
