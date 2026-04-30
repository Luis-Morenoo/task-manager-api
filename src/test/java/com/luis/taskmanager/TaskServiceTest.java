package com.luis.taskmanager;

import com.luis.taskmanager.model.Task;
import com.luis.taskmanager.repository.TaskRepository;
import com.luis.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void shouldReturnAllTasks() {
        // Arrange
        Task task = new Task();
        task.setId("1");
        task.setTitle("Test Task");
        task.setDescription("Test Task");
        task.setStatus("Pending");

        when(taskRepository.findAll()).thenReturn(List.of(task));

        // Act
        List<Task> result = taskService.getAllTasks();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Task");
    }

    @Test
    void shouldReturnTaskById() {
        // Arrange
        Task task = new Task();
        task.setId("2");
        task.setTitle("Test Task");
        task.setDescription("Test Task");
        task.setStatus("Pending");

        when(taskRepository.findById("2")).thenReturn(Optional.of(task));

        // Act
        Optional<Task> result = taskService.getTaskById("2");

        // Assert
        assertThat(result).isPresent(); // checks that the optional has a value and not empty
        assertThat(result.get().getId()).isEqualTo("2");
        assertThat(result.get().getTitle()).isEqualTo("Test Task");
    }

    @Test
    void shouldCreateTask() {
        // Arrange
        Task task = new Task();
        task.setTitle("New Task");
        task.setDescription("New Description");
        task.setStatus("Pending");

        when(taskRepository.save(task)).thenReturn(task);

        // Act
        Task result = taskService.createTask(task);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("New Task");
    }

    @Test
    void shouldUpdateTask() {
        // Arrange
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Task");
        updatedTask.setDescription("Updated Description");
        updatedTask.setStatus("Completed");

        when(taskRepository.save(updatedTask)).thenReturn(updatedTask);

        // Act
        Task result = taskService.updateTask("1", updatedTask);

        // Assert
        assertThat(result.getTitle()).isEqualTo("Updated Task");
        assertThat(result.getStatus()).isEqualTo("Completed");
    }

    @Test
    void shouldDeleteTask() {
        // Arrange
        String taskId = "1";

        // Act
        taskService.deleteTask(taskId);

        // Assert
        verify(taskRepository).deleteById(taskId); // for delete there is nothing returned so we verify the repository method was actually called
    }

}
