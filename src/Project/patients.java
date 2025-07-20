package Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class patients {
    private Connection connection;
    private Scanner scanner;

    public patients(Connection connection, Scanner scanner) {
        this.connection= connection;
        this.scanner= scanner;
    }

    public void addPatients(){
        System.out.print("Enter Patients name : ");
        String name= scanner.next();
        System.out.print("Enter your Age :  ");
        int age= scanner.nextInt();
        System.out.print("Enter Patients gender : ");
        String gender= scanner.next();

        try{
            String query= "INSERT INTO patients (name, age, gender) VALUES (?,?,?)";
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            int affectedRows= preparedStatement.executeUpdate();
            if(affectedRows>0){
                System.out.println("Patients Added Successfully");
            } else{
                System.out.println("Failed to add Patients.. ");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void viewPatients(){
        String query="Select * from patients";
        try{
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            ResultSet resultSet= preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+-------------+--------------+---------+-------------+");
            System.out.println("| PATIENTS ID | NAME          | AGE     | GENDER      |");
            System.out.println("+-------------+--------------+---------+-------------+");
            while (resultSet.next()){
                int id= resultSet.getInt("id");
                String name= resultSet.getString("name");
                int age= resultSet.getInt("age");
                String gender= resultSet.getString("gender");
                System.out.printf("|%-13s| %-15s | %-9s |%-13s\n", id, name, age, gender);
                System.out.println("+-------------+--------------+---------+-------------+");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean getPatientbyid (int id){
        String query= "Select * FROM patients WHERE id = ?";
        try{
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet= preparedStatement.executeQuery();
            if(resultSet.next()){
                return  true;
            } else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
