package gui;

import service.ResultManager;
import model.Student;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditStudentDialog extends JDialog {

    // Components
    private JTextField rollNumberField;
    private JTextField nameField;
    private JTextField courseField;
    private JTextField semesterField;
    private JTextField dobField;
    private JTextField contactField;
    private JButton saveButton;

    private Student studentToEdit; // The student object passed from the main panel
    private ResultManager resultManager = new ResultManager();
    private ViewStudentsPanel parentPanel; // Reference to refresh the table

    public EditStudentDialog(JFrame owner, ViewStudentsPanel parentPanel, Student student) {
        super(owner, "Edit Student Record: " + student.getName(), true); // 'true' for modal dialog
        this.studentToEdit = student;
        this.parentPanel = parentPanel;

        // Setup components and pre-fill data
        setupForm();
        preFillData(student);

        setSize(400, 450);
        setLocationRelativeTo(owner);
    }

    private void setupForm() {
        // Initialize fields
        rollNumberField = new JTextField(15);
        nameField = new JTextField(20);
        courseField = new JTextField(15);
        semesterField = new JTextField(5);
        dobField = new JTextField(10);
        contactField = new JTextField(15);
        saveButton = new JButton("Save Changes");

        // Layout setup
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10)); // 7 rows: 6 fields + 1 button row
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        formPanel.add(new JLabel("Roll Number:")); formPanel.add(rollNumberField);
        formPanel.add(new JLabel("Full Name:")); formPanel.add(nameField);
        formPanel.add(new JLabel("Course:")); formPanel.add(courseField);
        formPanel.add(new JLabel("Semester:")); formPanel.add(semesterField);
        formPanel.add(new JLabel("Date of Birth (YYYY-MM-DD):")); formPanel.add(dobField);
        formPanel.add(new JLabel("Contact Number:")); formPanel.add(contactField);

        formPanel.add(new JLabel("")); // Spacer
        formPanel.add(saveButton);

        add(formPanel, BorderLayout.CENTER);

        saveButton.addActionListener(this::saveUpdateAction);
    }

    private void preFillData(Student student) {
        rollNumberField.setText(student.getRollNumber());
        nameField.setText(student.getName());
        courseField.setText(student.getCourse());
        semesterField.setText(String.valueOf(student.getSemester()));
        dobField.setText(student.getDateOfBirth().toString());
        contactField.setText(student.getContactNumber());
    }

    /**
     * Handles the update logic when the Save Changes button is clicked.
     */
    private void saveUpdateAction(ActionEvent e) {
        try {
            // 1. Capture and validate new input
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

            // 2. Update the existing Model object with new values
            studentToEdit.setRollNumber(rollNumber);
            studentToEdit.setName(name);
            studentToEdit.setCourse(course);
            studentToEdit.setSemester(semester);
            studentToEdit.setDateOfBirth(dateOfBirth);
            studentToEdit.setContactNumber(contactNumber);

            // 3. Call the Service Layer Update Method
            boolean success = resultManager.updateStudent(studentToEdit);

            // 4. Provide Feedback
            if (success) {
                JOptionPane.showMessageDialog(this, "Student record updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                parentPanel.loadStudentData(); // Refresh the table in the background
                dispose(); // Close the dialog
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update student. Roll number might exist.", "Database Error", JOptionPane.ERROR_MESSAGE);
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
}