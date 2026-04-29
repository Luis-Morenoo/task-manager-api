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

import static org.assertj.core.api.Assertions.assertThat;
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
}
