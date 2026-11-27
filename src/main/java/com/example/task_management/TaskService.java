package com.example.task_management;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No task found"));
    }

    public Task createTask(String title) {
        if (title == null || title.isBlank() || title.strip().length() > 255) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Title must be 1 to 255 characters");
        }

        return taskRepository.save(new Task(title.strip()));
    }
}