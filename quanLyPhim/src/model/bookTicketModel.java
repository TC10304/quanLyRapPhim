package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class bookTicketModel {
	public int tongTien;
	public int gheThuong;
	public int gheVip;
	public int giaVeThuong = 60000;
	public int giaVeVip = 80000;
//	public List<String> seat_Name;
//	public ArrayList<Integer> seatID;

	public bookTicketModel() {
//		this.seat_Name = new ArrayList<>();
	}

	public bookTicketModel(int tongTien, int gheThuong, int gheVip, int giaVeThuong, int giaVeVip) {
		this.tongTien = tongTien;
		this.gheThuong = gheThuong;
		this.gheVip = gheVip;
		this.giaVeThuong = giaVeThuong;
		this.giaVeVip = giaVeVip;
//		if (seat_Name != null) {
//	        this.seat_Name = seat_Name;
//	    } else {
//	        this.seat_Name = new ArrayList<>();
//	    }
	}

	public int getTongTien() {
		return tongTien;
	}

	public void setTongTien(int tongTien) {
		this.tongTien = tongTien;
	}

	public int getGheThuong() {
		return gheThuong;
	}

	public void setGheThuong(int gheThuong) {
		this.gheThuong = gheThuong;
	}

	public int getGheVip() {
		return gheVip;
	}

	public void setGheVip(int gheVip) {
		this.gheVip = gheVip;
	}

	public int getGiaVeThuong() {
		return giaVeThuong;
	}

	public void setGiaVeThuong(int giaVeThuong) {
		this.giaVeThuong = giaVeThuong;
	}

	public int getGiaVeVip() {
		return giaVeVip;
	}

	public void setGiaVeVip(int giaVeVip) {
		this.giaVeVip = giaVeVip;
	}
	
//	public void addSeat_Name(String seatName) {
//		seat_Name.add(seatName);
//	}

	// lấy hall_ID
	public static int getHallIdByName(String hallName) {
		int hallId = -1;
		String selectHallIdSql = "SELECT hall_ID FROM cinema_Hall WHERE hall_name = ?";

		try (Connection conn = connect.connectDatabase.getConnection();
				PreparedStatement hallStmt = conn.prepareStatement(selectHallIdSql)) {

			hallStmt.setString(1, hallName);
			ResultSet hallKeys = hallStmt.executeQuery();

			if (hallKeys.next()) {
				hallId = hallKeys.getInt("hall_ID");
			} else {
				System.out.println("Không tìm thấy hall_ID cho tên phòng: " + hallName);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return hallId;
	}

	// lấy movie_ID
	public static int getMovieIdByName(String movieName) {
		String query = "SELECT movie_ID FROM movie WHERE movie_name = ?";
		try (Connection conn = connect.connectDatabase.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, movieName);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("movie_ID");
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}

	// lấy showtime_ID
	public static int getShowTimeIdByMovieID(int movieID) {
		String query = "SELECT showtime_ID FROM showtime WHERE movie_ID = ?";
		try (Connection conn = connect.connectDatabase.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, movieID);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("showtime_ID");
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}

	// lấy hàng ghế
	public static List<String> getSeatRow(String hallName) {
		List<String> seatRow = new ArrayList<>();

		int hallId = getHallIdByName(hallName);
		if (hallId == -1) {
			System.out.println("Không tìm thấy hall_ID cho tên phòng: " + hallName);
		}

		try (Connection conn = connect.connectDatabase.getConnection()) {
			String sql = "select DISTINCT seat_row from seat where hall_ID = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, hallId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				seatRow.add(rs.getString("seat_row"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		return seatRow;
	}

	// lấy số ghế
	public static List<Integer> getSeatNumber(String hallName) {
		List<Integer> seatNumbers = new ArrayList<>();

		int hallId = getHallIdByName(hallName);
		if (hallId == -1) {
			System.out.println("Không tìm thấy hall_ID cho tên phòng: " + hallName);
		}

		try (Connection conn = connect.connectDatabase.getConnection()) {
			String sql = "select DISTINCT seat_number from seat where hall_ID = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, hallId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				seatNumbers.add(rs.getInt("seat_number"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		return seatNumbers;
	}

	// điền ghế cho phim khi thêm vào
	public static void addSeatsForMovie(int hallID, int movieID) {
		String[] rows = { "A", "B", "C", "D", "E", "F", "G", "H" };
		int numSeats = 15;

		try (Connection conn = connect.connectDatabase.getConnection()) {
			String insertSeatSql = "INSERT INTO seat (hall_ID, movie_ID, seat_row, seat_number, seat_available) VALUES (?, ?, ?, ?, ?)";

			try (PreparedStatement pstmt = conn.prepareStatement(insertSeatSql)) {

				for (String row : rows) {
					for (int seatNumber = 1; seatNumber <= numSeats; seatNumber++) {
						pstmt.setInt(1, hallID);
						pstmt.setInt(2, movieID);
						pstmt.setString(3, row);
						pstmt.setInt(4, seatNumber);
						pstmt.setBoolean(5, true);

						// batch để thực hiện nhiều câu lệnh 1 lúc
						pstmt.addBatch();
					}
				}

				// Thực thi batch chèn dữ liệu vào bảng seat
				pstmt.executeBatch();
				System.out.println(
						"Đã thêm ghế vào bảng seat cho bộ phim với movie_ID: " + movieID + " và hall_ID: " + hallID);

			} catch (SQLException e) {
				System.out.println("Lỗi khi thêm ghế vào bảng seat: " + e.getMessage());
			}

		} catch (SQLException e) {
			System.out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	// lấy trạng thái ghế
	public static boolean getSeatAvailability(String row, int seatNumber, String hallName, String movieName) {
		String sql = "SELECT seat_available FROM seat WHERE hall_ID = ? AND seat_row = ? AND seat_number = ? AND movie_ID = ?";

		// Lấy hall_ID từ tên phòng
		int hallId = getHallIdByName(hallName);
		if (hallId == -1) {
			System.out.println("Không tìm thấy hall_ID cho tên phòng: " + hallName);
			return false;
		}

		// lấy movie ID
		int movieID = getMovieIdByName(movieName);
		if (movieID == -1) {
			System.out.println("Không tìm thấy movie_ID cho tên phim: " + movieName);
			return false;
		}

		try (Connection conn = connect.connectDatabase.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, hallId);
			pstmt.setString(2, row);
			pstmt.setInt(3, seatNumber);
			pstmt.setInt(4, movieID);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getBoolean("seat_available");
				} else {
					return false;
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.err.println("Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
			e.printStackTrace();
			return false; // Đảm bảo trả về false khi có lỗi
		}
	}

	// set trạng thái ghế được đặt
	public static boolean updateComplete(String row, int seatNumber, String hallName, String movieName) {
		String sql = "UPDATE seat SET seat_available = ? WHERE hall_ID = ? AND seat_row = ? AND seat_number = ? AND movie_ID = ?";

		// Lấy hall_ID từ tên phòng
		int hallId = getHallIdByName(hallName);
		if (hallId == -1) {
			System.out.println("Không tìm thấy hall_ID cho tên phòng: " + hallName);
			return false;
		}

		// lấy movie ID
		int movieID = getMovieIdByName(movieName);
		if (movieID == -1) {
			System.out.println("Không tìm thấy movie_ID cho tên phim: " + movieName);
			return false;
		}

		try (Connection conn = connect.connectDatabase.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setBoolean(1, false); // đã đặt : false
			pstmt.setInt(2, hallId);
			pstmt.setString(3, row);
			pstmt.setInt(4, seatNumber);
			pstmt.setInt(5, movieID);

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0; // Trả về true nếu ít nhất 1 dòng bị ảnh hưởng

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			return false;
		}
	}

	// set trạng thái ghế bị hủy
	public static boolean updateCancle(String row, int seatNumber, String hallName, String movieName) {
		String sql = "UPDATE seat SET seat_available = ? WHERE hall_ID = ? AND seat_row = ? AND seat_number = ? AND movie_ID = ?";

		// Lấy hall_ID từ tên phòng
		int hallId = getHallIdByName(hallName);
		if (hallId == -1) {
			System.out.println("Không tìm thấy hall_ID cho tên phòng: " + hallName);
			return false;
		}

		// lấy movie ID
		int movieID = getMovieIdByName(movieName);
		if (movieID == -1) {
			System.out.println("Không tìm thấy movie_ID cho tên phim: " + movieName);
			return false;
		}

		try (Connection conn = connect.connectDatabase.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setBoolean(1, true); // đã hủy : true
			pstmt.setInt(2, hallId);
			pstmt.setString(3, row);
			pstmt.setInt(4, seatNumber);
			pstmt.setInt(5, movieID);

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0; // Trả về true nếu ít nhất 1 dòng bị ảnh hưởng

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			return false;
		}
	}

	// lấy seatID
	public static int getSeatID(String seatRow, int seatNumber) {
		int seatID = -1;

		String sql = "select seat_ID from seat where seat_row = ? AND seat_number = ?";

		try (Connection conn = connect.connectDatabase.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, seatRow);
			pstmt.setInt(2, seatNumber);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					seatID = rs.getInt("seat_ID");
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return seatID;
	}

	// thêm ticket
	public static boolean addTicket(int ticketPrice, String movieName) {
		String insertTicket = "insert into ticket (showtime_ID, price, ticket_paid) values (?,?,?);";
		int movieID = getMovieIdByName(movieName);
		if (movieID == -1) {
			System.out.println("Không tìm thấy movieID cho movieName " + movieName);
			return false;
		}

		int showTimeID = getShowTimeIdByMovieID(movieID);
		if (showTimeID == -1) {
			System.out.println("Không tìm thấy showTime_ID cho movieID " + movieID);
			return false;
		}

		try (Connection conn = connect.connectDatabase.getConnection()) {
			try (PreparedStatement stmt = conn.prepareStatement(insertTicket)) {
				stmt.setInt(1, showTimeID);
				stmt.setInt(2, ticketPrice);
				stmt.setBoolean(3, false);
				int rowsInserted = stmt.executeUpdate();
		        if (rowsInserted > 0) {
		            System.out.println("Thêm thành công.");
		            return true;
		        }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return false;
	}

	// cập nhật giá khi có chỉnh sửa ghế ngồi
	public static boolean updateTicket(int ticketPrice, String movieName) {
		String updateTicket = "UPDATE ticket SET price = ? WHERE showtime_ID = ?;";

		int movieID = getMovieIdByName(movieName);
		if (movieID == -1) {
			System.out.println("Không tìm thấy movieID cho movieName " + movieName);
			return false;
		}

		int showTimeID = getShowTimeIdByMovieID(movieID);
		if (showTimeID == -1) {
			System.out.println("Không tìm thấy showTime_ID cho movieID " + movieID);
			return false;
		}

		try (Connection conn = connect.connectDatabase.getConnection()) {
			try (PreparedStatement stmt = conn.prepareStatement(updateTicket)) {
				stmt.setInt(1, ticketPrice);
				stmt.setInt(2, showTimeID);

				int rowsUpdated = stmt.executeUpdate();
				if (rowsUpdated > 0) {
					System.out.println("Cập nhật vé thành công.");
					return true;
				} else {
					System.out.println("Không tìm thấy vé nào để cập nhật.");
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return false;
	}

	// lấy ticket_ID mới nhất
	public static int getLatesTicketID() {
		int ticketID = -1;
		String sql = "SELECT TOP 1 ticket_ID FROM ticket ORDER BY ticket_ID DESC";

		try (Connection conn = connect.connectDatabase.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			if (rs.next()) {
				ticketID = rs.getInt("ticket_ID");
			}

		} catch (SQLException e) {
			System.out.println("Lỗi khi lấy ticketID mới nhất: " + e.getMessage());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		return ticketID;
	}

	// điền bảng ticket_seat
	public static boolean addTicket_Seat(String seatRow, int seatNumber) {
		String sql = "insert into ticket_seat (ticket_ID, seat_ID) values (?,?);";

		int seatID = getSeatID(seatRow, seatNumber);
		if (seatID == -1) {
			System.out.println("Không tìm thấy ID ghế cho hàng " + seatRow + " và số " + seatNumber);
			return false;
		}
		
		int ticketID = getLatesTicketID();
		if(ticketID == -1) {
			System.out.println("Không có hóa đơn");
			return false;
		}

		try (Connection conn = connect.connectDatabase.getConnection()) {
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, ticketID);
				stmt.setInt(2, seatID);
				int rowsInserted = stmt.executeUpdate();
		        if (rowsInserted > 0) {
		            System.out.println("Đã thêm ticket_ID " + ticketID + " với seat_ID " + seatID);
		            return true;
		        }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return false;
	}

	// xóa ticket_seat
	public static boolean deleteTicketSeat(int seat_ID) {
		String deleteTicket = "DELETE FROM ticket_seat WHERE seat_ID = ?";

		try (Connection conn = connect.connectDatabase.getConnection();
				PreparedStatement stmt = conn.prepareStatement(deleteTicket)) {

			stmt.setInt(1, seat_ID);

			int rowsDeleted = stmt.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println("Vé với seat_ID " + seat_ID + " đã được hủy thành công.");
				return true;
			} else {
				System.out.println("Không tìm thấy vé với seat_ID " + seat_ID + " để hủy.");
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
}
