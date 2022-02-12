package com.j6512.todolist_manager.models.data;

import com.j6512.todolist_manager.models.TodoList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepository extends CrudRepository<TodoList, Integer> {
}
