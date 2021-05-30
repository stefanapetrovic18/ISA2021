package rs.apoteka.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import rs.apoteka.exception.PasswordNotChangedException;
import rs.apoteka.exception.UserNotEnabledException;
import rs.apoteka.exception.UserNotValidatedException;
import rs.apoteka.service.impl.auth.UserDetailsServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JWTAuthTokenFilter.class);
    @Autowired
    JWTProvider provider;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwt(httpServletRequest);
            if (jwt != null && provider.validateToken(jwt)) {
                String username = provider.getUsernameFromToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                userCheck(userDetails);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            logger.error("Can not set user authentication! -> Message: {}", e.getMessage());
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getJwt(HttpServletRequest request) {
        String header = request.getHeader("AuthToken");
        if (header != null && header.startsWith("Bearer ")) {
            return header.replace("Bearer ", "");
        }
        return null;
    }

    private void userCheck(UserDetails userDetails) {
        try {
            // Korisnik ne moze pristupiti funkcionalnostima ukoliko mu je onemogucen pristup.
            if (!userDetails.isEnabled()) {
                throw new UserNotEnabledException();
            }
            // Korisnik ne moze pristupiti funkcionalnostima ukoliko nije promenio sifru nakon registracije.
            if (!userDetails.isCredentialsNonExpired()) {
                throw new PasswordNotChangedException();
            }
            // Korisnik ne moze pristupiti funkcionalnostima ukoliko nije odradio validaciju nakon registracije.
            if (!userDetails.isAccountNonLocked()) {
                throw new UserNotValidatedException();
            }
        } catch (UserNotEnabledException e) {
            logger.error("User not enabled! -> Message: {}", e.getMessage());
        } catch (PasswordNotChangedException e) {
            logger.error("User has not changed his password! -> Message: {}", e.getMessage());
        } catch (UserNotValidatedException e) {
            logger.error("User not validated! -> Message: {}", e.getMessage());
        }
    }
}
