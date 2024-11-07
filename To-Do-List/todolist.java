import javax.swing.*;
import java.util.LinkedList;
import java.util.Stack;

public class todolist {
    
    LinkedList<String> tasks = new LinkedList<>();
    Stack<String> undoStack = new Stack<>();
    public static void main(String[] args) {
        
        todolist app = new todolist();
        app.run();
    }

    public void run() {
        while (true) {
            String[] options = {"Add Task", "Delete Task", "Mark Task as Done", "Undo", "View Tasks", "Exit"};
            int choice = JOptionPane.showOptionDialog(null, "Choose an action", "To-Do List",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    addTask();
                    break;
                case 1:
                    deleteTask();
                    break;
                case 2:
                    markTaskAsDone();
                    break;
                case 3:
                    undo();
                    break;
                case 4:
                    viewTasks();
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Exiting the application.");
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option selected.");
            }
        }
    }

   
    void addTask() {
        String task = JOptionPane.showInputDialog("Enter the task description:");
        if (task != null && !task.trim().isEmpty()) {
            tasks.add(task);
            undoStack.push("ADD:" + task); 
            JOptionPane.showMessageDialog(null, "Task added: " + task);
        } else {
            JOptionPane.showMessageDialog(null, "Task cannot be empty.");
        }
    }

    
    void deleteTask() {
        if (tasks.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tasks to delete.");
            return;
        }

        String taskNumber = JOptionPane.showInputDialog("Enter the task number to delete (1 to " + tasks.size() + "):");

        try {
            int taskIndex = Integer.parseInt(taskNumber) - 1;
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                String taskToDelete = tasks.remove(taskIndex);
                undoStack.push("DELETE:" + taskToDelete); 
                JOptionPane.showMessageDialog(null, "Task deleted: " + taskToDelete);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid task number.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
        }
    }

  
    void markTaskAsDone() {
        if (tasks.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tasks to mark as done.");
            return;
        }

        String taskNumber = JOptionPane.showInputDialog("Enter the task number to mark as done (1 to " + tasks.size() + "):");

        try {
            int taskIndex = Integer.parseInt(taskNumber) - 1; 
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                String taskToMark = tasks.get(taskIndex);
                String completedTask = taskToMark + " - Done";
                tasks.set(taskIndex, completedTask);
                undoStack.push("MARK_DONE:" + taskToMark); 
                JOptionPane.showMessageDialog(null, "Task marked as done: " + taskToMark);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid task number.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
        }
    }

  
    void undo() {
        if (!undoStack.isEmpty()) {
            String lastAction = undoStack.pop();
            String[] actionParts = lastAction.split(":");
            String actionType = actionParts[0];
            String taskDescription = actionParts[1];

            switch (actionType) {
                case "ADD":
                    tasks.remove(taskDescription);
                    JOptionPane.showMessageDialog(null, "Undo: Task removed: " + taskDescription);
                    break;
                case "DELETE":
                    tasks.add(taskDescription);
                    JOptionPane.showMessageDialog(null, "Undo: Task added back: " + taskDescription);
                    break;
                case "MARK_DONE":
                  
                    tasks.set(tasks.indexOf(taskDescription + " - Done"), taskDescription);
                    JOptionPane.showMessageDialog(null, "Undo: Task marked as not done: " + taskDescription);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Unknown undo action.");
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(null, "No actions to undo.");
        }
    }

    
    void viewTasks() {
        if (tasks.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tasks to display.");
        } else {
            StringBuilder taskList = new StringBuilder("Current Tasks:\n");
            for (int i = 0; i < tasks.size(); i++) {
                taskList.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
            }
            JOptionPane.showMessageDialog(null, taskList.toString());
        }
    }
}

