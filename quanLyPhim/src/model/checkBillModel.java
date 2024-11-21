package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class checkBillModel {

	public String getTicketID() {
		return String.valueOf(model.bookTicketModel.getLatesTicketID());
	}

	public String getPrice() {
		String price = "";
		String sql = "SELECT TOP 1 price FROM ticket ORDER BY price DESC";

		try (Connection conn = connect.connectDatabase.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			if (rs.next()) {
				price = String.valueOf(rs.getInt("price"));
			}

		} catch (SQLException e) {
			System.out.println("Lỗi khi lấy price mới nhất: " + e.getMessage());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		return price;
	}
	
}
