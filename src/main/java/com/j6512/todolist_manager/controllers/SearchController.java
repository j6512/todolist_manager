package com.j6512.todolist_manager.controllers;

import com.j6512.todolist_manager.models.Task;
import com.j6512.todolist_manager.models.TodoList;
import com.j6512.todolist_manager.models.User;
import com.j6512.todolist_manager.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class SearchController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoListRepository todoListRepository;

    @Autowired
    private TaskRepository taskRepository;

    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("user");

        if (userId == null) {
            return null;
        }

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return null;
        }

        return optionalUser.get();
    }

    @GetMapping("search")
    public String displaySearchForm(Model model) {
        model.addAttribute("title", "Search");

        return "search";
    }

    @PostMapping("search")
    public String processSearchForm(Model model, HttpServletRequest request,
                                    @RequestParam String searchType, @RequestParam String searchTerm) {
        HttpSession session = request.getSession(false);
        User user = getUserFromSession(session);
        model.addAttribute("user", user);
        model.addAttribute("title", "Searching");

        Iterable<TodoList> todoLists;
        Iterable<Task> tasks;

        if (searchType.toLowerCase().equals("list")) {
            todoLists = TodoListData.findByColumnAndValue(searchType, searchTerm, todoListRepository.findAll());

            model.addAttribute("todoLists", todoLists);
        } else if (searchType.toLowerCase().equals("task")) {
            tasks = TaskData.findByColumnAndValue(searchType, searchTerm, taskRepository.findAll());

            model.addAttribute("tasks", tasks);
        }

        return "search";
    }
}
