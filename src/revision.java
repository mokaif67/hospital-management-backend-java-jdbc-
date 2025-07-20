import java.sql.*;

public class revision {
    private static final String url="jdbc:mysql://127.0.0.1:3306/mydb";
    private static final String username="root";
    private static final String password="Kaif@123";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");    //load driver
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try{
            Connection connection=DriverManager.getConnection(url, username, password);   //establish connection
            Statement statement= connection.createStatement();
            String query="select * from student";                          //load data
            ResultSet rs= statement.executeQuery(query);     //hold all data
            while(rs.next()){
                int id= rs.getInt("id");
                String name= rs.getString("name");
                int age= rs.getInt("age");
                double marks= rs.getDouble("marks");

                System.out.println("ID: " + id);                       //printing all data
                System.out.println("name: " + name);
                System.out.println("age: " + age);
                System.out.println("marks: " + marks);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
