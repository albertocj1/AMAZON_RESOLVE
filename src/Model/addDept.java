package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class addDept {
    public void addDepartmentToDB() {
       

        String sql = "INSERT INTO department (dept_Head, dept_Name, dept_Description) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbConnect.getConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "Rodney Lei Estrada");
            pstmt.setString(2, "Order Fulfillment Department");
            pstmt.setString(3, ("Handles processing incoming orders, picking and packing items, and ensuring those orders are shipped out promptly and accurately."));

            pstmt.executeUpdate();
            System.out.println("Department added successfully!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        String sql = "INSERT INTO department (dept_Head, dept_Name, dept_Description) VALUES (?, ?, ?)";

        try (Connection conn = DbConnect.getConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "Michaela Angela Cailing");
            pstmt.setString(2, "Returns Management Department");
            pstmt.setString(3, ("handles the return of unwanted or defective products, ensuring a smooth and efficient experience for customers."));

            pstmt.executeUpdate();
            System.out.println("Department added successfully!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    
}
