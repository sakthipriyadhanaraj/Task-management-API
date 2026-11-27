package com.example.task_management;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
public class TaskController {


    private final TaskRepository taskRepository;
    private final TaskService taskService;

    public TaskController(TaskRepository taskRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    @GetMapping("/api/tasks")
    public List<Task> getTasks() {
        return taskService.getTasks();
    }
    public record CreateTaskRequest(String title) {}

    @PostMapping("/api/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody CreateTaskRequest request) {
        return taskService.createTask(request.title());
    }

    @GetMapping("/api/tasks/{id}")
    public Task getTaskById(@PathVariable("id") Long id) {
        return taskService.getTaskById(id);
    }
    public record UpdateStatusRequest(String status) {}

    @PatchMapping("/api/tasks/{id}/status")
    public Task updateStatus(
            @PathVariable("id") Long id,
            @RequestBody UpdateStatusRequest request) {

        String status = request.status();

        if (!"TODO".equals(status)
                && !"IN_PROGRESS".equals(status)
                && !"DONE".equals(status)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Status must be TODO, IN_PROGRESS, or DONE");
        }

        Task task = getTaskById(id);
        task.setStatus(status);
        return taskRepository.save(task);
    }

    @DeleteMapping("/api/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable("id") Long id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }



}