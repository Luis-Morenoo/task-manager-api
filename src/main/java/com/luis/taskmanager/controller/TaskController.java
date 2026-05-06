package com.luis.taskmanager.controller;

import com.luis.taskmanager.dto.TaskRequest;
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
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        // Sanitize the ID before logging to prevent log injection attacks (SonarCloud S5145)
        // User-controlled input could contain newline characters that forge fake log entries
        String sanitizedId = id.replaceAll("[\n\r\t]", "_");
        log.info("GET /api/tasks/{} - Fetching task by ID", sanitizedId);
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("GET /api/tasks/{} - Task not found", sanitizedId);
                    return ResponseEntity.notFound().build();
                });
    }

    // POST create task
    @PostMapping
    public Task createTask(@RequestBody TaskRequest taskRequest) {
        // Sanitize user-provided title before logging to prevent log injection
        String sanitizedTitle = taskRequest.getTitle().replaceAll("[\n\r\t]", "_");
        log.info("POST /api/tasks - Creating new task: {}", sanitizedTitle);
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskRequest.getStatus());
        return taskService.createTask(task);
    }

    // PUT update task
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable String id, @RequestBody TaskRequest taskRequest) {
        String sanitizedId = id.replaceAll("[\n\r\t]", "_");
        log.info("PUT /api/tasks/{} - Updating task", sanitizedId);
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskRequest.getStatus());
        return taskService.updateTask(id, task);
    }

    // DELETE task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        String sanitizedId = id.replaceAll("[\n\r\t]", "_");
        log.info("DELETE /api/tasks/{} - Deleting task", sanitizedId);
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
