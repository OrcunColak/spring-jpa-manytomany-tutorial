package com.colak.springtutorial.unidirectional.service;

import com.colak.springtutorial.unidirectional.jpa.Role;
import com.colak.springtutorial.unidirectional.jpa.User;
import com.colak.springtutorial.unidirectional.repository.RoleRepository;
import com.colak.springtutorial.unidirectional.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public User save(User user) {
        String email = user.getEmail();
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new RuntimeException(String.format("User with the email address '%s' already exists.", email));
        }

        // For each role, and fetch or create from the database
        Set<Role> authorities = user.getAuthorities();
        Set<Role> roles = authorities.stream()
                .map(Role::getAuthority)
                .map(roleName -> roleRepository.findByAuthority(roleName)
                        .orElseGet(() -> createNewRole(roleName))) // Create new role if not found
                .collect(Collectors.toSet());

        // Set the roles for the user
        user.setAuthorities(roles);

        return userRepository.save(user);
    }

    private Role createNewRole(String roleName) {
        Role newRole = new Role();
        newRole.setAuthority(roleName);
        return roleRepository.save(newRole);
    }

}
