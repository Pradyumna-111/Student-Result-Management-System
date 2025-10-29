package gui;

import javax.swing.*;
import java.awt.Dimension;

public class MainApplication extends JFrame {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 600;

    public MainApplication() {
        super("Student Result Management System");

        setSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // 1. INSTANTIATE VIEW PANEL FIRST
        ViewStudentsPanel viewStudentsPanel = new ViewStudentsPanel();

        // 2. PASS VIEW PANEL REFERENCE TO ADD PANEL
        AddStudentPanel addStudentPanel = new AddStudentPanel(viewStudentsPanel);

        // Add Tabs
        tabbedPane.addTab("Add Student", null, addStudentPanel, "Add New Student Records");
        tabbedPane.addTab("View Students", null, viewStudentsPanel, "View, Refresh, and Delete Student Records");

        add(tabbedPane);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainApplication();
        });
    }
}