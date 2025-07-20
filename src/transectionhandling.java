import java.sql.*;
import java.util.Scanner;

public class transectionhandling {
    private static final String url="jdbc:mysql://127.0.0.1:3306/bank";
    private static final String username="root";
    private static final String password="Kaif@123";
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try{
            Connection connection=DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
            String debit_query="UPDATE accounts SET balance = balance-? WHERE account_number=?";
            String credit_query="UPDATE accounts SET balance = balance+? WHERE account_number=?";
            PreparedStatement debitPreparedStatement = connection.prepareStatement(debit_query);
            PreparedStatement creditPreparedStatement = connection.prepareStatement(credit_query);
            Scanner scanner= new Scanner(System.in);
            System.out.print("Enter the account number: ");
            int account_number= scanner.nextInt();
            System.out.print("Enter the amount you want to debit or credit: ");
            double amount= scanner.nextDouble();;
            debitPreparedStatement.setDouble(1,amount);
            debitPreparedStatement.setInt(2,account_number);
            creditPreparedStatement.setDouble(1,amount);
            creditPreparedStatement.setInt(2,account_number);
            if(isSufficient(connection, account_number, amount)) {
                int affectedRow1 = debitPreparedStatement.executeUpdate();
                int affectedRow2 = creditPreparedStatement.executeUpdate();
                connection.commit();
                System.out.println("Transection successfull");
            } else{
                connection.rollback();
                System.out.println("INsufficient funds..");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    static  boolean isSufficient( Connection connection, int account_number, double amount){
        try{
            String query="SELECT balance FROM accounts WHERE account_number=?";
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setInt(1,account_number);
            ResultSet resultSet= preparedStatement.executeQuery();
            if(resultSet.next()){
                double current_balance= resultSet.getDouble("balance");
                if(amount>current_balance){
                    return false;
                } else{
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
