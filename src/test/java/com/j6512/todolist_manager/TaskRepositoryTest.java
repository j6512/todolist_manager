package com.j6512.todolist_manager;

import com.j6512.todolist_manager.models.Task;
import com.j6512.todolist_manager.models.TodoList;
import com.j6512.todolist_manager.models.User;
import com.j6512.todolist_manager.models.data.TaskRepository;
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
public class TaskRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TodoListRepository todoListRepository;

    @Autowired
    TaskRepository taskRepository;

    // testing to see if a task is added to the correct todolist belonging to the correct user
    @Test
    @Order(1)
    public void addTaskTest() {
        User user = User.builder()
                .username("testUsername")
                .passwordHash("testPassword")
                .build();

        TodoList todoList = TodoList.builder()
                .name("testTodoList")
                .user(user)
                .dateCreated(new Date())
                .build();

        Task task = Task.builder()
                .name("testTask")
                .description("testDescription")
                .todoList(todoList)
                .build();

        userRepository.save(user);
        todoListRepository.save(todoList);
        taskRepository.save(task);

        Assertions.assertThat(task.getTodoList().getId()).isEqualTo(todoList.getId());
    }

    // testing to see if editing a task's name and description works properly
    @Test
    @Order(2)
    public void editTaskTest() {
        Task task = taskRepository.findByName("testTask");

        task.setName("newTestTask");
        task.setDescription("newTestDescription");

        taskRepository.save(task);

        Assertions.assertThat(task.getName()).isEqualTo("newTestTask");
        Assertions.assertThat(task.getDescription()).isEqualTo("newTestDescription");
    }

    // testing to see if deleting a task works properly
    @Test
    @Order(3)
    public void deleteTaskTest() {
        Task task = taskRepository.findByName("newTestTask");
        taskRepository.deleteById(task.getId());

        Task task1 = null;

        Optional<Task> optionalTask = taskRepository.findById(task.getId());

        if (optionalTask.isPresent()) {
            task1 = optionalTask.get();
        }

        Assertions.assertThat(task1).isNull();
    }

    // clean up database by deleting the User and TodoList created to use for testing the Task
    @Test
    @Order(4)
    public void cleanUp() {
        TodoList todoList = todoListRepository.findByName("testTodoList");
        todoListRepository.deleteById(todoList.getId());

        User user = userRepository.findByUsername("testUsername");
        userRepository.deleteById(user.getId());

        User user1 = null;
        TodoList todoList1 = null;

        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
            user1 = optionalUser.get();
        }

        Optional<TodoList> optionalTodoList = todoListRepository.findById(todoList.getId());
        if (optionalTodoList.isPresent()) {
            todoList1 = optionalTodoList.get();
        }

        Assertions.assertThat(user1).isNull();
        Assertions.assertThat(todoList1).isNull();
    }
}
