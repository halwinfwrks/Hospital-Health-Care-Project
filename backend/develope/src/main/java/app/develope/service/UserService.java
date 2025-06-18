package app.develope.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import app.develope.model.User;

@Service
public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);

    void saveUser(User userDetails);

    void updateUser(User userDetails);

    void deleteUser(String username);

    boolean userExists(String username);
}
