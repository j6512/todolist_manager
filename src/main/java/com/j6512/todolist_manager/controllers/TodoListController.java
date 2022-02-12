package com.j6512.todolist_manager.controllers;

import com.j6512.todolist_manager.models.Task;
import com.j6512.todolist_manager.models.TodoList;
import com.j6512.todolist_manager.models.User;
import com.j6512.todolist_manager.models.data.TaskRepository;
import com.j6512.todolist_manager.models.data.TodoListRepository;
import com.j6512.todolist_manager.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
public class TodoListController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TodoListRepository todoListRepository;

    @Autowired
    TaskRepository taskRepository;

    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("user");

        if (userId == null) {
            return null;
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }

    @GetMapping("view/all")
    public String displayAllTodoLists(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = getUserFromSession(session);
        model.addAttribute("user", user);

        if (user.getTodoLists() != null) {
            List<TodoList> todoLists = user.getTodoLists();
            model.addAttribute("todoLists", todoLists);
            model.addAttribute("title", "All Todo Lists");
        }

        return "view/all";
    }

    @GetMapping("view/todo-list/{todoListId}")
    public String displaySelectedTodoList(@PathVariable int todoListId, Model model) {
        Optional<TodoList> optionalTodoList = todoListRepository.findById(todoListId);
        TodoList todoList = (TodoList) optionalTodoList.get();

        model.addAttribute("title", "Viewing Todo List: " + todoList.getName());
        model.addAttribute("todoList", todoList);
        model.addAttribute("tasks", todoList.getTasks());
        model.addAttribute("date", todoList.getFormattedDate());

        return "view/todo-list";
    }

    @GetMapping("create/todo-list")
    public String displayTodoListCreateForm(Model model, HttpServletRequest request) {
        model.addAttribute(new TodoList());
        HttpSession session = request.getSession(false);

        if (session != null) {
            User user = getUserFromSession(session);

            model.addAttribute("user", user);
            model.addAttribute("title", "Create a List");
        }

        return "create/todo-list";
    }

    @PostMapping("create/todo-list")
    public String processTodoListCreateForm(@ModelAttribute @Valid TodoList newTodoList, Errors errors, Model model,
                                            HttpServletRequest request) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Create a List");

            return "create/todo-list";
        }

        HttpSession session = request.getSession(false);
        User user = getUserFromSession(session);
        model.addAttribute("user", user);

        newTodoList.setUser(user);
        newTodoList.setDateCreated(new Date());

        todoListRepository.save(newTodoList);

        return "redirect:/view/all";
    }

    @GetMapping("edit/todo-list/{todoListId}")
    public String displayTodoListEditForm(@PathVariable int todoListId, Model model) {
        Optional<TodoList> optionalTodoList = todoListRepository.findById(todoListId);
        TodoList todoList = (TodoList) optionalTodoList.get();

        model.addAttribute("title", "Edit Todo List: " + todoList.getName());
        model.addAttribute("todoList", todoList);

        return "edit/todo-list";
    }

    @PostMapping("edit/todo-list/{todoListId}")
    public String processTodoListEditForm(@PathVariable int todoListId, @RequestParam String name,
                                          @ModelAttribute @Valid TodoList todoList, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Editi Todo List: " + todoList.getName());

            return "edit/todo-list";
        }

        Optional<TodoList> optionalTodoList = todoListRepository.findById(todoListId);
        todoList = (TodoList) optionalTodoList.get();
        todoList.setName(name);
        todoListRepository.save(todoList);

        return "redirect:/view/all";
    }

    @GetMapping("delete/todo-list/{todoListId}")
    public String displayTodoListDeleteForm(@PathVariable int todoListId, Model model) {
        Optional<TodoList> optionalTodoList = todoListRepository.findById(todoListId);
        TodoList todoList = (TodoList) optionalTodoList.get();

        model.addAttribute("todoList", todoList);
        model.addAttribute("title", "Delete List: " + todoList.getName());

        return "delete/todo-list";
    }

    @PostMapping("delete/todo-list/{todoListId}")
    public String processTodoListDeleteForm(@PathVariable int todoListId) {

        List<Task> tasks = (List<Task>) taskRepository.getAllTasksByTodoListId(todoListId);
        Iterator<Task> taskHolder = tasks.iterator();

        while (taskHolder.hasNext()) {
            int taskId = taskHolder.next().getId();

            taskRepository.deleteById(taskId);
        }

        todoListRepository.deleteById(todoListId);

        return "redirect:/view/all";
    }
}
