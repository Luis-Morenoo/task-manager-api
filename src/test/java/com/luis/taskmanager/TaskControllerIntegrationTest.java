package com.luis.taskmanager;

import com.luis.taskmanager.model.Task;
import com.luis.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // boots the full spring application context
@AutoConfigureMockMvc // sets up MockMvc which lets you fire real HTTP requests witohout starting an actual server
public class TaskControllerIntegrationTest {

    @Autowired // spring injects these automatically
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach // runs before every test
    void setUp(){
        taskRepository.deleteAll();
    }

    @Test
    void shouldCreateAndRetrieveTask() throws Exception{
        // create a task
        String taskJson = """
                {
                    "title": "Integration Test Task",
                    "description": "Testing full stack",
                    "status": "pending"
                }
                """;

        mockMvc.perform(post("/api/tasks") // fires a real HTTP request to your controller
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskJson))
                .andExpect(status().isOk()) // verifies the response
                .andExpect(jsonPath("$.title").value("Integration Test Task"))
                .andExpect(jsonPath("$.status").value("pending"));

        // verify it was saved
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Integration Test Task"));
    }

    @Test
    void shouldGetTaskById() throws Exception {
        // First create a task to retrieve
        Task task = new Task();
        task.setTitle("Find Me");
        task.setDescription("By ID");
        task.setStatus("pending");
        Task saved = taskRepository.save(task);

        // Now retrieve it by ID
        mockMvc.perform(get("/api/tasks/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Find Me"));
    }

    @Test
    void shouldReturn404WhenTaskNotFound() throws Exception {
        // Try to get a task that doesn't exist
        mockMvc.perform(get("/api/tasks/nonexistentid123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateTask() throws Exception {
        // First create a task to update
        Task task = new Task();
        task.setTitle("Original Title");
        task.setDescription("Original Description");
        task.setStatus("pending");
        Task saved = taskRepository.save(task);

        // Now update it
        String updatedJson = """
            {
                "title": "Updated Title",
                "description": "Updated Description",
                "status": "completed"
            }
            """;

        mockMvc.perform(put("/api/tasks/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.status").value("completed"));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        // First create a task to delete
        Task task = new Task();
        task.setTitle("Delete Me");
        task.setDescription("This will be deleted");
        task.setStatus("pending");
        Task saved = taskRepository.save(task);

        // Delete it
        mockMvc.perform(delete("/api/tasks/" + saved.getId()))
                .andExpect(status().isNoContent());

        // Verify it's gone
        mockMvc.perform(get("/api/tasks/" + saved.getId()))
                .andExpect(status().isNotFound());
    }
}
