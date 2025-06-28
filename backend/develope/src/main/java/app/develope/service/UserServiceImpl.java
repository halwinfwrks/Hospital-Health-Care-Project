package app.develope.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import app.develope.model.User;
import app.develope.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        List<GrantedAuthority> authorities = List.of(user.getRoles().split(","))
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.trim()))
                .collect(Collectors.toList());

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(new Date().toString());
        user.setUpdatedAt(new Date().toString());
        userRepository.save(user);
        return userRepository.findByUsername(user.getUsername());
    }

    @Override
    public User updateUser(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User ID cannot be null for update");
        }
        userRepository.save(user);
        user.setUpdatedAt(new Date().toString());
        return userRepository.findByUsername(user.getUsername());
    }

    @Override
    public Boolean deleteUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean userExists(String username) {
        if (userRepository.existsByUsername(username)) {
            return true;
        }
        if (userRepository.existsByEmail(username)) {
            return true;
        }
        return false;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void clearRefreshToken(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setRemeberToken(null);
            userRepository.save(user);
        } else {
            System.out.println("User not found: " + username);
        }
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
