package com.luis.taskmanager.dto;

import lombok.Data;

// DTO - Data Transfer Object
// This is what clients send in POST and PUT requests
// Notice there is no 'id' field - clients should never control the ID
// This prevents mass assignments attacks (SonarCloud rule java:S4684)
@Data
public class TaskRequest {
    private String title;
    private String description;
    private String status;
}
