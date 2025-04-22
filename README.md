# Task Tracker CLI

A simple command-line tool for managing your tasks. This application allows you to add, list, and manage tasks using simple commands. The project is built using Java 17, Spring Boot, and Maven.

## Features

- **Add a task**: Adds a task to the tracker.
- **List tasks**: Displays all tasks currently in the tracker.

## Prerequisites

Before using the Task Tracker CLI, ensure you have the following installed:

- **Java 17** (or compatible version)
- **Maven** for building the project
- **Windows OS** (for `.bat` file usage)

## Installation

To install and build the application:

1. Clone the repository to your local machine:
   ```bash
   git clone https://github.com/yourusername/task-tracker-cli.git
   cd task-tracker-cli
   ```

2. Build the project using Maven:
   ```bash
   mvn clean install
   ```

3. After the build completes, the compiled JAR and the `task-cli.bat` file will be located in the `target` folder.

## Running the Application

### CMD

On **CMD (Command Prompt)**, you can run the commands directly from the `target` directory.

1. Open Command Prompt and navigate to the `target` directory:
   ```cmd
   cd target
   ```

2. To add a task:
   ```cmd
   task-cli.bat add "Buy groceries"
   ```

3. To list all tasks:
   ```cmd
   task-cli.bat list
   ```

### PowerShell

In **PowerShell**, you need to prefix the batch file name with `.\` due to its execution policy.

1. Open PowerShell and navigate to the `target` directory:
   ```powershell
   cd target
   ```

2. To add a task:
   ```powershell
   .\task-cli.bat add "Buy groceries"
   ```

3. To list all tasks:
   ```powershell
   .\task-cli.bat list
   ```

### Running from Any Directory

To run the application from anywhere without changing directories, add the `target` directory to your system's `PATH` variable. This is not required for portability, but it makes accessing the `.bat` file easier. Alternatively, always navigate to the `target` folder before running commands.

## Commands

- `add "<task_name>"`: Adds a task with the specified name.
- `list`: Lists all tasks currently in the tracker.

### Example:

1. **Add a task**:
   ```cmd
   task-cli.bat add "Finish homework"
   ```

2. **List tasks**:
   ```cmd
   task-cli.bat list
   ```

## Project Structure

Here is a quick overview of the project structure:

```
task-tracker-cli/
├── src/
│   ├── main/
│   │   ├── java/          # Java source files
│   │   └── resources/     # Application resources (including task-cli.bat)
├── target/                # Compiled JAR and .bat files
├── pom.xml                # Maven build configuration
├── assembly.xml           # Maven Assembly configuration
└── README.md              # Project documentation
```

## Contributing

Contributions are welcome! Please fork this repository and create a pull request with your changes. Ensure that your code follows the project's conventions and passes all tests.
