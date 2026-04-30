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
}
