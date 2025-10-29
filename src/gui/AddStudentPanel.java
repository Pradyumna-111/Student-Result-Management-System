package gui;

import service.ResultManager;
import model.Student;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddStudentPanel extends JPanel {

    private JTextField rollNumberField = new JTextField(15);
    private JTextField nameField = new JTextField(20);
    private JTextField courseField = new JTextField(15);
    private JTextField semesterField = new JTextField(5);
    private JTextField dobField = new JTextField("YYYY-MM-DD", 10);
    private JTextField contactField = new JTextField(15);
    private JButton saveButton = new JButton("Save Student Record");

    private ResultManager resultManager = new ResultManager();
    // NEW FIELD to hold the reference for refreshing the table
    private ViewStudentsPanel viewPanelReference;

    // MODIFIED CONSTRUCTOR: Takes the ViewStudentsPanel reference
    public AddStudentPanel(ViewStudentsPanel viewPanelReference) {
        this.viewPanelReference = viewPanelReference; // Store the reference

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        formPanel.add(new JLabel("Roll Number:")); formPanel.add(rollNumberField);
        formPanel.add(new JLabel("Full Name:")); formPanel.add(nameField);
        formPanel.add(new JLabel("Course:")); formPanel.add(courseField);
        formPanel.add(new JLabel("Semester:")); formPanel.add(semesterField);
        formPanel.add(new JLabel("Date of Birth (YYYY-MM-DD):")); formPanel.add(dobField);
        formPanel.add(new JLabel("Contact Number:")); formPanel.add(contactField);

        add(formPanel);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(saveButton);

        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveButton.addActionListener(this::saveStudentAction);
    }

    private void saveStudentAction(ActionEvent e) {
        try {
            String rollNumber = rollNumberField.getText().trim();
            String name = nameField.getText().trim();
            String course = courseField.getText().trim();
            int semester = Integer.parseInt(semesterField.getText().trim());
            String dobString = dobField.getText().trim();
            String contactNumber = contactField.getText().trim();

            if (rollNumber.isEmpty() || name.isEmpty() || course.isEmpty() || dobString.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Date dateOfBirth = Date.valueOf(LocalDate.parse(dobString, DateTimeFormatter.ISO_DATE));

            Student newStudent = new Student(rollNumber, name, course, semester, dateOfBirth, contactNumber);

            boolean success = resultManager.addStudent(newStudent);

            if (success) {
                JOptionPane.showMessageDialog(this, "Student " + name + " added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // THE FIX: REFRESH THE VIEW PANEL AFTER SUCCESS
                if (viewPanelReference != null) {
                    viewPanelReference.loadStudentData();
                }

                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add student. Roll number might already exist or DB error.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Semester must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Date of Birth format is incorrect. Use YYYY-MM-DD.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void clearFields() {
        rollNumberField.setText("");
        nameField.setText("");
        courseField.setText("");
        semesterField.setText("");
        contactField.setText("");
        dobField.setText("YYYY-MM-DD");
    }
}