import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class BankApp {
    public static void main(String[] args) {
        try {
            // Create a connection first
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking_system", "root", "sqlpass123");
            // Create bank object connected to database
            Bank bank = new Bank(connection);
            Scanner scanner = new Scanner(System.in);

            System.out.println("*** Welcome to Professional Banking System *** \n How can we assist you today?\n");

            while (true) {
                System.out.println("1. Register User");
                System.out.println("2. Login User");
                System.out.println("3. Exit");

                int choice = scanner.nextInt();

                scanner.nextLine();

                // REGISTER USER
                if (choice == 1) {
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Full Name: ");
                    String fullName = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    User user = new User(email, fullName, password);
                    if (bank.registerUser(user)) {
                        System.out.println("Registration Successful\n");
                    } else {
                        System.out.println("Registration Failed");
                    }
                }



                // LOGIN USER
                if (choice == 2) {
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    User user = bank.loginUser(email, password);
                    if (user != null) {
                        System.out.println("Login Successful.\n");

                        // ACCOUNT DOES NOT EXIST
                        if (bank.getAccountByEmail(user.getEmail()) == null) {
                            System.out.println("Looks like you need to create an account.\n Let's create your security PIN: ");
                            String securityPin = scanner.nextLine();
                            Account account = new Account(0, user.getFullName(), user.getEmail(), 0.0, securityPin);
                            if (bank.createAccount(account)) {
                                System.out.println("Account created successfully!");
                            } else {
                                System.out.println("Account creation failed.");
                            }
                        }


                        // ACCOUNT EXISTS: SECURITY
                        System.out.println("Enter your Account security PIN to continue: ");
                        String checkPin = scanner.nextLine();
                        while (!Objects.equals(checkPin, bank.getAccountByEmail(user.getEmail()).getSecurityPin())) {
                            System.out.println("Incorrect PIN, Try Again: ");
                            checkPin = scanner.nextLine();
                        }


                        // ACCOUNT LOGGED IN: Account Operations
                        while (true) {
                            // ACCOUNT LOGGED IN
                            System.out.println("Hello " + user.getFullName());
                            System.out.println("Balance: " + bank.getAccountByEmail(user.getEmail()).getBalance() + "\n");
                            System.out.println("1. Deposit");
                            System.out.println("2. Withdraw");
                            System.out.println("3. Loan Simulator");
                            System.out.println("4. Logout");

                            int operation = scanner.nextInt();
                            scanner.nextLine(); // consume new line

                            if (operation == 1) {
                                // Deposit
                                System.out.print("Amount ");
                                double amount = scanner.nextDouble();
                                if (bank.depositToAccount(user.getEmail(), amount)) {
                                    System.out.println("Deposit Successful.\n");
                                } else {
                                    System.out.println("Deposit Failed.");
                                }
                            } else if (operation == 2) {
                                // Withdraw
                                System.out.println("Amount: ");
                                double amount = scanner.nextDouble();
                                if (bank.withdrawFromAccount(user.getEmail(), amount)) {
                                    System.out.println("Withdrawal successful.\n");
                                } else {
                                    System.out.println("Withdrawal failed");
                                }
                            } else if (operation == 3) {
                                // LOAN SIMULATOR
                                System.out.println("*** Welcome to loan simulator ***\n");
                                System.out.println("How much do you want to borrow?");
                                System.out.print("$"); double principal = scanner.nextDouble();
                                System.out.println("What is your interest rate? (APR)");
                                double interestRate = scanner.nextDouble();
                                System.out.println("In how many months do you want to pay this off?");
                                int months = scanner.nextInt();
                                double monthlyPayment = (principal * (interestRate/100/12) * Math.pow(1+(interestRate/100/12), months) ) / ( (Math.pow(1+(interestRate/100/12), months)) - 1 );
                                System.out.println("Your monthly payment would be: $" + String.format("%.2f", monthlyPayment) + "\n");

                            } else if (operation == 4) {
                                // LOGOUT ACCOUNT
                                System.out.println("You have logged out of your bank account");
                                break;
                            }
                        }
                    } else {
                        System.out.println("Login failed. User does not exist or incorrect email or password");
                    }
                }


                // EXIT
                if (choice == 3) {
                    // LOGOUT USER
                    System.out.println("You have been logged out.");
                    break;
                }



            }
            scanner.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}