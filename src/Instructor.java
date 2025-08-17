import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;



class Instructor extends User {
    private String specialization;
    private List<Integer> assignedCourseIds;
    private Wallet wallet;

    public Instructor(String username, String password, LocalDate dateOfBirth, String specialization) {
        super(username, password, dateOfBirth);
        this.specialization = specialization;
        this.assignedCourseIds = new ArrayList<>();
        this.wallet = new Wallet(this.userId, 0.0);
    }

    public void assignToCourse(int courseId) {
        if (!assignedCourseIds.contains(courseId)) {
            assignedCourseIds.add(courseId);
        }
    }

    public void earnFromEnrollment(double amount, String courseName) {
        wallet.add(amount, "Student enrollment in: " + courseName);
    }

    @Override
    public void displayDashboard() {
        System.out.println("=== INSTRUCTOR DASHBOARD ===");
        System.out.println("Welcome, " + username);
        System.out.println("Specialization: " + specialization);
        System.out.println("Assigned Courses: " + assignedCourseIds.size());
        System.out.println("Earnings: $" + wallet.getBalance());
        System.out.println("============================");
    }


    public String getSpecialization() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    public List<Integer> getAssignedCourseIds() {
        return new ArrayList<>(assignedCourseIds);
    }
    public Wallet getWallet() {
        return wallet;
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", specialization='" + specialization + '\'' +
                ", assignedCourses=" + assignedCourseIds.size() +
                ", earnings=$" + wallet.getBalance() +
                '}';
    }
}