package models;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;





 public class Database      {
    // Static ArrayLists for data storage
    private static ArrayList<Student> students = new ArrayList<>();
    private static ArrayList<Instructor> instructors = new ArrayList<>();
    private static ArrayList<Admin> admins = new ArrayList<>();
    private static ArrayList<Course> courses = new ArrayList<>();
    private static ArrayList<Assignment> assignments = new ArrayList<>();
    private static ArrayList<AssignmentSubmission> submissions = new ArrayList<>();
    private static ArrayList<Category> categories = new ArrayList<>();

    // Map to track course enrollments
    private static Map<Integer, ArrayList<String>> courseEnrollments = new HashMap<>();

    // ID counters
    private static int nextCourseId = 1;
    private static int nextAssignmentId = 1;
    private static int nextSubmissionId = 1;
    private static int nextCategoryId = 1;

    // Initialize dummy data
    static {
        initializeDummyData();
    }

    private static void initializeDummyData() {
        // Create categories
        Category programming = new Category(nextCategoryId++, "Programming", "Computer programming courses");
        Category mathematics = new Category(nextCategoryId++, "Mathematics", "Mathematical courses");
        Category science = new Category(nextCategoryId++, "Science", "Science courses");

        categories.add(programming);
        categories.add(mathematics);
        categories.add(science);

        // Create admin
        Admin admin1 = new Admin("Admin", "Admin123", "1980-01-01", 40);
        admins.add(admin1);

        // Create instructors
        Instructor instructor1 = new Instructor("Ali", "Ali123", "1985-05-15", "Computer Science");
        Instructor instructor2 = new Instructor("Ahmed", "Ahmed456", "1982-08-22", "Mathematics");

        instructors.add(instructor1);
        instructors.add(instructor2);

        // Create students
        Student student1 = new Student("Shahd", "Shahd123", "2000-03-10",
                "Mivida", Gender.FEMALE, 15000.0);
        Student student2 = new Student("Mahmoud", "Mahmoud123", "1999-11-25",
                "Rehab", Gender.MALE, 18000.0);
        Student student3 = new Student("Abdelrahman", "Bedo123", "2000-03-10",
                "District5",Gender.MALE, 20000.0);
        Student student4 = new Student("Logy", "Logy123", "2000-03-10",
                "Maadi",Gender.FEMALE, 25000.0);
        Student student5 = new Student("Toqa", "Toqa123", "2000-03-10",
                "tagamo3", Gender.FEMALE, 15000.0);

        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);


        // Create courses
        Course course1 = new Course(nextCourseId++, "Java Programming",
                "Learn Java from basics to advanced", 7500,
                "Ali", programming, 30);
        Course course2 = new Course(nextCourseId++, "Calculus I",
                "Introduction to differential calculus", 7650,
                "Ahmed", mathematics, 25);

        courses.add(course1);
        courses.add(course2);

        // Update instructor teaching courses
        instructor1.getTeachingCourses().add(1);
        instructor2.getTeachingCourses().add(2);

        // Initialize course enrollments
        courseEnrollments.put(1, new ArrayList<>());
        courseEnrollments.put(2, new ArrayList<>());

        // Create sample assignments
        Assignment assignment1 = new Assignment(nextAssignmentId++, "Hello World Program",
                "Create your first Java program", 1,
                "2024-08-15", 100.0);
        Assignment assignment2 = new Assignment(nextAssignmentId++, "Derivative Problems",
                "Solve 10 derivative problems", 2,
                "2024-08-20", 100.0);

        assignments.add(assignment1);
        assignments.add(assignment2);
    }

    // User Management
    public static void addStudent(Student student) { students.add(student); }
    public static void addInstructor(Instructor instructor) { instructors.add(instructor); }
    public static void addAdmin(Admin admin) { admins.add(admin); }

    public static Student getStudentById(String username) {
        return students.stream()
                .filter(s -> s.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public static Instructor getInstructorById(String username) {
        return instructors.stream()
                .filter(i -> i.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public static Admin getAdminById(String username) {
        return admins.stream()
                .filter(a -> a.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    // Course Management
    public static void addCourse(Course course) {
        courses.add(course);
        courseEnrollments.put(course.getCourseId(), new ArrayList<>());
    }

    public static Course getCourseById(int courseId) {
        return courses.stream()
                .filter(c -> c.getCourseId() == courseId)
                .findFirst()
                .orElse(null);
    }

    public static void removeCourse(int courseId) {
        courses.removeIf(c -> c.getCourseId() == courseId);
        courseEnrollments.remove(courseId);
    }

    // Assignment Management
    public static void addAssignment(Assignment assignment) { assignments.add(assignment); }

    public static Assignment getAssignmentById(int assignmentId) {
        return assignments.stream()
                .filter(a -> a.getAssignmentId() == assignmentId)
                .findFirst()
                .orElse(null);
    }

    // Submission Management
    public static void addSubmission(AssignmentSubmission submission) { submissions.add(submission); }

    // Enrollment Management
    public static void addStudentToCourse(String studentId, int courseId) {
        courseEnrollments.computeIfAbsent(courseId, k -> new ArrayList<>()).add(studentId);
    }

    public static ArrayList<String> getStudentsInCourse(int courseId) {
        return courseEnrollments.getOrDefault(courseId, new ArrayList<>());
    }

    // Getters for all data
    public static ArrayList<Student> getAllStudents() { return students; }
    public static ArrayList<Instructor> getAllInstructors() { return instructors; }
    public static ArrayList<Admin> getAllAdmins() { return admins; }
    public static ArrayList<Course> getAllCourses() { return courses; }
    public static ArrayList<Assignment> getAllAssignments() { return assignments; }
    public static ArrayList<Category> getAllCategories() { return categories; }

    // ID generators
    public static int getNextCourseId() { return nextCourseId++; }
    public static int getNextAssignmentId() { return nextAssignmentId++; }
    public static int getNextSubmissionId() { return nextSubmissionId++; }

    // Authentication
    public static User authenticate(String username, String password) {
        // Check students
        Student student = getStudentById(username);
        if (student != null && student.getPassword().equals(password)) {
            return student;
        }

        // Check instructors
        Instructor instructor = getInstructorById(username);
        if (instructor != null && instructor.getPassword().equals(password)) {
            return instructor;
        }

        // Check admins
        Admin admin = getAdminById(username);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }

        return null; // Authentication failed
    }

    public static ArrayList<AssignmentSubmission> getAllSubmissions() {
        return submissions;
    }
}
