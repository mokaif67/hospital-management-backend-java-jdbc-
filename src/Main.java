import java.awt.*;
import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/mydb";
    private static final String username = "root";
    private static final String password = "Kaif@123";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");               //loading registers
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection connection = DriverManager.getConnection(url, username, password);      //to get connenction

            //now how can we insert data using prepared statement(which is fast compare to statement) --> Insert Case

//           String query="INSERT INTO student(name, age, marks ) Values(?, ?, ?)";
//          PreparedStatement preparedStatement= connection.prepareStatement(query);
//          preparedStatement.setString(1, "Akki");
//          preparedStatement.setInt(2, 22);
//          preparedStatement.setDouble(3, 99.2);

            //how to retreive data using it..

//          String query="SELECT marks FROM student Where id=?";
//          PreparedStatement preparedStatement= connection.prepareStatement(query);
//          preparedStatement.setInt(1,1);
//
//          ResultSet resultSet= preparedStatement.executeQuery();
//          if(resultSet.next()){
//              double mark= resultSet.getDouble("marks");
//              System.out.println("Marks : " + mark);
//          } else{
//              System.out.println("Marks Not Found...!!");
//          }

            //How to Update Data using Prepared Statement....

//            String query = "UPDATE STUDENT SET MARKS=? WHERE id=?";
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setDouble(1, 99.9);
//            preparedStatement.setInt(2,1);
//
//            int rowsAffected = preparedStatement.executeUpdate();
//            if (rowsAffected > 0) {
//                System.out.println("Data is updated Successfully..");
//            } else {
//                System.out.println("Data is not updated.. ");
//            }

            //How to delete data using prepared statement

//            String query = "DELETE FROM STUDENT WHERE id =?";
//            PreparedStatement preparedStatement= connection.prepareStatement(query);
//            preparedStatement.setInt(1,1);
//            int rowAffected= preparedStatement.executeUpdate();
//            if(rowAffected>0){
//                System.out.println("Data is deleted successfully..");
//            } else{
//                System.out.println("Data is not Deleted..!!");
//            }

//          }

//          Statement statement=connection.createStatement();

            //how to insert data

//          String query = String.format("Insert into student(name, age, marks) Values('%s', %o, %f)", "Sahil", 24, 80.5);     //to insert data

            //if you want to update data

//          String query2= String.format("Update student SET marks= %f where id = %d", 89.5,2 );             //to update data

            //how to delete data

//          String query3= "DELETE FROM student where ID=2";     // to delete data

//          int rowsAffected= preparedStatement.executeUpdate();
//          if(rowsAffected>0){
//              System.out.println("Data is inserted Successfully..");
//          } else{
//              System.out.println("Data is not inserted.. ");
//          }

            //how to retreive data using statement..

//          String query="select * from student";
//          ResultSet resultSet= statement.executeQuery(query);
//          while(resultSet.next()){
//              int id= resultSet.getInt("id");
//              String name = resultSet.getString("name");
//              int age= resultSet.getInt("age");
//              double marks= resultSet.getDouble("marks");
//              System.out.println("ID : " + id);
//              System.out.println("NAME : " + name);
//              System.out.println("Age : " + age);
//              System.out.println("Marks : " + marks)


            //Batch Procession --> store the multiple data in the more a batch

            // now process it using Statement

//            Statement statement= connection.createStatement();
//            Scanner scanner= new Scanner(System.in);
//            while(true){
//                System.out.print("Enter your name: ");
//                String name= scanner.next();
//                System.out.print("AGE: ");
//                int age= scanner.nextInt();
//                System.out.print("Enter your Marks: ");
//                double marks= scanner.nextDouble();
//                System.out.println("Enter more data(y/n): ");
//                String choice= scanner.next();
//                String query= String.format("INSERT INTO STUDENT (name, age, marks) VALUES ('%s' , %d, %f )", name, age, marks);
//                statement.addBatch(query);
//                if(choice.toUpperCase().equals("N")){
//                    break;
//                }
//            }
//            int [] arr= statement.executeBatch();
//            for(int i=0; i<arr.length; i++){
//                if(arr[i]==0){
//                    System.out.println("Query: " + i + "Not Execute Successfully...");
//                } else{
//                    System.out.println("Query is execute successfully..");
//                }
//            }


            //Now Batch Procession Using Prepared Statement

            String query= "INSERT INTO STUDENT (name, age, marks) VALUES (?,?,?)";
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            Scanner scanner= new Scanner(System.in);
            while(true) {
                System.out.print("Enter your name: ");
                String name = scanner.next();
                System.out.print("AGE: ");
                int age = scanner.nextInt();
                System.out.print("Enter your Marks: ");
                double marks = scanner.nextDouble();
                System.out.println("Enter more data(y/n): ");
                String choice = scanner.next();
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, age);
                preparedStatement.setDouble(3,marks);
                preparedStatement.addBatch();
                if(choice.toUpperCase().equals("N")){
                    break;
                }
            }
            int [] arr= preparedStatement.executeBatch();
            for(int i=0; i<arr.length; i++) {
                if (arr[i] == 0) {
                    System.out.println("Query: " + i + "Not Execute Successfully...");
                } else {
                    System.out.println("Query is execute successfully..");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}