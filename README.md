# 📚 Student Result Management System

A full-stack desktop application built with Java Swing and JDBC for efficient management of student records, subject details, and academic results.

## ✨ Features

This platform provides a comprehensive and organized environment for managing educational data, implementing full CRUD (Create, Read, Update, Delete) capabilities across core entities.

- **Student Management (Full CRUD)**: Seamlessly add, view, edit (via double-click in the table), and delete student records.
- **Subject Management (C & R)**: Add new academic subjects with unique codes and track their maximum marks.
- **Result Entry**: Intuitive form to link a specific Student and Subject and record the marks obtained.
- **Dynamic Data Views**: Tabular views for Students and Subjects with instant data refresh upon successful record creation or modification.
- **Robust Database Layer**: Utilizes the Service Layer pattern to manage transactions and prevent duplicate entries (e.g., duplicate roll numbers or subject codes).

## 🛠️ Tech Stack

| Category | Technology | Description |
|----------|-----------|-------------|
| Frontend/GUI | Java Swing | Used for creating the cross-platform desktop user interface and forms |
| Backend/Logic | Core Java | Handles the business logic, input validation, and object models |
| Persistence | MySQL Server | The robust relational database used to store all student and result data |
| Connectivity | JDBC | Java Database Connectivity for connecting the application to MySQL |
| Version Control | Git & GitHub | For source code management and collaborative tracking |

## ⚙️ Getting Started

Follow these steps to set up and run the project locally.

### Prerequisites

You'll need the following installed and configured:

- **Java Development Kit (JDK 8 or higher)**
- **MySQL Server** (running locally on port 3306)
- **MySQL Connector/J JAR file** ([Download](https://dev.mysql.com/downloads/connector/j/) and add to the project's `lib/` folder)
- **Git**

### Installation

1. **Clone the repository:**

```bash
git clone https://github.com/Pradyumna-111/Student-Result-Management-System.git
cd Student-Result-Management-System
```

2. **Add JDBC Connector:**

   - Place your downloaded `mysql-connector-j-x.x.x.jar` file into the `lib/` directory
   - In your IDE (e.g., IntelliJ), ensure the JAR file is added as a Module Dependency

3. **Database Schema Setup:**

   Execute the following SQL commands in your MySQL client (Workbench, CLI, etc.) to create the necessary database and tables:

```sql
CREATE DATABASE IF NOT EXISTS student_management_system;
USE student_management_system;

CREATE TABLE student (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    roll_number VARCHAR(15) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    father_name VARCHAR(100),
    course VARCHAR(50),
    branch VARCHAR(50)
);

CREATE TABLE subject (
    subject_id INT PRIMARY KEY AUTO_INCREMENT,
    subject_code VARCHAR(10) UNIQUE NOT NULL,
    subject_name VARCHAR(100) NOT NULL,
    max_marks INT NOT NULL
);

CREATE TABLE result (
    result_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    subject_id INT NOT NULL,
    marks_obtained INT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES student(student_id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subject(subject_id) ON DELETE CASCADE,
    UNIQUE KEY unique_student_subject (student_id, subject_id)
);
```

4. **Set up Credentials:**

   - Open `src/database/DBConnection.java`
   - Update the `PASSWORD` constant to match your local MySQL password

```java
private static final String USER = "root"; 
private static final String PASSWORD = "your_mysql_password"; // <-- CHANGE THIS!
```

5. **Run the Application:**

   - Open the project in your IDE
   - Locate the main class: `src/gui/MainApplication.java`
   - Run the main method
   - The application will launch on your desktop screen

## 📂 Project Structure

```
Student-Result-Management-System/
├── src/
│   ├── database/       # Manages DB connection (DBConnection.java)
│   ├── model/          # Data objects (Student.java, Subject.java, Result.java)
│   ├── service/        # Business logic and JDBC operations (ResultManager.java)
│   └── gui/            # Swing Panels and Frames
│       ├── MainApplication.java
│       └── ... various panels (AddStudentPanel, ViewStudentsPanel, etc.)
└── lib/                # JDBC Connector JAR file
```

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/view-results-panel`)
3. Commit your Changes (`git commit -m 'feat: complete the View Results panel'`)
4. Push to the Branch (`git push origin feature/view-results-panel`)
5. Open a Pull Request

## 📜 License

Distributed under the MIT License. See `LICENSE` for more information.

## 📞 Contact

**Your Name** - [Your Email]

**Project Link**: [https://github.com/Pradyumna-111/Student-Result-Management-System](https://github.com/Pradyumna-111/Student-Result-Management-System)

---

⭐ If you find this project useful, please consider giving it a star on GitHub!
