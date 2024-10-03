package com.colak.springtutorial.unidirectional.service;

import com.colak.springtutorial.unidirectional.jpa.Role;
import com.colak.springtutorial.unidirectional.jpa.User;
import com.colak.springtutorial.unidirectional.repository.RoleRepository;
import com.colak.springtutorial.unidirectional.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User saveUserWithRoles(User user, List<String> roleNames) {
        for (String roleName : roleNames) {
            // Check if the role already exists in the database
            Role existingRole = roleRepository.findByAuthority(roleName)
                    .orElseGet(() -> {
                        // Create a new role if it doesn't exist
                        Role newRole = new Role();
                        newRole.setAuthority(roleName);
                        return roleRepository.save(newRole); // Save the new role
                    });

            user.getAuthorities().add(existingRole); // Add the role (either existing or newly created)
        }
        return userRepository.save(user); // Save user
    }
}
