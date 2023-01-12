package banking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CardRepository {
    private Connection conn;

    public CardRepository(String fileName) {
        try {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS card (\n" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                    "number VARCHAR NOT NULL, \n" +
                    "pin VARCHAR NOT NULL, \n" +
                    "balance INTEGER DEFAULT 0);";
            conn = DriverManager.getConnection("jdbc:sqlite:" + fileName);
            Statement statement = conn.createStatement();
            statement.execute(createTableSQL);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBalance(Account account) {
        try {
            Statement statement = conn.createStatement();
            String updateSQL = "UPDATE card SET balance=" + account.getBalance() + " WHERE number=" + account.getCardNumber();
            statement.execute(updateSQL);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showBalance(Account newAccount) {
        try {
            Statement stm1 = conn.createStatement();
            ResultSet rs = stm1.executeQuery("select balance from card WHERE number =" + newAccount.getCardNumber());
            while (rs.next()) {
                double balance = rs.getDouble("balance");
                System.out.println("Balance: " + balance);
                stm1.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void updateAccount(Account newaccount) {
        String sql = "UPDATE card SET " +
                "pin = ?," +
                "balance = ?" +
                "WHERE number = ?";
        RuntimeException exception = new RuntimeException();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newaccount.getPin());
            pstmt.setInt(2, (int) newaccount.getBalance());
            pstmt.setString(3, newaccount.getCardNumber());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAccount(Account newaccount) {
        try {
            PreparedStatement pstmt3 = conn.prepareStatement("DELETE FROM card where number = ?");
            pstmt3.setString(1, newaccount.getCardNumber());
            pstmt3.execute();
            pstmt3.close();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }


    public void insert(Account newaccount) {
        try {
            String sql = "INSERT INTO card(number, pin, balance) VALUES(?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newaccount.getCardNumber());
            pstmt.setString(2, newaccount.getPin());
            pstmt.setInt(3, (int) newaccount.getBalance());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Account> findAllAccounts(Account account) {
        List<Account> accountList = new ArrayList<Account>();


        String sql = "SELECT * FROM card where number" + account.getCardNumber();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                Account a = new Account();
                a.setCardNumber(rs.getString("number"));
                a.setPin(rs.getString("pin"));
                a.setBalance(rs.getInt("balance"));
                accountList.add(a);
                //System.out.println("Account loaded: " + a);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return accountList;
    }






}












