package com.j6512.todolist_manager.models.data;

import com.j6512.todolist_manager.models.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {

    Iterable<Task> getAllTasksByTodoListId(int todo_list_id);
}
