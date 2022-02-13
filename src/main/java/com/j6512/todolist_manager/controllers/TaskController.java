package com.j6512.todolist_manager.controllers;

import com.j6512.todolist_manager.models.Task;
import com.j6512.todolist_manager.models.TaskStatus;
import com.j6512.todolist_manager.models.TodoList;
import com.j6512.todolist_manager.models.data.TaskRepository;
import com.j6512.todolist_manager.models.data.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class TaskController {

    @Autowired
    TodoListRepository todoListRepository;

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("create/task/{todoListId}")
    public String displayTodoListAddTaskForm(@PathVariable int todoListId, Model model) {
        Optional<TodoList> optionalTodoList = todoListRepository.findById(todoListId);
        TodoList todoList = (TodoList) optionalTodoList.get();

        model.addAttribute("title", "Add a Task");
        model.addAttribute("todoList", todoList);
        model.addAttribute("statuses", TaskStatus.values());
        model.addAttribute(new Task());

        return "create/task";
    }

    @PostMapping("create/task/{todoListId}")
    public String processTodoListAddTaskForm(@PathVariable int todoListId, @ModelAttribute @Valid Task newTask,
                                             Errors errors, Model model,
                                             @RequestParam TaskStatus status) {
        Optional<TodoList> optionalTodoList = todoListRepository.findById(todoListId);
        TodoList todoList = (TodoList) optionalTodoList.get();

        if (errors.hasErrors()) {
            model.addAttribute("statuses", TaskStatus.values());
            return "create/task";
        }

        model.addAttribute("todoList", todoList);
        newTask.setTodoList(todoList);
        newTask.setStatus(status);
        taskRepository.save(newTask);

        model.addAttribute("tasks", todoList.getTasks());
        model.addAttribute("title", "Add Tasks");

        return "view/todo-list";
    }

    @GetMapping("edit/task/{todoListId}/{taskId}")
    public String displayTodoListEditTaskForm(@PathVariable int todoListId, @PathVariable int taskId, Model model) {

        Optional<TodoList> optionalTodoList = todoListRepository.findById(todoListId);
        TodoList todoList = (TodoList) optionalTodoList.get();
        model.addAttribute("todoList", todoList);

        Optional<Task> optionalTask = taskRepository.findById(taskId);
        Task task = (Task) optionalTask.get();
        model.addAttribute("task", task);

        model.addAttribute("statuses", TaskStatus.values());
        model.addAttribute("title", "Edit task: " + task.getName());

        return "edit/task";
    }

    @PostMapping("edit/task/{todoListId}/{taskId}")
    public String processTodoListEditForm(@ModelAttribute @Valid Task newTask, Errors errors, Model model,
                                          @PathVariable int todoListId, @PathVariable int taskId,
                                          @RequestParam String name, @RequestParam String description,
                                          @RequestParam TaskStatus status) {

        Optional<TodoList> optionalTodoList = todoListRepository.findById(todoListId);
        TodoList todoList = (TodoList) optionalTodoList.get();

        Optional<Task> optionalTask = taskRepository.findById(taskId);

        if (errors.hasErrors()) {
            model.addAttribute("todoList", todoList);
            model.addAttribute("title", "Edit task: " + optionalTask.get().getName());
            model.addAttribute("statuses", TaskStatus.values());

            return "edit/task";
        }

        newTask = optionalTask.get();
        newTask.setName(name);
        newTask.setDescription(description);
        newTask.setStatus(status);
        taskRepository.save(newTask);

        model.addAttribute("todoList", todoList);
        model.addAttribute("tasks", todoList.getTasks());

        return "view/todo-list";
    }

    @GetMapping("delete/task/{todoListId}/{taskId}")
    public String displayTodoListDeleteForm(@PathVariable int todoListId, @PathVariable int taskId, Model model) {

        Optional<Task> optionalTask = taskRepository.findById(taskId);
        Task task = optionalTask.get();

        model.addAttribute("title", "Delete Task: " + task.getName());
        model.addAttribute("task", task);

        return "delete/task";
    }

    @PostMapping("delete/task/{todoListId}/{taskId}")
    public String processTodoListDeleteForm(@PathVariable int todoListId, @PathVariable int taskId, Model model) {

        taskRepository.deleteById(taskId);

        Optional<TodoList> optionalTodoList = todoListRepository.findById(todoListId);
        TodoList todoList = (TodoList) optionalTodoList.get();

        model.addAttribute("todoList", todoList);
        model.addAttribute("tasks", todoList.getTasks());

        return "view/todo-list";
    }
}
