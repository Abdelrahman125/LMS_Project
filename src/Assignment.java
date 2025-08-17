import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;



class Assignment {
    private int assignmentId;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private int courseId;
    private double maxGrade;
    private Map<Integer, Double> studentGrades; // studentId -> grade
    private static int nextAssignmentId = 1;

    public Assignment(String title, String description, LocalDateTime deadline, int courseId, double maxGrade) {
        this.assignmentId = nextAssignmentId++;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.courseId = courseId;
        this.maxGrade = maxGrade;
        this.studentGrades = new HashMap<>();
    }

    public boolean isDeadlinePassed() {
        return LocalDateTime.now().isAfter(deadline);
    }

    public void gradeStudent(int studentId, double grade) {
        if (grade >= 0 && grade <= maxGrade) {
            studentGrades.put(studentId, grade);
        }
    }
    public int getAssignmentId() {
        return assignmentId;
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
    public LocalDateTime getDeadline() {
        return deadline;
    }
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
    public int getCourseId() {
        return courseId;
    }
    public double getMaxGrade() {
        return maxGrade;
    }
    public void setMaxGrade(double maxGrade) {
        this.maxGrade = maxGrade;
    }
    public Map<Integer, Double> getStudentGrades() {
        return new HashMap<>(studentGrades);
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "assignmentId=" + assignmentId +
                ", title='" + title + '\'' +
                ", deadline=" + deadline +
                ", courseId=" + courseId +
                ", maxGrade=" + maxGrade +
                '}';
    }
}