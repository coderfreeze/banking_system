public class User {
    private final String email;
    private final String fullName;
    private final String password;

    // constructor
    public User(String email, String fullName, String password) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
    }

    // Getters
    public String getEmail() {
        return email;
    }
    public String getFullName() { return fullName; }
    public String getPassword() {
        return password;
    }

    public boolean verifyPassword(String password){
        return this.password.equals(password);
    }
}

