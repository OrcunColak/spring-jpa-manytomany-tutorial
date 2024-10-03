package com.colak.springtutorial.unidirectional.service;

import com.colak.springtutorial.unidirectional.jpa.Role;
import com.colak.springtutorial.unidirectional.jpa.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    // When user is saved DataIntegrityViolationException is thrown because Role is not saved
    @Test
    void saveUser() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("password");
        Set<Role> roleSet = Set.of(new Role("role1"));
        user.setAuthorities(roleSet);

        assertThatThrownBy(() -> userService.save(user))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    // When user is saved roles are also saved
    @Test
    void saveUserWithRoles() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("password");

        User savedUser = userService.saveUserWithRoles(user, List.of("role2"));
        for (Role role : savedUser.getAuthorities()) {
            assertThat(role.getId()).isNotNull();
        }
    }
}
