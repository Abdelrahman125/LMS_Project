import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;



class Database {
    // Static ArrayLists to store all data
    private static List<User> users = new ArrayList<>();
    private static List<Student> students = new ArrayList<>();
    private static List<Instructor> instructors = new ArrayList<>();
    private static List<Admin> admins = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();
    private static List<Category> categories = new ArrayList<>();
    private static List<Assignment> assignments = new ArrayList<>();

    // Initialize with dummy data
    static {
        initializeDummyData();
    }
    private static void initializeDummyData() {
        // Create categories
        Category programming = new Category("Programming", "Software Development Courses");
        Category mathematics = new Category("Mathematics", "Math and Statistics Courses");
        Category science = new Category("Science", "Natural Sciences Courses");
        categories.add(programming);
        categories.add(mathematics);
        categories.add(science);

        // Create admins
        Admin admin1 = new Admin("admin", "admin123", LocalDate.of(1980, 5, 15), "System Administrator", 40);
        Admin admin2 = new Admin("superadmin", "super123", LocalDate.of(1975, 8, 20), "Super Administrator", 45);
        admins.add(admin1);
        admins.add(admin2);
        users.add(admin1);
        users.add(admin2);

        // Create instructors
        Instructor instructor1 = new Instructor("Ahmed", "Ahmed123", LocalDate.of(1985, 3, 10), "Computer Science");
        Instructor instructor2 = new Instructor("Joe", "Joe123", LocalDate.of(1988, 7, 25), "Mathematics");
        instructors.add(instructor1);
        instructors.add(instructor2);
        users.add(instructor1);
        users.add(instructor2);

        // Create students
        Student student1 = new Student("Rana", "Rana123", LocalDate.of(2000, 1, 15),
                "Rehab", User.Gender.FEMALE, 1000.0);
        Student student2 = new Student("Abdelrahman", "Bedo123", LocalDate.of(1999, 12, 5),
                "madindy", User.Gender.MALE, 1500.0);
        Student student3 = new Student("Mahmoud", "Mahmoud123", LocalDate.of(2001, 6, 18),
                "Mivida", User.Gender.MALE, 800.0);
        students.add(student1);
        students.add(student2);
        students.add(student3);
        users.add(student1);
        users.add(student2);
        users.add(student3);

        // Create courses
        Course course1 = new Course("Java Programming", "Learn Java from basics to advanced",
                299.99, instructor1.getUserId(), programming);
        Course course2 = new Course("Data Structures", "Master algorithms and data structures",
                399.99, instructor1.getUserId(), programming);
        Course course3 = new Course("Calculus I", "Introduction to differential calculus",
                249.99, instructor2.getUserId(), mathematics);
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);

        instructor1.assignToCourse(course1.getCourseId());
        instructor1.assignToCourse(course2.getCourseId());
        instructor2.assignToCourse(course3.getCourseId());

        // Create assignments
        Assignment assignment1 = new Assignment("Hello World Program", "Create your first Java program",
                LocalDateTime.now().plusDays(7), course1.getCourseId(), 100.0);
        Assignment assignment2 = new Assignment("Array Implementation", "Implement a dynamic array",
                LocalDateTime.now().plusDays(14), course2.getCourseId(), 100.0);
        assignments.add(assignment1);
        assignments.add(assignment2);
        course1.addAssignment(assignment1);
        course2.addAssignment(assignment2);
    }

    // User operations
    public static void addUser(User user) { users.add(user); }
    public static List<User> getAllUsers() { return new ArrayList<>(users); }
    public static User getUserById(int userId) {
        return users.stream().filter(u -> u.getUserId() == userId).findFirst().orElse(null);
    }
    public static User getUserByUsername(String username) {
        return users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
    }
    public static boolean removeUser(int userId) {
        User user = getUserById(userId);
        if (user != null) {
            users.remove(user);
            // Also remove from specific lists
            if (user instanceof Student) students.remove(user);
            else if (user instanceof Instructor) instructors.remove(user);
            else if (user instanceof Admin) admins.remove(user);
            return true;
        }
        return false;
    }

    // Student operations
    public static void addStudent(Student student) {
        students.add(student);
        users.add(student);
    }
    public static List<Student> getAllStudents() { return new ArrayList<>(students); }
    public static Student getStudentById(int studentId) {
        return students.stream().filter(s -> s.getUserId() == studentId).findFirst().orElse(null);
    }

    // Instructor operations
    public static void addInstructor(Instructor instructor) {
        instructors.add(instructor);
        users.add(instructor);
    }
    public static List<Instructor> getAllInstructors() { return new ArrayList<>(instructors); }
    public static Instructor getInstructorById(int instructorId) {
        return instructors.stream().filter(i -> i.getUserId() == instructorId).findFirst().orElse(null);
    }

    // Admin operations
    public static void addAdmin(Admin admin) {
        admins.add(admin);
        users.add(admin);
    }
    public static List<Admin> getAllAdmins() { return new ArrayList<>(admins); }

    // Course operations
    public static void addCourse(Course course) { courses.add(course); }
    public static List<Course> getAllCourses() { return new ArrayList<>(courses); }
    public static Course getCourseById(int courseId) {
        return courses.stream().filter(c -> c.getCourseId() == courseId).findFirst().orElse(null);
    }
    public static boolean removeCourse(int courseId) {
        Course course = getCourseById(courseId);
        return course != null && courses.remove(course);
    }

    // Category operations
    public static List<Category> getAllCategories() { return new ArrayList<>(categories); }

    // Assignment operations
    public static List<Assignment> getAllAssignments() { return new ArrayList<>(assignments); }
}

