package app.develope.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import app.develope.model.User;

@Service
public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);

    User saveUser(User userDetails);

    User updateUser(User userDetails);

    Boolean deleteUser(String username);

    boolean userExists(String username);

    User findByUsername(String username);

    void clearRefreshToken(String username);

    List<User> getUsers();
}
