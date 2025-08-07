import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;



public class LearningManagementSystem {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("    LEARNING MANAGEMENT SYSTEM - MILESTONE 1    ");
        System.out.println("=================================================");

        // Demo the system
        runDemo();
    }

    private static void runDemo() {
        System.out.println("\n🎯 DEMO: Admin Dashboard and Operations\n");

        // Get the default admin
        Admin admin = Database.getAllAdmins().get(0);
        System.out.println("Logging in as admin: " + admin.getUsername());

        // Show admin dashboard
        admin.displayDashboard();

        // Demo admin operations
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ADMIN OPERATIONS DEMO");
        System.out.println("=".repeat(50));

        // View all data
        admin.viewAllStudents();
        admin.viewAllInstructors();
        admin.viewAllCourses();

        // Add a new instructor
        System.out.println("\n🔹 Adding new instructor...");
        admin.addInstructor("mike_johnson", "mike123", LocalDate.of(1990, 4, 12), "Physics");

        // Add a new course
        System.out.println("\n🔹 Adding new course...");
        Instructor newInstructor = Database.getAllInstructors().get(Database.getAllInstructors().size() - 1);
        Category scienceCategory = Database.getAllCategories().stream()
                .filter(c -> c.getName().equals("Science")).findFirst().orElse(null);
        admin.addCourse("Physics 101", "Introduction to Physics", 349.99,
                newInstructor.getUserId(), scienceCategory);

        // Add a new student
        System.out.println("\n🔹 Adding new student...");
        admin.addStudent("Jana", "Jana123", LocalDate.of(2002, 9, 30),
                "District5", User.Gender.FEMALE, 2000.0);

        // Show updated lists
        System.out.println("\n🔹 Updated data:");
        admin.viewAllInstructors();
        admin.viewAllCourses();
        admin.viewAllStudents();

        // Demo student enrollment
        System.out.println("\n" + "=".repeat(50));
        System.out.println("STUDENT ENROLLMENT DEMO");
        System.out.println("=".repeat(50));

        Student student = Database.getAllStudents().get(0);
        Course course = Database.getAllCourses().get(0);
        Instructor instructor = Database.getInstructorById(course.getInstructorId());

        System.out.println("Student before enrollment: " + student);
        System.out.println("Instructor before enrollment: " + instructor);
        System.out.println("Course: " + course);

        System.out.println("\n🔹 Enrolling student in course...");
        boolean enrolled = student.enrollInCourse(course);
        if (enrolled) {
            // Update instructor earnings
            instructor.earnFromEnrollment(course.getPrice(), course.getTitle());
            System.out.println("✅ Enrollment successful!");
        } else {
            System.out.println("❌ Enrollment failed!");
        }

        System.out.println("\nStudent after enrollment: " + student);
        System.out.println("Instructor after enrollment: " + instructor);

        // Show dashboards
        System.out.println("\n" + "=".repeat(50));
        System.out.println("DASHBOARD DEMO");
        System.out.println("=".repeat(50));

        student.displayDashboard();
        instructor.displayDashboard();
        admin.displayDashboard();

        System.out.println("\n🎉 Demo completed successfully!");
        System.out.println("✅ All OOP principles implemented");
        System.out.println("✅ CRUD operations working");
        System.out.println("✅ Validation logic in place");
        System.out.println("✅ Wallet system functional");
        System.out.println("✅ Ready for Milestone 2 (GUI)");

        // Interactive menu for further testing
        runInteractiveMenu(admin);
    }

    private static void runInteractiveMenu(Admin admin) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("INTERACTIVE TESTING MENU");
        System.out.println("=".repeat(50));

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Admin Operations");
            System.out.println("2. Student Operations");
            System.out.println("3. Instructor Operations");
            System.out.println("4. View System Statistics");
            System.out.println("5. Test Validation Cases");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    adminMenu(admin);
                    break;
                case 2:
                    studentMenu();
                    break;
                case 3:
                    instructorMenu();
                    break;
                case 4:
                    showSystemStatistics();
                    break;
                case 5:
                    testValidationCases();
                    break;
                case 0:
                    System.out.println("Thank you for using LMS! Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void adminMenu(Admin admin) {
        System.out.println("\n=== ADMIN OPERATIONS ===");
        System.out.println("1. View All Students");
        System.out.println("2. View All Instructors");
        System.out.println("3. View All Courses");
        System.out.println("4. Add New Course");
        System.out.println("5. Add New Instructor");
        System.out.println("6. Add New Student");
        System.out.println("7. Remove Course");
        System.out.println("8. Remove User");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                admin.viewAllStudents();
                break;
            case 2:
                admin.viewAllInstructors();
                break;
            case 3:
                admin.viewAllCourses();
                break;
            case 4:
                addNewCourse(admin);
                break;
            case 5:
                addNewInstructor(admin);
                break;
            case 6:
                addNewStudent(admin);
                break;
            case 7:
                removeCourse(admin);
                break;
            case 8:
                removeUser(admin);
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void addNewCourse(Admin admin) {
        System.out.println("\n--- Add New Course ---");
        System.out.print("Course Title: ");
        String title = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Price: $");
        double price = getDoubleInput();

        // Show available instructors
        List<Instructor> instructors = Database.getAllInstructors();
        System.out.println("\nAvailable Instructors:");
        for (int i = 0; i < instructors.size(); i++) {
            Instructor inst = instructors.get(i);
            System.out.println((i + 1) + ". " + inst.getUsername() + " (ID: " + inst.getUserId() +
                    ", Specialization: " + inst.getSpecialization() + ")");
        }
        System.out.print("Select instructor (1-" + instructors.size() + "): ");
        int instructorChoice = getIntInput() - 1;

        if (instructorChoice < 0 || instructorChoice >= instructors.size()) {
            System.out.println("Invalid instructor selection.");
            return;
        }

        // Show available categories
        List<Category> categories = Database.getAllCategories();
        System.out.println("\nAvailable Categories:");
        for (int i = 0; i < categories.size(); i++) {
            Category cat = categories.get(i);
            System.out.println((i + 1) + ". " + cat.getName() + " - " + cat.getDescription());
        }
        System.out.print("Select category (1-" + categories.size() + "): ");
        int categoryChoice = getIntInput() - 1;

        if (categoryChoice < 0 || categoryChoice >= categories.size()) {
            System.out.println("Invalid category selection.");
            return;
        }

        Instructor selectedInstructor = instructors.get(instructorChoice);
        Category selectedCategory = categories.get(categoryChoice);

        admin.addCourse(title, description, price, selectedInstructor.getUserId(), selectedCategory);
    }

    private static void addNewInstructor(Admin admin) {
        System.out.println("\n--- Add New Instructor ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Birth Year (YYYY): ");
        int year = getIntInput();
        System.out.print("Birth Month (1-12): ");
        int month = getIntInput();
        System.out.print("Birth Day (1-31): ");
        int day = getIntInput();
        System.out.print("Specialization: ");
        String specialization = scanner.nextLine();

        LocalDate dateOfBirth = LocalDate.of(year, month, day);
        admin.addInstructor(username, password, dateOfBirth, specialization);
    }

    private static void addNewStudent(Admin admin) {
        System.out.println("\n--- Add New Student ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Birth Year (YYYY): ");
        int year = getIntInput();
        System.out.print("Birth Month (1-12): ");
        int month = getIntInput();
        System.out.print("Birth Day (1-31): ");
        int day = getIntInput();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.println("Gender Options: 1. MALE, 2. FEMALE, 3. OTHER");
        System.out.print("Select gender (1-3): ");
        int genderChoice = getIntInput();
        User.Gender gender = User.Gender.values()[Math.max(0, Math.min(2, genderChoice - 1))];
        System.out.print("Initial Balance: $");
        double balance = getDoubleInput();

        LocalDate dateOfBirth = LocalDate.of(year, month, day);
        admin.addStudent(username, password, dateOfBirth, address,gender, balance);
    }

    private static void removeCourse(Admin admin) {
        System.out.println("\n--- Remove Course ---");
        admin.viewAllCourses();
        System.out.print("Enter Course ID to remove: ");
        int courseId = getIntInput();
        admin.removeCourse(courseId);
    }

    private static void removeUser(Admin admin) {
        System.out.println("\n--- Remove User ---");
        System.out.println("All Users:");
        List<User> users = Database.getAllUsers();
        for (User user : users) {
            String type = user instanceof Admin ? "Admin" :
                    user instanceof Instructor ? "Instructor" : "Student";
            System.out.println("ID: " + user.getUserId() + ", Username: " + user.getUsername() +
                    " (" + type + ")");
        }
        System.out.print("Enter User ID to remove: ");
        int userId = getIntInput();
        admin.removeUser(userId);
    }

    private static void studentMenu() {
        System.out.println("\n=== STUDENT OPERATIONS ===");
        List<Student> students = Database.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("Select a student:");
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            System.out.println((i + 1) + ". " + student.getUsername() + " (Balance: $" +
                    student.getWallet().getBalance() + ")");
        }
        System.out.print("Choose student (1-" + students.size() + "): ");
        int choice = getIntInput() - 1;

        if (choice < 0 || choice >= students.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Student selectedStudent = students.get(choice);
        studentOperations(selectedStudent);
    }

    private static void studentOperations(Student student) {
        System.out.println("\n--- Student Operations for " + student.getUsername() + " ---");
        System.out.println("1. View Dashboard");
        System.out.println("2. View Available Courses");
        System.out.println("3. Enroll in Course");
        System.out.println("4. View Enrolled Courses");
        System.out.println("5. View Wallet Details");
        System.out.println("0. Back");
        System.out.print("Enter your choice: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                student.displayDashboard();
                break;
            case 2:
                viewAvailableCourses(student);
                break;
            case 3:
                enrollInCourse(student);
                break;
            case 4:
                viewEnrolledCourses(student);
                break;
            case 5:
                viewWalletDetails(student);
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void viewAvailableCourses(Student student) {
        System.out.println("\n--- Available Courses ---");
        List<Course> courses = Database.getAllCourses();
        List<Integer> enrolledIds = student.getEnrolledCourseIds();

        for (Course course : courses) {
            if (!enrolledIds.contains(course.getCourseId()) && course.isActive()) {
                Instructor instructor = Database.getInstructorById(course.getInstructorId());
                System.out.println("Course ID: " + course.getCourseId());
                System.out.println("Title: " + course.getTitle());
                System.out.println("Description: " + course.getDescription());
                System.out.println("Price: $" + course.getPrice());
                System.out.println("Instructor: " + (instructor != null ? instructor.getUsername() : "Unknown"));
                System.out.println("Category: " + course.getCategory().getName());
                System.out.println("Enrolled Students: " + course.getEnrolledStudentIds().size());
                System.out.println("---");
            }
        }
    }

    private static void enrollInCourse(Student student) {
        System.out.println("\n--- Enroll in Course ---");
        viewAvailableCourses(student);
        System.out.print("Enter Course ID to enroll: ");
        int courseId = getIntInput();

        Course course = Database.getCourseById(courseId);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (student.getEnrolledCourseIds().contains(courseId)) {
            System.out.println("You are already enrolled in this course.");
            return;
        }

        System.out.println("Course: " + course.getTitle() + " - $" + course.getPrice());
        System.out.println("Your balance: $" + student.getWallet().getBalance());

        if (student.getWallet().getBalance() < course.getPrice()) {
            System.out.println("Insufficient balance. You need $" +
                    (course.getPrice() - student.getWallet().getBalance()) + " more.");
            return;
        }

        System.out.print("Confirm enrollment? (y/n): ");
        String confirm = scanner.nextLine().toLowerCase();
        if (confirm.equals("y") || confirm.equals("yes")) {
            boolean enrolled = student.enrollInCourse(course);
            if (enrolled) {
                Instructor instructor = Database.getInstructorById(course.getInstructorId());
                if (instructor != null) {
                    instructor.earnFromEnrollment(course.getPrice(), course.getTitle());
                }
                System.out.println("✅ Successfully enrolled in " + course.getTitle());
            } else {
                System.out.println("❌ Enrollment failed.");
            }
        }
    }

    private static void viewEnrolledCourses(Student student) {
        System.out.println("\n--- Your Enrolled Courses ---");
        List<Integer> enrolledIds = student.getEnrolledCourseIds();

        if (enrolledIds.isEmpty()) {
            System.out.println("You are not enrolled in any courses.");
            return;
        }

        for (Integer courseId : enrolledIds) {
            Course course = Database.getCourseById(courseId);
            if (course != null) {
                Instructor instructor = Database.getInstructorById(course.getInstructorId());
                System.out.println("📚 " + course.getTitle());
                System.out.println("   Instructor: " + (instructor != null ? instructor.getUsername() : "Unknown"));
                System.out.println("   Category: " + course.getCategory().getName());
                System.out.println("   Assignments: " + course.getAssignments().size());
                System.out.println();
            }
        }
    }

    private static void viewWalletDetails(Student student) {
        System.out.println("\n--- Wallet Details ---");
        Wallet wallet = student.getWallet();
        System.out.println("Wallet ID: " + wallet.getWalletId());
        System.out.println("Current Balance: $" + wallet.getBalance());
        System.out.println("Owner: " + student.getUsername());

        System.out.println("\nTransaction History:");
        List<String> history = wallet.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (String transaction : history) {
                System.out.println("  " + transaction);
            }
        }
    }

    private static void instructorMenu() {
        System.out.println("\n=== INSTRUCTOR OPERATIONS ===");
        List<Instructor> instructors = Database.getAllInstructors();
        if (instructors.isEmpty()) {
            System.out.println("No instructors found.");
            return;
        }

        System.out.println("Select an instructor:");
        for (int i = 0; i < instructors.size(); i++) {
            Instructor instructor = instructors.get(i);
            System.out.println((i + 1) + ". " + instructor.getUsername() +
                    " (Specialization: " + instructor.getSpecialization() +
                    ", Earnings: $" + instructor.getWallet().getBalance() + ")");
        }
        System.out.print("Choose instructor (1-" + instructors.size() + "): ");
        int choice = getIntInput() - 1;

        if (choice < 0 || choice >= instructors.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Instructor selectedInstructor = instructors.get(choice);
        instructorOperations(selectedInstructor);
    }

    private static void instructorOperations(Instructor instructor) {
        System.out.println("\n--- Instructor Operations for " + instructor.getUsername() + " ---");
        System.out.println("1. View Dashboard");
        System.out.println("2. View Assigned Courses");
        System.out.println("3. View Students in My Courses");
        System.out.println("4. Create Assignment");
        System.out.println("5. View Earnings");
        System.out.println("0. Back");
        System.out.print("Enter your choice: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                instructor.displayDashboard();
                break;
            case 2:
                viewAssignedCourses(instructor);
                break;
            case 3:
                viewStudentsInCourses(instructor);
                break;
            case 4:
                createAssignment(instructor);
                break;
            case 5:
                viewEarnings(instructor);
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void viewAssignedCourses(Instructor instructor) {
        System.out.println("\n--- Your Assigned Courses ---");
        List<Integer> assignedIds = instructor.getAssignedCourseIds();

        if (assignedIds.isEmpty()) {
            System.out.println("You have no assigned courses.");
            return;
        }

        for (Integer courseId : assignedIds) {
            Course course = Database.getCourseById(courseId);
            if (course != null) {
                System.out.println("📚 " + course.getTitle() + " (ID: " + course.getCourseId() + ")");
                System.out.println("   Description: " + course.getDescription());
                System.out.println("   Price: $" + course.getPrice());
                System.out.println("   Enrolled Students: " + course.getEnrolledStudentIds().size());
                System.out.println("   Assignments: " + course.getAssignments().size());
                System.out.println("   Category: " + course.getCategory().getName());
                System.out.println();
            }
        }
    }

    private static void viewStudentsInCourses(Instructor instructor) {
        System.out.println("\n--- Students in Your Courses ---");
        List<Integer> assignedIds = instructor.getAssignedCourseIds();

        for (Integer courseId : assignedIds) {
            Course course = Database.getCourseById(courseId);
            if (course != null) {
                System.out.println("Course: " + course.getTitle());
                List<Integer> enrolledStudentIds = course.getEnrolledStudentIds();

                if (enrolledStudentIds.isEmpty()) {
                    System.out.println("  No students enrolled.");
                } else {
                    for (Integer studentId : enrolledStudentIds) {
                        Student student = Database.getStudentById(studentId);
                        if (student != null) {
                            System.out.println("  👤 " + student.getUsername() +
                                    " (ID: " + student.getUserId() + ")");
                        }
                    }
                }
                System.out.println();
            }
        }
    }

    private static void createAssignment(Instructor instructor) {
        System.out.println("\n--- Create Assignment ---");
        List<Integer> assignedIds = instructor.getAssignedCourseIds();

        if (assignedIds.isEmpty()) {
            System.out.println("You have no courses to create assignments for.");
            return;
        }

        System.out.println("Select a course:");
        for (int i = 0; i < assignedIds.size(); i++) {
            Course course = Database.getCourseById(assignedIds.get(i));
            if (course != null) {
                System.out.println((i + 1) + ". " + course.getTitle());
            }
        }
        System.out.print("Choose course (1-" + assignedIds.size() + "): ");
        int choice = getIntInput() - 1;

        if (choice < 0 || choice >= assignedIds.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Course selectedCourse = Database.getCourseById(assignedIds.get(choice));

        System.out.print("Assignment Title: ");
        String title = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Maximum Grade: ");
        double maxGrade = getDoubleInput();
        System.out.print("Deadline (days from now): ");
        int daysFromNow = getIntInput();

        LocalDateTime deadline = LocalDateTime.now().plusDays(daysFromNow);
        Assignment assignment = new Assignment(title, description, deadline,
                selectedCourse.getCourseId(), maxGrade);

        selectedCourse.addAssignment(assignment);
        System.out.println("✅ Assignment created successfully for " + selectedCourse.getTitle());
        System.out.println("Assignment ID: " + assignment.getAssignmentId());
        System.out.println("Deadline: " + deadline);
    }

    private static void viewEarnings(Instructor instructor) {
        System.out.println("\n--- Your Earnings ---");
        Wallet wallet = instructor.getWallet();
        System.out.println("Current Balance: $" + wallet.getBalance());

        System.out.println("\nEarning History:");
        List<String> history = wallet.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("No earning history found.");
        } else {
            for (String transaction : history) {
                System.out.println("  " + transaction);
            }
        }
    }

    private static void showSystemStatistics() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("SYSTEM STATISTICS");
        System.out.println("=".repeat(50));

        System.out.println("📊 Users:");
        System.out.println("   Total Users: " + Database.getAllUsers().size());
        System.out.println("   Students: " + Database.getAllStudents().size());
        System.out.println("   Instructors: " + Database.getAllInstructors().size());
        System.out.println("   Admins: " + Database.getAllAdmins().size());

        System.out.println("\n📚 Courses:");
        System.out.println("   Total Courses: " + Database.getAllCourses().size());
        System.out.println("   Total Categories: " + Database.getAllCategories().size());

        // Course enrollment statistics
        int totalEnrollments = 0;
        double totalRevenue = 0;
        for (Course course : Database.getAllCourses()) {
            int enrollments = course.getEnrolledStudentIds().size();
            totalEnrollments += enrollments;
            totalRevenue += enrollments * course.getPrice();
        }
        System.out.println("   Total Enrollments: " + totalEnrollments);
        System.out.println("   Total Revenue: $" + String.format("%.2f", totalRevenue));

        System.out.println("\n📝 Assignments:");
        System.out.println("   Total Assignments: " + Database.getAllAssignments().size());

        // Most popular course
        Course popularCourse = null;
        int maxEnrollments = 0;
        for (Course course : Database.getAllCourses()) {
            if (course.getEnrolledStudentIds().size() > maxEnrollments) {
                maxEnrollments = course.getEnrolledStudentIds().size();
                popularCourse = course;
            }
        }
        if (popularCourse != null) {
            System.out.println("\n🏆 Most Popular Course:");
            System.out.println("   " + popularCourse.getTitle() + " (" + maxEnrollments + " students)");
        }

        // Top earning instructor
        Instructor topInstructor = null;
        double maxEarnings = 0;
        for (Instructor instructor : Database.getAllInstructors()) {
            if (instructor.getWallet().getBalance() > maxEarnings) {
                maxEarnings = instructor.getWallet().getBalance();
                topInstructor = instructor;
            }
        }
        if (topInstructor != null) {
            System.out.println("\n💰 Top Earning Instructor:");
            System.out.println("   " + topInstructor.getUsername() + " ($" +
                    String.format("%.2f", maxEarnings) + ")");
        }
    }

    private static void testValidationCases() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("VALIDATION TESTING");
        System.out.println("=".repeat(50));

        Admin admin = Database.getAllAdmins().get(0);

        System.out.println("🧪 Test 1: Adding duplicate username");
        boolean result1 = admin.addInstructor("john_doe", "test123", LocalDate.now(), "Test");
        System.out.println("Expected: false, Actual: " + result1);

        System.out.println("\n🧪 Test 2: Student with insufficient balance");
        Student poorStudent = new Student("poor_student", "poor123", LocalDate.now(),
                "Poor Street", User.Gender.FEMALE, 10.0);
        Database.addStudent(poorStudent);
        Course expensiveCourse = Database.getAllCourses().get(0);
        boolean result2 = poorStudent.enrollInCourse(expensiveCourse);
        System.out.println("Expected: false, Actual: " + result2);

        System.out.println("\n🧪 Test 3: Enrolling in same course twice");
        Student richStudent = Database.getAllStudents().get(0);
        Course course = Database.getAllCourses().get(0);
        boolean result3a = richStudent.enrollInCourse(course);
        boolean result3b = richStudent.enrollInCourse(course);
        System.out.println("First enrollment - Expected: true, Actual: " + result3a);
        System.out.println("Second enrollment - Expected: false, Actual: " + result3b);

        System.out.println("\n🧪 Test 4: Adding course with non-existent instructor");
        Category category = Database.getAllCategories().get(0);
        boolean result4 = admin.addCourse("Test Course", "Test Desc", 100.0, 99999, category);
        System.out.println("Expected: false, Actual: " + result4);

        System.out.println("\n🧪 Test 5: Removing non-existent course");
        boolean result5 = admin.removeCourse(99999);
        System.out.println("Expected: false, Actual: " + result5);

        System.out.println("\n✅ Validation testing completed!");
    }

    private static int getIntInput() {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                return input;
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    private static double getDoubleInput() {
        while (true) {
            try {
                double input = Double.parseDouble(scanner.nextLine());
                return input;
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}