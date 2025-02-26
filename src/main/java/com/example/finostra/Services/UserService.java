package com.example.finostra.Services;

import com.example.finostra.Entity.Requests.Verification.UserPhoneNumberRegistrationRequest;
import com.example.finostra.Entity.Roles.Role;
import com.example.finostra.Entity.User;
import com.example.finostra.Exceptions.UserBadRequestException;
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

    public User registerNewUser(UserPhoneNumberRegistrationRequest registrationDto) {
        // TODO : register New User
        return null;
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

