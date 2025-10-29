package service;

import database.DBConnection;
import model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultManager {

    // --- C: Create (Add) Operation ---
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO student (roll_number, name, course, semester, date_of_birth, contact_number) VALUES (?, ?, ?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, student.getRollNumber());
            ps.setString(2, student.getName());
            ps.setString(3, student.getCourse());
            ps.setInt(4, student.getSemester());
            ps.setDate(5, student.getDateOfBirth());
            ps.setString(6, student.getContactNumber());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Database error when adding student: " + e.getMessage());
            // 1062 is Duplicate Entry (Unique constraint violation on roll_number)
            if (e.getErrorCode() == 1062) {
                System.err.println("Error: Roll number already exists.");
            }
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) {/* ignore */}
            DBConnection.closeConnection(con);
        }
        return false;
    }

    // --- R: Read (Retrieve All) Operation ---
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        String sql = "SELECT * FROM student ORDER BY roll_number";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("student_id"),
                        rs.getString("roll_number"),
                        rs.getString("name"),
                        rs.getString("course"),
                        rs.getInt("semester"),
                        rs.getDate("date_of_birth"),
                        rs.getString("contact_number")
                );
                studentList.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Database error during student retrieval: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {/* ignore */}
            try { if (ps != null) ps.close(); } catch (SQLException e) {/* ignore */}
            DBConnection.closeConnection(con);
        }
        return studentList;
    }

    // --- D: Delete Operation (NEWLY ADDED) ---
    public boolean deleteStudent(int studentId) {
        String sql = "DELETE FROM student WHERE student_id = ?";

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, studentId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Database error when deleting student: " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) {/* ignore */}
            DBConnection.closeConnection(con);
        }
        return false;
    }
    // --- U: Update Operation ---
    /**
     * Updates an existing student record based on the student_id.
     * @param student The Student object containing the updated information (ID must be set).
     * @return true if the student was updated successfully, false otherwise.
     */
    public boolean updateStudent(Student student) {
        String sql = "UPDATE student SET roll_number = ?, name = ?, course = ?, semester = ?, date_of_birth = ?, contact_number = ? WHERE student_id = ?";

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);

            // Set values for the prepared statement
            ps.setString(1, student.getRollNumber());
            ps.setString(2, student.getName());
            ps.setString(3, student.getCourse());
            ps.setInt(4, student.getSemester());
            ps.setDate(5, student.getDateOfBirth());
            ps.setString(6, student.getContactNumber());
            ps.setInt(7, student.getStudentId()); // The WHERE clause condition

            // Execute the update
            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Database error when updating student: " + e.getMessage());
            if (e.getErrorCode() == 1062) {
                System.err.println("Error: Updated roll number already exists for another student.");
            }
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) {/* ignore */}
            DBConnection.closeConnection(con);
        }
        return false;
    }

    /**
     * Helper method to retrieve a single student by ID (needed for the Edit Dialog).
     */
    public Student getStudentById(int studentId) {
        String sql = "SELECT * FROM student WHERE student_id = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Student student = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);
            rs = ps.executeQuery();

            if (rs.next()) {
                student = new Student(
                        rs.getInt("student_id"),
                        rs.getString("roll_number"),
                        rs.getString("name"),
                        rs.getString("course"),
                        rs.getInt("semester"),
                        rs.getDate("date_of_birth"),
                        rs.getString("contact_number")
                );
            }
        } catch (SQLException e) {
            System.err.println("Database error retrieving student by ID: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {/* ignore */}
            try { if (ps != null) ps.close(); } catch (SQLException e) {/* ignore */}
            DBConnection.closeConnection(con);
        }
        return student;
    }
}