import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    private List<Task> tasks;

    public Main() {
        tasks = new ArrayList<>();

        JFrame frame = new JFrame("Task Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(240, 240, 240));

        JList<Task> taskList = new JList<>();
        DefaultListModel<Task> listModel = new DefaultListModel<>();
        taskList.setModel(listModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setBackground(new Color(255, 255, 255));
        taskList.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        JButton addButton = new JButton("Add Task");
        addButton.setBackground(new Color(0, 153, 0));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskDescription = JOptionPane.showInputDialog(frame, "Enter task:");
                if (taskDescription != null && !taskDescription.isEmpty()) {
                    Task newTask = new Task(taskDescription);
                    tasks.add(newTask);
                    updateTaskList(listModel);
                }
            }
        });

        JButton removeButton = new JButton("Remove Task");
        removeButton.setBackground(new Color(204, 0, 0));
        removeButton.setForeground(Color.WHITE);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    tasks.remove(selectedIndex);
                    updateTaskList(listModel);
                }
            }
        });

        JButton completeButton = new JButton("Complete Task");
        completeButton.setBackground(new Color(0, 102, 204));
        completeButton.setForeground(Color.WHITE);
        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Task selectedTask = tasks.get(selectedIndex);
                    selectedTask.setCompleted(true);
                    updateTaskList(listModel);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(completeButton);

        frame.add(new JScrollPane(taskList), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void updateTaskList(DefaultListModel<Task> listModel) {
        listModel.removeAllElements();
        for (Task task : tasks) {
            listModel.addElement(task);
        }
    }

    private class Task {
        private String description;
        private boolean completed;
        private Date deadline;

        public Task(String description) {
            this.description = description;
            this.completed = false;
            // Set a default deadline (can be modified as needed)
            this.deadline = new Date();
        }

        public String getDescription() {
            return description;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public Date getDeadline() {
            return deadline;
        }

        @Override
        public String toString() {
            String status = completed ? "[✓] " : "[ ] ";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String deadlineStr = (deadline != null) ? " - Deadline: " + dateFormat.format(deadline) : "";
            return "<html><body style='width: 200px;'>" + status + "<b>" + description + "</b>" + deadlineStr + "</body></html>";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}
