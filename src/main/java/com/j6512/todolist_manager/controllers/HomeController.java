package com.j6512.todolist_manager.controllers;

import com.j6512.todolist_manager.models.Task;
import com.j6512.todolist_manager.models.TodoList;
import com.j6512.todolist_manager.models.User;
import com.j6512.todolist_manager.models.data.TodoListRepository;
import com.j6512.todolist_manager.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

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

    @GetMapping("index")
    public String displayIndex(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = getUserFromSession(session);

        LocalDateTime dateObj = LocalDateTime.now();
        DateTimeFormatter formatDateObj = DateTimeFormatter.ofPattern("E, MMM dd yyyy hh:mm a");

        String formattedDate = dateObj.format(formatDateObj);

        model.addAttribute("title", "Todo List Manager");
        model.addAttribute("greeting", "Hello " + user.getUsername());
        model.addAttribute("listSize", "Total Amount of Todo Lists: " + user.getTodoLists().size());

        int totalTasks = 0;
        int totalCompletedTasks = 0;
        int totalInProgressTasks = 0;
        int totalOnHoldTasks = 0;
        for (TodoList todoList : user.getTodoLists()) {
            totalTasks += todoList.getTasks().size();

            for (Task task : todoList.getTasks()) {
                if (task.getStatus().getStatus().equals("In Progress")) {
                    totalInProgressTasks++;
                } else if (task.getStatus().getStatus().equals("Completed")) {
                    totalCompletedTasks++;
                } else {
                    totalOnHoldTasks++;
                }
            }
        }

        model.addAttribute("taskSize", "Total Amount of Tasks: " + totalTasks);
        model.addAttribute("completedTasks", "Total Amount of Tasks Completed: " + totalCompletedTasks);
        model.addAttribute("inProgressTasks", "Total Amount of Tasks In Progress: " + totalInProgressTasks);
        model.addAttribute("onHoldTasks", "Total Amount of Tasks On Hold: " + totalOnHoldTasks);
        model.addAttribute("date", formattedDate);

        return "index";
    }

    @GetMapping("timer")
    public String timer(Model model) {
        model.addAttribute("title", "timer");

        return "timer";
    }
}
