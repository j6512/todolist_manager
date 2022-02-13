package com.j6512.todolist_manager;

import com.j6512.todolist_manager.models.TodoList;
import com.j6512.todolist_manager.models.User;
import com.j6512.todolist_manager.models.data.TodoListRepository;
import com.j6512.todolist_manager.models.data.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TodoListRepositoryTest {

    @Autowired
    TodoListRepository todoListRepository;

    @Autowired
    UserRepository userRepository;


    // testing to see if a todolist is added and saved to a user properly
    @Test
    @Order(1)
    public void addTodoListToUser() {
        User user = User.builder()
                .username("testUsername")
                .passwordHash("testPassword")
                .build();

        TodoList todoList = TodoList.builder()
                .name("testTodoList")
                .user(user)
                .dateCreated(new Date())
                .build();

        userRepository.save(user);
        todoListRepository.save(todoList);

        Assertions.assertThat(todoList.getUser()).isEqualTo(user);
    }

    // testing to see if editing a todolist's name works properly
    @Test
    @Order(2)
    public void editTodoListName() {
        TodoList todoList = todoListRepository.findByName("testTodoList");

        todoList.setName("newTestTodoList");

        todoListRepository.save(todoList);

        Assertions.assertThat(todoList.getName()).isEqualTo("newTestTodoList");
    }

    // testing to see if deleting a todolist from a user works properly
    @Test
    @Order(3)
    public void deleteTodoList() {
        TodoList todoList = todoListRepository.findByName("newTestTodoList");
        todoListRepository.deleteById(todoList.getId());

        Optional<TodoList> optionalTodoList = todoListRepository.findById(todoList.getId());

        TodoList todoList1 = null;

        if (optionalTodoList.isPresent()) {
            todoList1 = optionalTodoList.get();
        }

        Assertions.assertThat(todoList1).isNull();
    }

    // clean up database by deleting the User created for testing the TodoList
    @Test
    @Order(4)
    public void cleanUp() {
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
