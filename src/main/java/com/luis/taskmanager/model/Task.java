package com.luis.taskmanager.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data //used for lombok, automatically generates all getters,setters,constructors, and toString
@Document(collection = "tasks") //used to tell mongoDB this class maps to a collection called 'tasks'
public class Task {

    @Id //marks id field as primary key. This lets mongodb to auto generate this as an ObjectID
    private String id;
    private String title;
    private String description;
    private String status;
}
