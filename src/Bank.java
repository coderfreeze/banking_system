import java.sql.*;

public class Bank {
    private final Connection connection;

    public Bank(Connection connection) {
        this.connection = connection;
    }

    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (email, full_name, password) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getFullName());
            statement.setString(3, user.getPassword());
            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User loginUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String dbPassword = rs.getString("password");
                if (dbPassword.equals(password)) {
                    String fullName = rs.getString("full_name");
                    return new User(email, fullName, password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createAccount(Account account) {
        String sql = "INSERT INTO accounts (full_name, email, balance, security_pin) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, account.getFullName());
            statement.setString(2, account.getEmail());
            statement.setDouble(3, account.getBalance());
            statement.setString(4, account.getSecurityPin());
            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Get Account using email
    public Account getAccountByEmail(String email) {
        String sql = "SELECT * FROM accounts WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                long accountNumber = rs.getLong("account_number");
                String fullName = rs.getString("full_name");
                double balance = rs.getDouble("balance");
                String securityPin = rs.getString("security_pin");
                return new Account(accountNumber, fullName, email, balance, securityPin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Deposit to an account
    public boolean depositToAccount(String email, double amount) {
        Account account = getAccountByEmail(email);
        if (account != null) {
            account.deposit(amount);
            String sql = "UPDATE accounts SET balance = ? WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setDouble(1, account.getBalance());
                statement.setString(2, email);
                int rows = statement.executeUpdate();
                return rows > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Withdraw from account
    public boolean withdrawFromAccount(String email, double amount) {
        Account account = getAccountByEmail(email);
        if (account != null) {
            account.withdraw(amount);
            String sql = "UPDATE accounts SET balance = ? WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setDouble(1, account.getBalance());
                statement.setString(2, email);
                int rows = statement.executeUpdate();
                return rows > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
