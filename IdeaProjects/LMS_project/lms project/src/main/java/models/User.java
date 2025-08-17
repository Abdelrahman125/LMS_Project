package models;

// Role.java
enum Role {
    ADMIN, INSTRUCTOR, STUDENT
}

public abstract class User {
    protected String username;
    protected String password;
    protected String dateOfBirth;
    protected Role role;

    public User(String username, String password, String dateOfBirth, Role role) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public Role getRole() { return role; }

    // Abstract methods to be implemented by subclasses
    public abstract void displayDashboard();
    public abstract String getProfileInfo();
}
