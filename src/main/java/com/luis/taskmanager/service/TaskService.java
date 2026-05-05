package com.luis.taskmanager.service;

import com.luis.taskmanager.model.Task;
import com.luis.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    // get all tasks
    @Cacheable(value = "tasks")
    public List<Task> getAllTasks() {
        log.info("Fetching all tasks from MongoDB");
        return taskRepository.findAll();
    }

    // get task by ID
    public Optional<Task> getTaskById(String id){
        return taskRepository.findById(id);
    }

    // create new tasks
    @CacheEvict(value = "tasks", allEntries = true)
    public Task createTask(Task task){
        return taskRepository.save(task);
    }

    // update existing task
    @CacheEvict(value = "tasks", allEntries = true)
    public Task updateTask(String id, Task updatedTask){
        updatedTask.setId(id);
        return taskRepository.save(updatedTask);
    }

    // delete a task
    @CacheEvict(value = "tasks", allEntries = true)
    public void deleteTask(String id){
        taskRepository.deleteById(id);
    }
}
