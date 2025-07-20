package Project;

import java.sql.*;
import java.util.Scanner;

public class hospitalmanagementgroup {
    private static final String url="jdbc:mysql://127.0.0.1:3306/hospital";
    private static final String username="root";
    private static final String password="Kaif@123";

    public static void main(String[] args) {
           try{
               Class.forName("com.mysql.cj.jdbc.Driver");
           } catch (ClassNotFoundException e) {
               e.printStackTrace();
           }
        Scanner scanner= new Scanner(System.in);
           try{
               Connection connection= DriverManager.getConnection(url,username, password);
               patients patients= new patients(connection,scanner);
               doctors doctors= new doctors(connection);
               while(true){
                   System.out.println("================================");
                   System.out.println("HOSPITAL MANAGEMENT SYSTEM...");
                   System.out.println("1. ADD PATIENTS ");
                   System.out.println("2. VIEW PATIENTS ");
                   System.out.println("3. VIEW DOCTORS ");
                   System.out.println("4. BOOK APPOINTMENT ");
                   System.out.println("5. EXIT ");
                   System.out.println("================================");

                   System.out.print("Enter your choice : ");
                   int choice= scanner.nextInt();

                   switch (choice){
                       case 1 :
                           //Add patients
                           patients.addPatients();
                           System.out.println();
                           break;
                       case 2 :
                           //view patients
                           patients.viewPatients();
                           System.out.println();
                           break;
                       case 3:
                           //view doctors
                           doctors.viewDoctors();
                           System.out.println();
                           break;
                       case 4:
                           //book appointments
                           bookappointment(patients, doctors, connection, scanner);
                           System.out.println();
                           break;
                       case 5:
                           System.out.println("Thanks for using my services.. ");
                           return;
                       default:
                           System.out.println("invalid choice ");


                   }

               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
    }
    public static void bookappointment( patients patients, doctors doctors, Connection connection, Scanner scanner){
        System.out.print("Enter Patients Id: ");
        int patientId= scanner.nextInt();
        System.out.print("Enter Doctor Id: ");
        int doctorId= scanner.nextInt();
        System.out.print("Enter appointment Date (YYYY-MM-DD) : ");
        String appointmentDate= scanner.next();
        if(patients.getPatientbyid(patientId) && doctors.getDoctorbyId(doctorId)){
            if(checkDoctorAvailabllity(doctorId,appointmentDate, connection)){
               String appointmentQuery= "INSERT INTO appointments(patient_id, doctor_id, appointment_date ) Values (?,?,?)";
               try{
                   PreparedStatement preparedStatement= connection.prepareStatement(appointmentQuery);
                   preparedStatement.setInt(1,patientId);
                   preparedStatement.setInt(2, doctorId);
                   preparedStatement.setString(3, appointmentDate);
                   int rowsAffected= preparedStatement.executeUpdate();
                   if(rowsAffected>0){
                       System.out.println("Appointment Booked..");
                   } else{
                       System.out.println("Failed to book appointment..!!");
                   }
               } catch (SQLException e) {
                   e.printStackTrace();
               }
            } else{
                System.out.println("Doctor is not Available on this  Day ");
            }
        } else{
            System.out.println("Either Doctor or Patients of this id does not Exist..!!");
        }

    }
    public static boolean checkDoctorAvailabllity(int doctorId, String appointmentDate, Connection connection){
        try{
            String query="SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date= ?";
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setInt(1,doctorId);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet= preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
               return count==0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
