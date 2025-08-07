import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;



class Course {
    private int courseId;
    private String title;
    private String description;
    private double price;
    private int instructorId;
    private Category category;
    private List<Integer> enrolledStudentIds;
    private List<Assignment> assignments;
    private boolean isActive;
    private static int nextCourseId = 1;

    public Course(String title, String description, double price, int instructorId, Category category) {
        this.courseId = nextCourseId++;
        this.title = title;
        this.description = description;
        this.price = price;
        this.instructorId = instructorId;
        this.category = category;
        this.enrolledStudentIds = new ArrayList<>();
        this.assignments = new ArrayList<>();
        this.isActive = true;
    }

    public boolean enrollStudent(int studentId) {
        if (isActive && !enrolledStudentIds.contains(studentId)) {
            enrolledStudentIds.add(studentId);
            return true;
        }
        return false;
    }


    public boolean removeStudent(int studentId) {
        return enrolledStudentIds.remove(Integer.valueOf(studentId));
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }



    public int getCourseId() {
        return courseId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getInstructorId() {
        return instructorId;
    }
    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public List<Integer> getEnrolledStudentIds() {
        return new ArrayList<>(enrolledStudentIds);
    }
    public List<Assignment> getAssignments() {
        return new ArrayList<>(assignments);
    }
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", title='" + title + '\'' +
                ", price=$" + price +
                ", instructor=" + instructorId +
                ", category=" + category.getName() +
                ", enrolledStudents=" + enrolledStudentIds.size() +
                ", active=" + isActive +
                '}';
    }


}