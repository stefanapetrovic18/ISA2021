package rs.apoteka.service.intf;

import rs.apoteka.auth.JWTResponse;
import rs.apoteka.entity.auth.LoginRequest;
import rs.apoteka.entity.auth.RegistrationRequest;
import rs.apoteka.entity.auth.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    List<User> findValidatedAndEnabled();

    User getOne(Long id);

    User findByUsername(String username);

    User create(RegistrationRequest request) throws Exception;

    Boolean confirm(String token) throws Exception;

    JWTResponse login(LoginRequest request);

    User update(User user) throws Exception;

    Boolean delete(Long id);
}
