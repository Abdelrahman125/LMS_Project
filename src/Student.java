import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


class Student extends User {
    private String address;
    private Gender gender;
    private List<Integer> enrolledCourseIds;
    private Wallet wallet;

    public Student(String username, String password, LocalDate dateOfBirth,
                   String address, Gender gender, double initialBalance) {
        super(username, password, dateOfBirth);
        this.address = address;
        this.gender = gender;
        this.enrolledCourseIds = new ArrayList<>();
        this.wallet = new Wallet(this.userId, initialBalance);
    }

    public boolean enrollInCourse(Course course) {
        if (wallet.getBalance() >= course.getPrice() && !enrolledCourseIds.contains(course.getCourseId())) {
            if (wallet.deduct(course.getPrice(), "Course enrollment: " + course.getTitle())) {
                enrolledCourseIds.add(course.getCourseId());
                course.enrollStudent(this.userId);
                return true;
            }
        }
        return false;
    }


    @Override
    public void displayDashboard() {
        System.out.println("=== STUDENT DASHBOARD ===");
        System.out.println("Welcome, " + username);
        System.out.println("Balance: $" + wallet.getBalance());
        System.out.println("Enrolled Courses: " + enrolledCourseIds.size());
        System.out.println("========================");
    }


    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public List<Integer> getEnrolledCourseIds() {
        return new ArrayList<>(enrolledCourseIds);
    }
    public Wallet getWallet() {
        return wallet;
    }


    @Override
    public String toString() {
        return "Student{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", gender=" + gender +
                ", enrolledCourses=" + enrolledCourseIds.size() +
                ", balance=$" + wallet.getBalance() +
                '}';
    }



}