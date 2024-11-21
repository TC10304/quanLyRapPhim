package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.adminModel;
import model.bookTicketModel;
import view.functionAdminView;
import view.homeAdminView;
import view.timKiemPhim;

public class adminControllerPhim implements ActionListener {
	private functionAdminView functionAdminView;
	private timKiemPhim timKiemPhim;
	private homeAdminView homeAdminView;

	public adminControllerPhim(view.functionAdminView functionAdminView) {
		this.functionAdminView = functionAdminView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		if (str.equals("Thêm")) {
			if (functionAdminView.text_tenPhim.getText().isEmpty() || functionAdminView.text_TheLoai.getText().isEmpty()
					|| functionAdminView.text_ThoiLuong.getText().isEmpty()
					|| functionAdminView.comboBox.getSelectedItem() == null
					|| functionAdminView.text_GioChieu.getText().isEmpty()) {

				JOptionPane.showMessageDialog(functionAdminView, "Vui lòng điền đầy đủ thông tin phim muốn thêm");
			} else {
				boolean addPhim = adminModel.addThongTinPhim(functionAdminView.text_tenPhim.getText(),
						functionAdminView.text_TheLoai.getText(), functionAdminView.text_ThoiLuong.getText(),
						functionAdminView.comboBox.getSelectedItem().toString(),
						functionAdminView.text_GioChieu.getText());
				
				// Sau khi thêm phim thành công, tự động thêm ghế vào bảng seat
		        int movieID = adminModel.getLatestMovieID(); 
		        int hallID = adminModel.getHallIdByName(functionAdminView.comboBox.getSelectedItem().toString());
		        bookTicketModel.addSeatsForMovie(hallID, movieID); 
				
				
				if (addPhim) {
					// Lấy lại dữ liệu và cập nhật vào model của bảng
					List<String[]> updatedData = adminModel.thongTinPhim(); // Lấy dữ liệu mới nhất
					((DefaultTableModel) functionAdminView.tableModelPhim).setRowCount(0); // Xóa tất cả dữ liệu cũ

					for (String[] row : updatedData) {
						((DefaultTableModel) functionAdminView.tableModelPhim).addRow(row); // Thêm dữ liệu mới vào
					}

					functionAdminView.tablePhim.revalidate();
					functionAdminView.tablePhim.repaint();

					functionAdminView.text_tenPhim.setText("");
					functionAdminView.text_TheLoai.setText("");
					functionAdminView.text_ThoiLuong.setText("");
					functionAdminView.comboBox.setSelectedItem(null);
					functionAdminView.text_GioChieu.setText("");
					
					

					JOptionPane.showMessageDialog(functionAdminView, "Thêm phim thành công");

				} else {
					JOptionPane.showMessageDialog(functionAdminView, "Đã có lỗi xảy ra khi thêm phim");
				}
			}

		} else if (str.equals("Xóa")) {
			int selectedRow = functionAdminView.tablePhim.getSelectedRow();

			if (selectedRow != -1) {
				// Lấy tên phim từ hàng được chọn
				String movieName = functionAdminView.tableModelPhim.getValueAt(selectedRow, 0).toString();

				int confirm = JOptionPane.showConfirmDialog(functionAdminView, "Bạn có chắc chắn muốn xóa phim này?",
						"Xác nhận", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					// Xóa phim trong cơ sở dữ liệu
					boolean deleteSuccess = adminModel.deleteThongTinPhim(movieName);

					if (deleteSuccess) {
						// Xóa hàng khỏi JTable
						functionAdminView.tableModelPhim.removeRow(selectedRow);
						JOptionPane.showMessageDialog(functionAdminView, "Xóa phim thành công");
					} else {
						JOptionPane.showMessageDialog(functionAdminView,
								"Đã có lỗi xảy ra khi xóa phim khỏi cơ sở dữ liệu");

					}
				}
			} else {
				JOptionPane.showMessageDialog(functionAdminView, "Chưa chọn phim để xóa.");
			}
		} else if (str.equals("Tìm Kiếm")) {
			if (functionAdminView.text_tenPhim.getText().isEmpty() && functionAdminView.text_TheLoai.getText().isEmpty()
					&& functionAdminView.text_ThoiLuong.getText().isEmpty()
					&& functionAdminView.comboBox.getSelectedItem() == null
					&& functionAdminView.text_GioChieu.getText().isEmpty()) {

				JOptionPane.showMessageDialog(functionAdminView, "Vui lòng điền thông tin muốn tìm kiếm");
			} else {

				this.timKiemPhim = new timKiemPhim();

				int rowCount = timKiemPhim.tableModel.getRowCount();

				// theo tên phim
				List<String> movieName = new ArrayList<String>();
				for (int i = 0; i < rowCount; i++) {
					movieName.add(functionAdminView.tableModelPhim.getValueAt(i, 0).toString().toLowerCase());
				}
				if (movieName.contains(functionAdminView.text_tenPhim.getText().toLowerCase())) {
					List<String[]> updatedData = adminModel
							.timKiemTheoTenPhim(functionAdminView.text_tenPhim.getText());
					
					hienBang(updatedData);
					
					functionAdminView.text_tenPhim.setText("");

				}

				// tìm kiếm theo thể loại
				List<String> movieGenre = new ArrayList<String>();
				for (int i = 0; i < rowCount; i++) {
					movieGenre.add(functionAdminView.tableModelPhim.getValueAt(i, 1).toString().toLowerCase());
				}
				if (movieGenre.contains(functionAdminView.text_TheLoai.getText().toLowerCase())) {
					List<String[]> updatedData = adminModel
							.timKiemTheoTheLoai(functionAdminView.text_TheLoai.getText());
					
					hienBang(updatedData);

					functionAdminView.text_TheLoai.setText("");
				}

				// tìm kiếm theo phòng
				List<String> phong = new ArrayList<String>();

				for (int i = 0; i < rowCount; i++) {
					Object cellValue = functionAdminView.tableModelPhim.getValueAt(i, 2);

					if (cellValue != null) {
						phong.add(cellValue.toString().toLowerCase());
					}
				}

				if (functionAdminView.comboBox.getSelectedItem() != null
						&& phong.contains(functionAdminView.comboBox.getSelectedItem().toString().toLowerCase())) {
					List<String[]> updatedData = adminModel
							.timKiemTheoPhong(functionAdminView.comboBox.getSelectedItem().toString().toLowerCase());
					
					hienBang(updatedData);

					functionAdminView.comboBox.setSelectedItem(null);
				}

				// tìm kiếm theo thời lượng
				List<String> thoiLuong = new ArrayList<String>();

				for (int i = 0; i < rowCount; i++) {
					thoiLuong.add(functionAdminView.tableModelPhim.getValueAt(i, 3).toString().toLowerCase());
				}

				if (thoiLuong.contains(functionAdminView.text_ThoiLuong.getText().toLowerCase())) {
					List<String[]> updatedData = adminModel
							.timKiemThoiLuong(functionAdminView.text_ThoiLuong.getText());
					
					hienBang(updatedData);

					functionAdminView.text_ThoiLuong.setText("");
				}

				// tìm theo giờ chiếu
				List<String> gioChieu = new ArrayList<>();

				for (int i = 0; i < rowCount; i++) {
					gioChieu.add(functionAdminView.tableModelPhim.getValueAt(i, 4).toString());
				}

				if (!functionAdminView.text_GioChieu.getText().isEmpty()
						&& gioChieu.contains(functionAdminView.text_GioChieu.getText())) {
					// Chuyển đổi chuỗi đầu vào thành Timestamp
					Timestamp inputGioChieu = Timestamp.valueOf(functionAdminView.text_GioChieu.getText());

					List<String[]> updatedData = adminModel.timKiemGioChieu(inputGioChieu);
					
					hienBang(updatedData);

					functionAdminView.text_GioChieu.setText("");

				}
			}
		} else if (str.equals("Thoát")) {
			functionAdminView.dispose();
			if (this.homeAdminView == null) {
	            this.homeAdminView = new homeAdminView(); // Khởi tạo đối tượng nếu chưa được khởi tạo
	        }
	        this.homeAdminView.setVisible(true);
		}
	}
	
	public void hienBang(List<String[]> updatedData) {
		((DefaultTableModel) timKiemPhim.tableModel).setRowCount(0);

		for (String[] row : updatedData) {
			((DefaultTableModel) timKiemPhim.tableModel).addRow(row);
		}

		timKiemPhim.table.revalidate();
		timKiemPhim.table.repaint();

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				timKiemPhim.setVisible(true);
			}
		});
		thread.start();
	}
}
