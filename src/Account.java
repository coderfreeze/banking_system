public class Account {
    private final long accountNumber;
    private final String fullName;
    private final String email;
    private double balance;
    private final String securityPin;

    // Constructor
    public Account(long accountNumber, String fullName, String email, double balance, String securityPin) {
        this.accountNumber = accountNumber;
        this.fullName = fullName;
        this.email = email;
        this.balance = balance;
        this.securityPin = securityPin;
    }

    // Get and Set methods
    public long getAccountNumber() { return accountNumber; }
    public String getFullName() {return fullName; }
    public String getEmail() {return email; }
    public double getBalance() { return balance; }
    public String getSecurityPin() {return securityPin; }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited " + amount);
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew " + amount);
        } else {
            System.out.println("Invalid withdrawal amount or not enough funds");
        }
    }

    public void displayAccountInfo() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Full Name: " + fullName);
        System.out.println("Email: " + email);
        System.out.println("Balance " + balance);
    }
}
