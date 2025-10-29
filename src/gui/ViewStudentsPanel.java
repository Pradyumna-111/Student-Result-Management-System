package gui;

import service.ResultManager;
import model.Student;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewStudentsPanel extends JPanel {

    private JTable studentTable;
    private ResultManager resultManager = new ResultManager();
    private String[] columnNames = {"ID", "Roll Number", "Name", "Course", "Semester", "DOB", "Contact"};
    private JButton deleteButton = new JButton("Delete Selected Student"); // NEW

    // Inside ViewStudentsPanel's constructor:
    public ViewStudentsPanel() {
        setLayout(new BorderLayout());

        studentTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(studentTable);

        add(scrollPane, BorderLayout.CENTER);

        // ... (rest of the button setup) ...

        // 4. Implement Double-Click Listener for Editing (NEW)
        studentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2 && !evt.isConsumed()) {
                    evt.consume();
                    editSelectedStudent();
                }
            }
        });

        loadStudentData();
    }
    /**
     * Handles the action when the Delete button is clicked.
     */
    private void deleteSelectedStudent() {
        int selectedRow = studentTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student row to delete.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get the student_id (first column) of the selected row
        int studentId = (int) studentTable.getModel().getValueAt(selectedRow, 0);

        // Confirmation Dialog
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete the student with ID: " + studentId + "?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // Call the service layer method
            boolean success = resultManager.deleteStudent(studentId);

            if (success) {
                JOptionPane.showMessageDialog(this, "Student deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadStudentData(); // Reload data immediately
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete student record.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Fetches the student data from the service layer and populates the JTable.
     */
    public void loadStudentData() {
        try {
            List<Student> students = resultManager.getAllStudents();
            Object[][] data = convertStudentListToArray(students);

            DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            studentTable.setModel(model);

            // Set widths
            studentTable.getColumnModel().getColumn(0).setPreferredWidth(30);
            studentTable.getColumnModel().getColumn(1).setPreferredWidth(80);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Could not load student data: " + ex.getMessage(),
                    "Data Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private Object[][] convertStudentListToArray(List<Student> students) {
        Object[][] data = new Object[students.size()][columnNames.length];

        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            data[i][0] = s.getStudentId();
            data[i][1] = s.getRollNumber();
            data[i][2] = s.getName();
            data[i][3] = s.getCourse();
            data[i][4] = s.getSemester();
            data[i][5] = s.getDateOfBirth();
            data[i][6] = s.getContactNumber();
        }
        return data;
    }
    /**
     * Retrieves the selected student's ID and opens the EditStudentDialog.
     */
    private void editSelectedStudent() {
        int selectedRow = studentTable.getSelectedRow();

        if (selectedRow == -1) {
            // This shouldn't happen with double-click, but good practice
            JOptionPane.showMessageDialog(this, "Please select a student row to edit.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 1. Get the Student ID from the table model
        int studentId = (int) studentTable.getModel().getValueAt(selectedRow, 0);

        // 2. Retrieve the full Student object from the database (using the new helper method)
        Student studentToEdit = resultManager.getStudentById(studentId);

        if (studentToEdit != null) {
            // 3. Open the modal Edit Dialog
            JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
            EditStudentDialog dialog = new EditStudentDialog(owner, this, studentToEdit);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Could not retrieve student details from the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // NOTE: You now have a reference to the 'deleteButton' in the class.
    // If you want to enable/disable buttons based on selection, you can do so here.
}