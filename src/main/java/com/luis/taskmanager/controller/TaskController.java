package com.luis.taskmanager.controller;

import com.luis.taskmanager.model.Task;
import com.luis.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //tells spring-boot this class handles HTTP requests and that every method returns data directly as JSON
@RequestMapping("/api/tasks") //sets the base URL for all endpoints in this class
@RequiredArgsConstructor //Lombok injects TaskService through the constructor
public class TaskController {
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;

    // GET all tasks
    @GetMapping
    public List<Task> getAllTasks(){
        log.info("GET /api/tasks - Fetching all tasks"); //log.info() normal operations
        return taskService.getAllTasks();
    }

    // GET task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id){
        log.info("GET /api/tasks/{} - Fetching task by ID", id);
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("GET /api/tasks/{} - Task not found", id); //log.warn() something unexpected but not breaking
                    return ResponseEntity.notFound().build();
                });
    }

    // POST create task
    @PostMapping
    public Task createTask(@RequestBody Task task){
        log.info("POST /api/tasks - Creating new task: {}", task.getTitle());
        return taskService.createTask(task);
    }

    // PUT update task
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable String id, @RequestBody Task task){
        log.info("PUT /api/tasks/{} - Updating task", id);
        return taskService.updateTask(id, task);
    }

    // DELETE task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id){
        log.info("DELETE /api/tasks/{} - Deleting task", id);
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
