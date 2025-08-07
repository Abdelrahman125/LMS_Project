import java.time.LocalDate;

abstract class User {
    protected String username;
    protected String password;
    protected LocalDate dateOfBirth;
    protected int userId;
    protected static int nextUserId = 1000;

    public User(String username, String password, LocalDate dateOfBirth) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.userId = nextUserId++;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    public int getUserId() {
        return userId;
    }
    public abstract void displayDashboard();




    //login method
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }



    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
    enum Gender {
        MALE, FEMALE, OTHER
    }



}