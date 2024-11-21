//package connect;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.sql.ResultSet;
//
//public class connectDatabase {
//		    public static void main(String[] args) {
//		        // Chuỗi kết nối SQL Server
//		        // Kết nối SQL Server
//		        try  {
//		        	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			        String url = "jdbc:sqlserver://localhost:1433;databaseName=quanLyPhim;encrypt=true;trustServerCertificate=true";
//
//			    	String username = "sa";
//			    	String password = "123";		        
//			        Connection conn = DriverManager.getConnection(url, username, password);
//		        
//		        } catch (ClassNotFoundException | SQLException e) {
//		            e.printStackTrace();
//		        }
//		    }
//
//}

package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class connectDatabase {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=quanLyPhim;encrypt=true;trustServerCertificate=true";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123";

    //  kết nối
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // kiểm tra đăng nhập admin
    public static boolean AD(String taiKhoan, String matKhau) {
        String sqlQuery = "SELECT * FROM ad WHERE taiKhoan = '" + taiKhoan + "' AND matKhau = '" + matKhau + "'";
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            return resultSet.next(); 
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // kiểm tra đăng nhập user
    public static boolean User(String taiKhoan, String matKhau) {
        String sqlQuery = "SELECT * FROM nguoiDung WHERE taiKhoan = '" + taiKhoan + "' AND matKhau = '" + matKhau + "'";
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            return resultSet.next(); 
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // thêm người dùng 
    public static boolean addUser(String taiKhoan, String matKhau) {
        String sqlQuery = "INSERT INTO nguoiDung (taiKhoan, matKhau) VALUES (?, ?)"; // Sử dụng dấu ? để tránh SQL Injection
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery)) {

            // Thiết lập các tham số cho PreparedStatement
            preparedStatement.setString(1, taiKhoan);
            preparedStatement.setString(2, matKhau);

            // Thực thi câu lệnh INSERT
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false; 
        }
    }
    
    
    

}