package rs.apoteka.service.intf.auth;

import rs.apoteka.auth.JWTResponse;
import rs.apoteka.entity.auth.LoginRequest;
import rs.apoteka.entity.auth.PasswordChangeRequest;
import rs.apoteka.entity.auth.RegistrationRequest;
import rs.apoteka.entity.auth.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    List<User> findValidatedAndEnabled();

    User getOne(Long id);

    User findByUsername(String username);

    List<User> getUsersWithoutType();

    User create(RegistrationRequest request) throws Exception;

    Boolean confirm(String token) throws Exception;

    JWTResponse login(LoginRequest request);

    String changePassword(PasswordChangeRequest request) throws Exception;

    User update(User user) throws Exception;

    Boolean delete(Long id);
}
