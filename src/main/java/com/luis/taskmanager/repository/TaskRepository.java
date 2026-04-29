package com.luis.taskmanager.repository;

import com.luis.taskmanager.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
// this repository is for the Task class, and its ID type is String
public interface TaskRepository extends MongoRepository<Task, String> {
}
