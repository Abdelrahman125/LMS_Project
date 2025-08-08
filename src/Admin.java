import java.util.*;
import java.time.LocalDate;


 class Admin extends User {
private String role;
private  int workingHours;

    public Admin(String username, String password, LocalDate dateOfBirth, String role, int workingHours) {
        super(username, password, dateOfBirth);
        this.role = role;
        this.workingHours = workingHours;
    }
    @Override
    public void displayDashboard() {
            System.out.println("=== ADMIN DASHBOARD ===");
            System.out.println("Welcome Admin, " + username);
            System.out.println("Role: " + role);
            System.out.println("Working Hours: " + workingHours + " hrs/week");
            System.out.println("Total Users: " + Database.getAllUsers().size());
            System.out.println("Total Courses: " + Database.getAllCourses().size());
            System.out.println("======================");
    }

    public void viewAllCourses() {
        System.out.println("\n=== ALL COURSES ===");
        List<Course> courses = Database.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            for (Course course : courses) {
                System.out.println(course);
            }
        }
        System.out.println("==================");
    }

    public void viewAllInstructors() {
        System.out.println("\n=== ALL INSTRUCTORS ===");
        List<Instructor> instructors = Database.getAllInstructors();
        if (instructors.isEmpty()) {
            System.out.println("No instructors found.");
        } else {
            for (Instructor instructor : instructors) {
                System.out.println(instructor);
            }
        }
        System.out.println("======================");
    }


    public void viewAllStudents() {
        System.out.println("\n=== ALL STUDENTS ===");
        List<Student> students = Database.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
        System.out.println("===================");
    }




    public boolean addCourse(String title, String description, double price, int instructorId, Category category) {
        // Validate instructor exists
        Instructor instructor = Database.getInstructorById(instructorId);
        if (instructor == null) {
            System.out.println("Error: Instructor with ID " + instructorId + " not found.");
            return false;
        }

        Course course = new Course(title, description, price, instructorId, category);
        Database.addCourse(course);
        instructor.assignToCourse(course.getCourseId());

        System.out.println("Course added successfully: " + course.getTitle());
        return true;
    }




    public boolean removeCourse(int courseId) {
        Course course = Database.getCourseById(courseId);
        if (course != null) {
            Database.removeCourse(courseId);
            System.out.println("Course removed successfully: " + course.getTitle());
            return true;
        } else {
            System.out.println("Error: Course with ID " + courseId + " not found.");
            return false;
        }
    }



    public boolean addInstructor(String username, String password, LocalDate dateOfBirth, String specialization) {
        // Check if username already exists
        if (Database.getUserByUsername(username) != null) {
            System.out.println("Error: Username '" + username + "' already exists.");
            return false;
        }

        Instructor instructor = new Instructor(username, password, dateOfBirth, specialization);
        Database.addInstructor(instructor);
        System.out.println("Instructor added successfully: " + instructor.getUsername());
        return true;
    }



    public boolean addStudent(String username, String password, LocalDate dateOfBirth,
                              String address, Gender gender, double initialBalance) {
        // Check if username already exists
        if (Database.getUserByUsername(username) != null) {
            System.out.println("Error: Username '" + username + "' already exists.");
            return false;
        }

        Student student = new Student(username, password, dateOfBirth, address, gender, initialBalance);
        Database.addStudent(student);
        System.out.println("Student added successfully: " + student.getUsername());
        return true;
    }





    public boolean removeUser(int userId) {
        User user = Database.getUserById(userId);
        if (user != null) {
            Database.removeUser(userId);
            System.out.println("User removed successfully: " + user.getUsername());
            return true;
        } else {
            System.out.println("Error: User with ID " + userId + " not found.");
            return false;
        }
    }



    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public int getWorkingHours() {
        return workingHours;
    }
    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }


    @Override
    public String toString() {
        return "Admin{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", workingHours=" + workingHours +
                '}';
    }



}
