package io.github.tasktracker.task_tracker_cli.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.tasktracker.task_tracker_cli.model.Task;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final File file = new File("tasks.json");
    private final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .registerModule(new JavaTimeModule());

    public List<Task> loadTasks() throws IOException {
        if (!file.exists() || file.length() == 0) return new ArrayList<>();
        return new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Task[].class)));
    }

    public void saveTasks(List<Task> tasks) throws IOException {
        objectMapper.writeValue(file, tasks);
    }

    public void addTask(String description) throws IOException {
        List<Task> tasks = loadTasks();
        int nextId = tasks.stream().mapToInt(Task::getId).max().orElse(0) + 1;
        Task newTask = new Task(nextId, description, "todo", LocalDateTime.now(), LocalDateTime.now());
        tasks.add(newTask);
        saveTasks(tasks);
        System.out.println("Task added successfully (ID: " + nextId + ")");
    }

    public void updateTask(int id, String newDescription) throws IOException {
        List<Task> tasks = loadTasks();
        Optional<Task> taskOpt = tasks.stream().filter(t -> t.getId() == id).findFirst();

        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setDescription(newDescription);
            task.setUpdatedAt(LocalDateTime.now());
            saveTasks(tasks);
            System.out.println("Task updated successfully.");
        } else {
            System.out.println("Task with ID " + id + " not found.");
        }
    }

    public void deleteTask(int id) throws IOException {
        List<Task> tasks = loadTasks();
        boolean removed = tasks.removeIf(t -> t.getId() == id);
        if (removed) {
            saveTasks(tasks);
            System.out.println("Task deleted successfully.");
        } else {
            System.out.println("Task with ID " + id + " not found.");
        }
    }

    public void markTask(int id, String status) throws IOException {
        List<String> validStatuses = Arrays.asList("todo", "in-progress", "done");

        if (!validStatuses.contains(status.toLowerCase())) {
            System.out.println("Invalid status. Valid values are: todo, in-progress, done.");
            return;
        }

        List<Task> tasks = loadTasks();
        Optional<Task> taskOpt = tasks.stream().filter(t -> t.getId() == id).findFirst();

        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setStatus(status.toLowerCase());
            task.setUpdatedAt(LocalDateTime.now());
            saveTasks(tasks);
            System.out.println("Task marked as " + status + ".");
        } else {
            System.out.println("Task with ID " + id + " not found.");
        }
    }

    public void listTasks() throws IOException {
        List<Task> tasks = loadTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        printTasks(tasks);
    }

    public void listTasksByStatus(String status) throws IOException {
        List<Task> tasks = loadTasks().stream()
                .filter(t -> t.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());

        if (tasks.isEmpty()) {
            System.out.println("No tasks found with status: " + status);
            return;
        }
        printTasks(tasks);
    }

    public void printSummary() throws IOException {
        List<Task> tasks = loadTasks();
        Map<String, Long> summary = tasks.stream()
                .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));

        if (summary.isEmpty()) {
            System.out.println("No tasks to summarize.");
            return;
        }

        System.out.println("Task Summary:");
        summary.forEach((status, count) -> System.out.printf("  %s: %d%n", status, count));
    }

    private void printTasks(List<Task> tasks) {
        tasks.forEach(task ->
                System.out.printf("ID: %d | Description: %s | Status: %s | Created: %s | Updated: %s%n",
                        task.getId(), task.getDescription(), task.getStatus(),
                        task.getCreatedAt(), task.getUpdatedAt()));
    }

    public void markInProgress(int id) throws IOException {
        changeTaskStatus(id, "in-progress");
    }

    public void markDone(int id) throws IOException {
        changeTaskStatus(id, "done");
    }

    private void changeTaskStatus(int id, String newStatus) throws IOException {
        List<Task> tasks = loadTasks();
        Optional<Task> taskOpt = tasks.stream().filter(t -> t.getId() == id).findFirst();

        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setStatus(newStatus);
            task.setUpdatedAt(LocalDateTime.now());
            saveTasks(tasks);
            System.out.println("Task marked as " + newStatus + ".");
        } else {
            System.out.println("Task with ID " + id + " not found.");
        }
    }
}
