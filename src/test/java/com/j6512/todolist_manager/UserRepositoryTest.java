package com.j6512.todolist_manager;

import com.j6512.todolist_manager.models.User;
import com.j6512.todolist_manager.models.data.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    // testing to see if user is added to database properly
    @Test
    @Order(1)
    public void addUserTest() {
        User user = User.builder()
                .username("testUsername")
                .passwordHash("testPassword")
                .build();

        userRepository.save(user);

        Assertions.assertThat(user.getId()).isGreaterThan(0);
    }

    // testing to see if user is deleted properly
    @Test
    @Order(2)
    public void deleteUserTest() {
        User user = userRepository.findByUsername("testUsername");

        userRepository.deleteById(user.getId());

        User user1 = null;

        Optional<User> optionalUser = userRepository.findById(user.getId());

        if (optionalUser.isPresent()) {
            user1 = optionalUser.get();
        }

        Assertions.assertThat(user1).isNull();
    }
}

