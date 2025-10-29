package model;

import java.sql.Date;

public class Student {
    private int studentId;
    private String rollNumber;
    private String name;
    private String course;
    private int semester;
    private Date dateOfBirth;
    private String contactNumber;

    // Full Constructor (Used when reading from DB)
    public Student(int studentId, String rollNumber, String name, String course, int semester, Date dateOfBirth, String contactNumber) {
        this.studentId = studentId;
        this.rollNumber = rollNumber;
        this.name = name;
        this.course = course;
        this.semester = semester;
        this.dateOfBirth = dateOfBirth;
        this.contactNumber = contactNumber;
    }

    // Simple Constructor (Used when adding a new student)
    public Student(String rollNumber, String name, String course, int semester, Date dateOfBirth, String contactNumber) {
        this(0, rollNumber, name, course, semester, dateOfBirth, contactNumber);
    }

    // --- Getters ---
    public int getStudentId() { return studentId; }
    public String getRollNumber() { return rollNumber; }
    public String getName() { return name; }
    public String getCourse() { return course; }
    public int getSemester() { return semester; }
    public Date getDateOfBirth() { return dateOfBirth; }
    public String getContactNumber() { return contactNumber; }


    // --- Setters (REQUIRED FOR UPDATE OPERATION) ---
    // These methods allow the EditStudentDialog to change the object's state.

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return "Roll: " + rollNumber + ", Name: " + name + ", Course: " + course;
    }
}