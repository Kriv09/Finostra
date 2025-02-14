package com.example.finostra.Services;

import com.example.finostra.Entity.DTO.UserRegistrationDto;
import com.example.finostra.Entity.Roles.Role;
import com.example.finostra.Entity.User;
import com.example.finostra.Exceptions.UserBadRequestException;
import com.example.finostra.Exceptions.UserNotFoundException;
import com.example.finostra.Repositories.RoleRepository;
import com.example.finostra.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewUser(UserRegistrationDto registrationDto) {
        if (userRepository.findByUsername(registrationDto.getUsername()) != null) {
            throw new UserNotFoundException("User already exists");
        }

        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setEnabled(true);

        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }

        user.getRoles().add(userRole);
        return userRepository.save(user);
    }

    public void assignRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username).get();
        if (user == null) {
            throw new UserBadRequestException("User not found");
        }

        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new IllegalArgumentException("Role not found");
        }

        user.getRoles().add(role);
        userRepository.save(user);
    }

}

