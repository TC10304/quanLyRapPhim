package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class adminModel {
	private String tenPhim;
	private String theLoai;
	private String phongChieu;
	private int thoiLuong;
	private Date ngayChieu;
	private LocalTime gioChieu;
	private String maNV;
	private String tenNV;
	public adminModel(String tenPhim, String theLoai, String phongChieu, int thoiLuong, Date ngayChieu,
			LocalTime gioChieu, String maNV, String tenNV) {
		this.tenPhim = tenPhim;
		this.theLoai = theLoai;
		this.phongChieu = phongChieu;
		this.thoiLuong = thoiLuong;
		this.ngayChieu = ngayChieu;
		this.gioChieu = gioChieu;
		this.maNV = maNV;
		this.tenNV = tenNV;
	}

	public adminModel() {
	}

	public String getTenPhim() {
		return tenPhim;
	}

	public void setTenPhim(String tenPhim) {
		this.tenPhim = tenPhim;
	}

	public String getTheLoai() {
		return theLoai;
	}

	public void setTheLoai(String theLoai) {
		this.theLoai = theLoai;
	}

	public String getPhongChieu() {
		return phongChieu;
	}

	public void setPhongChieu(String phongChieu) {
		this.phongChieu = phongChieu;
	}

	public int getThoiLuong() {
		return thoiLuong;
	}

	public void setThoiLuong(int thoiLuong) {
		this.thoiLuong = thoiLuong;
	}

	public Date getNgayChieu() {
		return ngayChieu;
	}

	public void setNgayChieu(Date ngayChieu) {
		this.ngayChieu = ngayChieu;
	}

	public LocalTime getGioChieu() {
		return gioChieu;
	}

	public void setGioChieu(LocalTime gioChieu) {
		this.gioChieu = gioChieu;
	}

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getTenNV() {
		return tenNV;
	}

	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}

	// phim
	public static List<String[]> thongTinPhim() {
		List<String[]> dataList = new ArrayList<>();
		try (Connection conn = connect.connectDatabase.getConnection()) {
			String sql = "SELECT movie.movie_name, \r\n"
					+ "       movie.movie_genre, \r\n"
					+ "       cinema_Hall.hall_name, \r\n"
					+ "       movie.movie_duration, \r\n"
					+ "       showtime.start_time \r\n"
					+ "FROM movie\r\n"
					+ "JOIN showtime ON movie.movie_ID = showtime.movie_ID\r\n"
					+ "JOIN cinema_Hall ON cinema_Hall.hall_ID = showtime.hall_ID\r\n"
					+ "ORDER BY showtime.start_time ASC;";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				String[] row = { rs.getString("movie_name"), rs.getString("movie_genre"), rs.getString("hall_name"),
						rs.getString("movie_duration"), rs.getString("start_time")

				};
				dataList.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		return dataList;
	}

	// lấy hall_ID từ hall_Name
	public static int getHallIdByName(String hallName) {
		int hallId = -1;
		String selectHallIdSql = "SELECT hall_ID FROM cinema_Hall WHERE hall_name = ?";

		try (Connection conn = connect.connectDatabase.getConnection();
				PreparedStatement hallStmt = conn.prepareStatement(selectHallIdSql)) {

			// Gán giá trị cho tham số trong truy vấn
			hallStmt.setString(1, hallName);

			// Thực hiện truy vấn
			ResultSet hallKeys = hallStmt.executeQuery();

			// Kiểm tra xem có kết quả không
			if (hallKeys.next()) {
				hallId = hallKeys.getInt("hall_ID"); // Lấy giá trị hall_ID từ kết quả
			} else {
				System.out.println("Không tìm thấy hall_ID cho tên phòng: " + hallName);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return hallId;
	}

	// thêm thông tin phim
	public static boolean addThongTinPhim(String movieName, String movieGenre, String movieDuration, String hallName,
			String startTime) {
		int hallId = getHallIdByName(hallName);
		if (hallId == -1) {
			System.out.println("Không tìm thấy hall_ID cho tên phòng: " + hallName);
			return false;
		}

		String insertMovieSql = "INSERT INTO movie (movie_name, movie_genre, movie_duration) VALUES (?, ?, ?)";
		String insertShowtimeSql = "INSERT INTO showtime (start_time, movie_ID, hall_ID) VALUES (?, ?, ?)";

		try (Connection conn = connect.connectDatabase.getConnection()) {
			conn.setAutoCommit(false); // Bắt đầu transaction

			try (PreparedStatement movieStmt = conn.prepareStatement(insertMovieSql, Statement.RETURN_GENERATED_KEYS);
					PreparedStatement showtimeStmt = conn.prepareStatement(insertShowtimeSql)) {

				// Thêm dữ liệu vào bảng movie
				movieStmt.setString(1, movieName);
				movieStmt.setString(2, movieGenre);
				movieStmt.setString(3, movieDuration);
				movieStmt.executeUpdate();

				// Lấy movie_ID sinh tự động
				ResultSet movieKeys = movieStmt.getGeneratedKeys();
				int movieId = 0;
				if (movieKeys.next()) {
					movieId = movieKeys.getInt(1);
				}

				// Thêm dữ liệu vào bảng showtime với movie_ID và hall_ID
				showtimeStmt.setString(1, startTime);
				showtimeStmt.setInt(2, movieId);
				showtimeStmt.setInt(3, hallId);
				showtimeStmt.executeUpdate();

				conn.commit(); // Commit transaction
				return true;
			} catch (SQLException e) {
				conn.rollback(); // Rollback nếu có lỗi xảy ra
				e.printStackTrace();
				return false;
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	// lấy movie_ID từ movie_name
	private static int getMovieIdByName(String movieName) {
		String query = "SELECT movie_ID FROM movie WHERE movie_name = ?";
		try (Connection conn = connect.connectDatabase.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
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

	// xóa phim
	public static boolean deleteThongTinPhim(String movieName) {
		int movieId = getMovieIdByName(movieName);
		if (movieId == -1) {
			System.out.println("Không tìm thấy movie_ID cho tên phim: " + movieName);
			return false;
		}

		// Bắt đầu transaction
		String deleteShowtimeSql = "DELETE FROM showtime WHERE movie_ID = ?";
		String deleteMovieSql = "DELETE FROM movie WHERE movie_ID = ?";

		try (Connection conn = connect.connectDatabase.getConnection()) {
			conn.setAutoCommit(false);

			try (PreparedStatement showtimeStmt = conn.prepareStatement(deleteShowtimeSql);
					PreparedStatement movieStmt = conn.prepareStatement(deleteMovieSql)) {

				// Xóa các suất chiếu liên kết với movie_ID
				showtimeStmt.setInt(1, movieId);
				showtimeStmt.executeUpdate();

				// Xóa phim trong bảng movie bằng movie_ID
				movieStmt.setInt(1, movieId); // Sử dụng movie_ID để xóa
				movieStmt.executeUpdate();

				conn.commit();
				return true;
			} catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
				return false;
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Tìm kiếm theo tên phim
	public static List<String[]> timKiemTheoTenPhim(String tenPhim) {
		List<String[]> dataList = new ArrayList<>();
		try (Connection conn = connect.connectDatabase.getConnection()) {
			String sql = "SELECT movie.movie_name, \r\n"
			           + "       movie.movie_genre, \r\n"
			           + "       cinema_Hall.hall_name, \r\n"
			           + "       movie.movie_duration, \r\n"
			           + "       showtime.start_time \r\n"
			           + "FROM movie\r\n"
			           + "JOIN showtime ON movie.movie_ID = showtime.movie_ID\r\n"
			           + "JOIN cinema_Hall ON cinema_Hall.hall_ID = showtime.hall_ID \r\n"
			           + "WHERE movie.movie_name = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, tenPhim); // Truyền tên phim vào câu truy vấn

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				String[] row = { rs.getString("movie_name"), rs.getString("movie_genre"), rs.getString("hall_name"),
						rs.getString("movie_duration"), rs.getString("start_time") };
				dataList.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return dataList;
	}

	// Tìm kiếm theo thể loại
	public static List<String[]> timKiemTheoTheLoai(String theLoai) {
		List<String[]> dataList = new ArrayList<>();
		try (Connection conn = connect.connectDatabase.getConnection()) {
			String sql = "SELECT movie.movie_name, \r\n"
			           + "       movie.movie_genre, \r\n"
			           + "       cinema_Hall.hall_name, \r\n"
			           + "       movie.movie_duration, \r\n"
			           + "       showtime.start_time \r\n"
			           + "FROM movie\r\n"
			           + "JOIN showtime ON movie.movie_ID = showtime.movie_ID\r\n"
			           + "JOIN cinema_Hall ON cinema_Hall.hall_ID = showtime.hall_ID \r\n"
			           + "WHERE movie.movie_genre = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, theLoai);

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				String[] row = { rs.getString("movie_name"), rs.getString("movie_genre"), rs.getString("hall_name"),
						rs.getString("movie_duration"), rs.getString("start_time") };
				dataList.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return dataList;
	}

	// tìm kiếm theo phòng
	public static List<String[]> timKiemTheoPhong(String Phong) {
		List<String[]> dataList = new ArrayList<>();
		try (Connection conn = connect.connectDatabase.getConnection()) {
			String sql = "SELECT movie.movie_name, \r\n"
			           + "       movie.movie_genre, \r\n"
			           + "       cinema_Hall.hall_name, \r\n"
			           + "       movie.movie_duration, \r\n"
			           + "       showtime.start_time \r\n"
			           + "FROM movie\r\n"
			           + "JOIN showtime ON movie.movie_ID = showtime.movie_ID\r\n"
			           + "JOIN cinema_Hall ON cinema_Hall.hall_ID = showtime.hall_ID \r\n"
			           + "WHERE cinema_Hall.hall_name = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, Phong);

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				String[] row = { rs.getString("movie_name"), rs.getString("movie_genre"), rs.getString("hall_name"),
						rs.getString("movie_duration"), rs.getString("start_time") };
				dataList.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return dataList;
	}

	// tìm kiếm theo thời lượng
	public static List<String[]> timKiemThoiLuong(String thoiLuong) {
		List<String[]> dataList = new ArrayList<>();
		try (Connection conn = connect.connectDatabase.getConnection()) {
			String sql = "SELECT movie.movie_name, \r\n"
			           + "       movie.movie_genre, \r\n"
			           + "       cinema_Hall.hall_name, \r\n"
			           + "       movie.movie_duration, \r\n"
			           + "       showtime.start_time \r\n"
			           + "FROM movie\r\n"
			           + "JOIN showtime ON movie.movie_ID = showtime.movie_ID\r\n"
			           + "JOIN cinema_Hall ON cinema_Hall.hall_ID = showtime.hall_ID \r\n"
			           + "WHERE movie.movie_duration = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, thoiLuong);

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				String[] row = { rs.getString("movie_name"), rs.getString("movie_genre"), rs.getString("hall_name"),
						rs.getString("movie_duration"), rs.getString("start_time") };
				dataList.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return dataList;
	}

	// tìm kiếm theo giờ chiếu
	public static List<String[]> timKiemGioChieu(java.sql.Timestamp thoiGian) {
		List<String[]> dataList = new ArrayList<>();
		try (Connection conn = connect.connectDatabase.getConnection()) {
			String sql = "SELECT movie.movie_name, \r\n"
			           + "       movie.movie_genre, \r\n"
			           + "       cinema_Hall.hall_name, \r\n"
			           + "       movie.movie_duration, \r\n"
			           + "       showtime.start_time \r\n"
			           + "FROM movie\r\n"
			           + "JOIN showtime ON movie.movie_ID = showtime.movie_ID\r\n"
			           + "JOIN cinema_Hall ON cinema_Hall.hall_ID = showtime.hall_ID \r\n"
			           + "WHERE showtime.start_time = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setTimestamp(1, thoiGian);

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				String[] row = { rs.getString("movie_name"), rs.getString("movie_genre"), rs.getString("hall_name"),
						rs.getString("movie_duration"), rs.getString("start_time") };
				dataList.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}
		return dataList;
	}

	// lấy mã nhân viên
	public static String[] layMaNhanVien() {
		List<String> staffIdList = new ArrayList<>();
		try (Connection conn = connect.connectDatabase.getConnection()) {
			String sql = "select staff.staff_ID from staff;";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				staffIdList.add(rs.getString("staff_ID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		return staffIdList.toArray(new String[0]);
	}

	// lấy tên nhân viên
	public static String layTenNhanVien(String staff_ID) {
		String staffName = null;
		try (Connection conn = connect.connectDatabase.getConnection()) {
			String sql = "select staff.staff_name from staff where staff_ID = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, staff_ID);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				staffName = rs.getString("staff_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		return staffName;
	}

	// lấy thông tin nhân viên
	public static List<String[]> thongTinCaLam() {
		List<String[]> dataList = new ArrayList<>();
		try (Connection conn = connect.connectDatabase.getConnection()) {
			String sql = "select staff.staff_ID, staff.staff_name, staffDate.staff_date, cinema_Hall.hall_name\r\n"
					+ "from staff, cinema_Hall, staffDate\r\n"
					+ "where staffDate.hall_ID = cinema_Hall.hall_ID and staff.staff_ID = staffDate.staff_ID "
					+ "order by staff_date ASC;";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				String[] row = { rs.getString("staff_ID"), rs.getString("staff_name"), rs.getString("staff_date"),
						rs.getString("hall_name") };
				dataList.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		return dataList;
	}

	// thêm ca làm
	public static boolean addCaLam(String maNV, String tenNV, String ngayLam, String phongNV) {
		int hallId = getHallIdByName(phongNV);
		if (hallId == -1) {
			System.out.println("Không tìm thấy hall_ID cho tên phòng: " + phongNV);
			return false;
		}
		String insertStaffDate = "INSERT INTO staffDate (staff_ID, staff_date, hall_ID) VALUES (?, ?, ?)";

		try (Connection conn = connect.connectDatabase.getConnection()) {
			conn.setAutoCommit(false);

			try (PreparedStatement staffDateStmt = conn.prepareStatement(insertStaffDate)) {

				staffDateStmt.setString(1, maNV);
				staffDateStmt.setString(2, ngayLam);
				staffDateStmt.setInt(3, hallId);
				staffDateStmt.executeUpdate();

				conn.commit();
				return true;
			} catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
				return false;
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// xóa ca làm
	public static boolean deleteCaLam(String maNV) {

	    String deleteStaffDate = "DELETE FROM staffDate WHERE  staff_ID = ?";

	    try (Connection conn = connect.connectDatabase.getConnection()) {
	        conn.setAutoCommit(false);

	        try (PreparedStatement staffDateStmt = conn.prepareStatement(deleteStaffDate)) {
	            staffDateStmt.setString(1, maNV);
	            int rowsAffected = staffDateStmt.executeUpdate();
	            
	            if (rowsAffected > 0) {
	                conn.commit();
//	                System.out.println("Đã xóa ca làm thành công cho ID: " + maNV);
	                return true;
	            } else {
//	                System.out.println("Không tìm thấy ca làm để xóa.");
	                conn.rollback();
	                return false;
	            }
	        } catch (SQLException e) {
	            conn.rollback();
	            e.printStackTrace();
	            return false;
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	// tìm kiếm theo mã nhân viên && tên nhân viên
	public static List<String[]> timKiemTheoMaNV(String manv) {
		List<String []> dataList = new ArrayList<String[]>();
		try (Connection conn = connect.connectDatabase.getConnection()) {
			String sql = "select staff.staff_ID, staff.staff_name, staffDate.staff_date, cinema_Hall.hall_name\r\n"
					+ "from staff, cinema_Hall, staffDate\r\n"
					+ "where staffDate.hall_ID = cinema_Hall.hall_ID and staff.staff_ID = staffDate.staff_ID and staff.staff_ID = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, manv);
			
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				String[] row = { rs.getString("staff_ID"), rs.getString("staff_name"), rs.getString("staff_date"),
						rs.getString("hall_name") };
				dataList.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		return dataList;
	}
	
	// tìm kiếm theo ngày làm nhân viên
	public static List<String[]> timKiemTheoNgayLam(java.sql.Date ngayLam) {
		List<String[]> dataList = new ArrayList<>();
		try (Connection conn = connect.connectDatabase.getConnection()) {
			String sql = "select staff.staff_ID, staff.staff_name, staffDate.staff_date, cinema_Hall.hall_name\r\n"
					+ "from staff, cinema_Hall, staffDate\r\n"
					+ "where staffDate.hall_ID = cinema_Hall.hall_ID and staff.staff_ID = staffDate.staff_ID and staffDate.staff_date = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setDate(1, ngayLam);

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				String[] row = { rs.getString("staff_ID"), rs.getString("staff_name"), rs.getString("staff_date"),
						rs.getString("hall_name") };
				dataList.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}
		return dataList;
	}
	
	// tìm kiếm theo phòng
		public static List<String[]> timKiemTheoPhongNV(String phongNV) {
			List<String []> dataList = new ArrayList<String[]>();
			try (Connection conn = connect.connectDatabase.getConnection()) {
				String sql = "select staff.staff_ID, staff.staff_name, staffDate.staff_date, cinema_Hall.hall_name\r\n"
						+ "from staff, cinema_Hall, staffDate\r\n"
						+ "where staffDate.hall_ID = cinema_Hall.hall_ID and staff.staff_ID = staffDate.staff_ID and cinema_Hall.hall_name = ?;";
				PreparedStatement statement = conn.prepareStatement(sql);
				
				statement.setString(1, phongNV);
				
				ResultSet rs = statement.executeQuery();

				while (rs.next()) {
					String[] row = { rs.getString("staff_ID"), rs.getString("staff_name"), rs.getString("staff_date"),
							rs.getString("hall_name") };
					dataList.add(row);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			return dataList;
		}
	
	// sửa ca làm
		public static boolean updateCaLam(String maNV, String tenNV, String ngayLam, String phongNV) {
		    int hallId = getHallIdByName(phongNV); 
		    if (hallId == -1) {
		        System.out.println("Không tìm thấy hall_ID cho tên phòng: " + phongNV);
		        return false;
		    }

		    // Câu lệnh SQL để cập nhật ca làm
		    String updateStaffDate = "UPDATE staffDate SET staff_ID = ?, staff_date = ?, hall_ID = ? WHERE staff_date = ?";
		    
//		    System.out.println("Câu lệnh SQL: " + updateStaffDate);
//		    System.out.println("Tham số: ");
//		    System.out.println("Mã NV: " + maNV);
//		    System.out.println("Tên NV: " + tenNV);
//		    System.out.println("Ngày làm: " + ngayLam);
//		    System.out.println("Tên phòng: " + phongNV);
//		    System.out.println("Hall ID: " + hallId);

		    try (Connection conn = connect.connectDatabase.getConnection()) {
		        conn.setAutoCommit(false);  // Bắt đầu giao dịch

		        try (PreparedStatement staffDateStmt = conn.prepareStatement(updateStaffDate)) {
		            staffDateStmt.setString(1, maNV);      // Cập nhật staff_ID mới
		            staffDateStmt.setString(2, ngayLam);   // Cập nhật ngày làm mới
		            staffDateStmt.setInt(3, hallId);       // Cập nhật hall_ID mới
		            staffDateStmt.setString(4, maNV);      // Điều kiện WHERE với staff_ID cũ

		            int rowsUpdated = staffDateStmt.executeUpdate(); 

		            if (rowsUpdated > 0) {
		                conn.commit();  // Commit nếu cập nhật thành công
		                return true;
		            } else {
		                conn.rollback();  // Rollback nếu không có bản ghi nào được cập nhật
		                return false;
		            }
		        } catch (SQLException e) {
		            conn.rollback();  // Rollback nếu có lỗi
		            e.printStackTrace();
		            return false;
		        }
		    } catch (SQLException | ClassNotFoundException e) {
		        e.printStackTrace();
		        return false;
		    }
		}
		
		// lấy Id phim vừa thêm
		public static int getLatestMovieID() {
		    int movieID = -1;
		    String sql = "SELECT TOP 1 movie_ID FROM movie ORDER BY movie_ID DESC"; // Lấy ID của bộ phim mới nhất

		    try (Connection conn = connect.connectDatabase.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(sql);
		         ResultSet rs = pstmt.executeQuery()) {

		        if (rs.next()) {
		            movieID = rs.getInt("movie_ID");
		        }

		    } catch (SQLException e) {
		        System.out.println("Lỗi khi lấy movie_ID mới nhất: " + e.getMessage());
		    } catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

		    return movieID;
		}


}
