package io.github.tasktracker.task_tracker_cli.cli;

import io.github.tasktracker.task_tracker_cli.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TaskCliRunner implements CommandLineRunner {

    @Autowired
    private TaskService taskService;

    @Override
    public void run(String... args) throws Exception {
        if (args.length == 0) {
            printUsage();
            return;
        }

        String command = args[0];

        try {
            switch (command) {
                case "add":
                    if (args.length < 2) {
                        System.out.println("Usage: add \"task description\"");
                        return;
                    }
                    taskService.addTask(args[1]);
                    break;

                case "update":
                    if (args.length < 3) {
                        System.out.println("Usage: update <id> \"new description\"");
                        return;
                    }
                    taskService.updateTask(Integer.parseInt(args[1]), args[2]);
                    break;

                case "delete":
                    if (args.length < 2) {
                        System.out.println("Usage: delete <id>");
                        return;
                    }
                    taskService.deleteTask(Integer.parseInt(args[1]));
                    break;

                case "mark-in-progress":
                    if (args.length < 2) {
                        System.out.println("Usage: mark-in-progress <id>");
                        return;
                    }
                    taskService.markInProgress(Integer.parseInt(args[1]));
                    break;

                case "mark-done":
                    if (args.length < 2) {
                        System.out.println("Usage: mark-done <id>");
                        return;
                    }
                    taskService.markDone(Integer.parseInt(args[1]));
                    break;

                case "list":
                    if (args.length == 1) {
                        taskService.listTasks();
                    } else {
                        taskService.listTasksByStatus(args[1]);
                    }
                    break;

                default:
                    System.out.println("Unknown command: " + command);
                    printUsage();
            }
        } catch (Exception e) {
            System.out.println("Error executing command: " + e.getMessage());
        }
    }

    private void printUsage() {
        System.out.println("Usage:");
        System.out.println("  add \"task description\"");
        System.out.println("  update <id> \"new description\"");
        System.out.println("  delete <id>");
        System.out.println("  mark-in-progress <id>");
        System.out.println("  mark-done <id>");
        System.out.println("  list");
        System.out.println("  list <status>");
    }
}
