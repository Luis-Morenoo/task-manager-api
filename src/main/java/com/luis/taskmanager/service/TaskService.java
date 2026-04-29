package com.luis.taskmanager.service;

import com.luis.taskmanager.model.Task;
import com.luis.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    // get all tasks
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    // get task by ID
    public Optional<Task> getTaskById(String id){
        return taskRepository.findById(id);
    }

    // create new tasks
    public Task createTask(Task task){
        return taskRepository.save(task);
    }

    // update existing task
    public Task updateTask(String id, Task updatedTask){
        updatedTask.setId(id);
        return taskRepository.save(updatedTask);
    }

    // delete a task
    public void deleteTask(String id){
        taskRepository.deleteById(id);
    }
}
