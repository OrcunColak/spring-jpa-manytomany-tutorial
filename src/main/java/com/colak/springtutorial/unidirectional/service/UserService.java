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
}
