package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.adminModel;
import view.functionAdminView;
import view.homeAdminView;
import view.timKiemNhanVien;

public class adminControllerNV implements ActionListener{
	private functionAdminView functionAdminView;
	private timKiemNhanVien timKiemNhanVien;
	private homeAdminView homeAdminView;

	public adminControllerNV(view.functionAdminView functionAdminView) {
		this.functionAdminView = functionAdminView;
	}
	
//	public adminControllerNV(homeAdminView homeAdminView) {
////		this.functionAdminView = functionAdminView;
//		this.homeAdminView = new homeAdminView();
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		int selectedRow = functionAdminView.tableNV.getSelectedRow();
		String str = e.getActionCommand();
		if(str.equals("Thêm")) {
			if (functionAdminView.text_TenNV.getText().isEmpty() || 
			    functionAdminView.text_NgayLamNV.getText().isEmpty() || 
			    functionAdminView.comboBox_nhanVienPhong.getSelectedItem() == null || 
			    functionAdminView.comboBox_NhanVienID.getSelectedItem() == null ) {

			        JOptionPane.showMessageDialog(functionAdminView, "Vui lòng điền đầy đủ thông tin ca làm");
			    } else {
			        boolean addPhim = adminModel.addCaLam(
			        	functionAdminView.comboBox_NhanVienID.getSelectedItem().toString(), 
			        	functionAdminView.text_TenNV.getText(), 
			        	functionAdminView.text_NgayLamNV.getText(),
			        	functionAdminView.comboBox_nhanVienPhong.getSelectedItem().toString()
			        );
			        if (addPhim) {
			            
			            // Lấy lại dữ liệu và cập nhật vào model của bảng
			            List<String[]> updatedData = adminModel.thongTinCaLam(); 
			            ((DefaultTableModel) functionAdminView.tableModelNV).setRowCount(0); 
			            
			            // sắp xếp theo ngày làm
			            updatedData.sort((row1, row2) -> row1[2].compareTo(row2[2]));
			            
			            for (String[] row : updatedData) {
			            	((DefaultTableModel) functionAdminView.tableModelNV).addRow(row); 
			            }

			            functionAdminView.tableNV.revalidate(); 
			            functionAdminView.tableNV.repaint(); 
			            
			            functionAdminView.comboBox_NhanVienID.setSelectedItem(null);
			            functionAdminView.text_TenNV.setText("");
			        	functionAdminView.text_NgayLamNV.setText("");
			        	functionAdminView.comboBox_nhanVienPhong.setSelectedItem(null);
			            
				        JOptionPane.showMessageDialog(functionAdminView, "Thêm ca làm thành công");
			            
			        } else {
			            JOptionPane.showMessageDialog(functionAdminView, "Đã có lỗi xảy ra khi thêm ca làm");
			        }
			    }
			
		} else if (str.equals("Xóa")) {
			int selectedRow = functionAdminView.tableNV.getSelectedRow();

			if (selectedRow != -1) {
			    String maNV = functionAdminView.tableModelNV.getValueAt(selectedRow, 0).toString();
			    
			    int confirm = JOptionPane.showConfirmDialog(functionAdminView, "Bạn có chắc chắn muốn xóa ca làm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
			    if (confirm == JOptionPane.YES_OPTION) {
			    	
			        boolean deleteSuccess = adminModel.deleteCaLam(maNV);
			        
			        if (deleteSuccess) {
			            functionAdminView.tableModelNV.removeRow(selectedRow);
			            JOptionPane.showMessageDialog(functionAdminView, "Xóa ca làm thành công");
			        } else {
			            JOptionPane.showMessageDialog(functionAdminView, "Đã có lỗi xảy ra khi xóa phim khỏi cơ sở dữ liệu");
			            
			        }
			    }
			} else {
			    JOptionPane.showMessageDialog(functionAdminView, "Chưa chọn hàng nào để xóa.");
			}
			
		} else if(str.equals("Thoát")) {
			functionAdminView.dispose();
			if (this.homeAdminView == null) {
	            this.homeAdminView = new homeAdminView(); // Khởi tạo đối tượng nếu chưa được khởi tạo
	        }
	        this.homeAdminView.setVisible(true);
			
		} else if(str.equals("Tìm Kiếm")){
			this.timKiemNhanVien = new timKiemNhanVien();
			if(functionAdminView.comboBox_NhanVienID.getSelectedItem() == null && functionAdminView.text_NgayLamNV.getText().isEmpty() && functionAdminView.comboBox_nhanVienPhong.getSelectedItem() == null) {
				JOptionPane.showMessageDialog(functionAdminView, "Chưa có thông tin tìm kiếm");
			} else {
				int rowCount = timKiemNhanVien.tableModel.getRowCount();
				
				// theo mã và tên nhân viên
				List<String>  maNV = new ArrayList<String>();
				for(int i=0; i<rowCount;i++) {
					maNV.add(functionAdminView.tableModelNV.getValueAt(i, 0).toString().toLowerCase());
				}
				
				if(functionAdminView.comboBox_NhanVienID.getSelectedItem() != null) {
					if(maNV.contains(functionAdminView.comboBox_NhanVienID.getSelectedItem().toString().toLowerCase())) {
					List<String[]> updatedData = adminModel.timKiemTheoMaNV(functionAdminView.comboBox_NhanVienID.getSelectedItem().toString().toLowerCase()); 
					
					hienBang(updatedData);
			            
			        functionAdminView.comboBox_NhanVienID.setSelectedItem(null);
					}
				}
				
				// tìm kiếm theo ngày làm 
				List<String> ngayLamNV = new ArrayList<>();

				for (int i = 0; i < rowCount; i++) {
					ngayLamNV.add(functionAdminView.tableModelNV.getValueAt(i, 2).toString());
				}

				if (!functionAdminView.text_NgayLamNV.getText().isEmpty()
						&& ngayLamNV.contains(functionAdminView.text_NgayLamNV.getText())) {
					// Chuyển đổi chuỗi đầu vào thành date
					LocalDate localDate = LocalDate.parse(functionAdminView.text_NgayLamNV.getText());
			        Date inputNgayLam = Date.valueOf(localDate);

					List<String[]> updatedData = adminModel.timKiemTheoNgayLam(inputNgayLam);
					
					hienBang(updatedData);

					functionAdminView.text_NgayLamNV.setText("");

				}
				
				// theo phòng
				List<String>  phongNV = new ArrayList<String>();
				for(int i=0; i<rowCount;i++) {
					phongNV.add(functionAdminView.tableModelNV.getValueAt(i, 3).toString().toLowerCase());
				}
				
				if(functionAdminView.comboBox_nhanVienPhong.getSelectedItem() != null) {
					if(phongNV.contains(functionAdminView.comboBox_nhanVienPhong.getSelectedItem().toString().toLowerCase())) {
					List<String[]> updatedData = adminModel.timKiemTheoPhongNV(functionAdminView.comboBox_nhanVienPhong.getSelectedItem().toString().toLowerCase());
			           
			        hienBang(updatedData);
			            
			        functionAdminView.comboBox_nhanVienPhong.setSelectedItem(null);
					}
				}
			}
			
		} else if (str.equals("Sửa")) {
		    int selectedRow = functionAdminView.tableNV.getSelectedRow();

		    if (selectedRow != -1) {
		        String maNV = functionAdminView.tableModelNV.getValueAt(selectedRow, 0).toString();
		        String ngayLam = functionAdminView.tableModelNV.getValueAt(selectedRow, 2).toString();
		        String phongNV = functionAdminView.tableModelNV.getValueAt(selectedRow, 3).toString();

		        int confirm = JOptionPane.showConfirmDialog(functionAdminView, "Bạn có chắc chắn muốn sửa ca làm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
		        if (confirm == JOptionPane.YES_OPTION) {
		            functionAdminView.comboBox_NhanVienID.setSelectedItem(maNV);
		            functionAdminView.text_NgayLamNV.setText(ngayLam);
		            functionAdminView.text_NgayLamNV.setToolTipText("không thể sửa ngày làm");
		            functionAdminView.text_NgayLamNV.setEditable(false); // không cho chỉnh sửa
		            functionAdminView.comboBox_nhanVienPhong.setSelectedItem(phongNV);
		        }
		    } else {
		        JOptionPane.showMessageDialog(functionAdminView, "Chưa chọn hàng nào để sửa.");
		    }
		} else if(str.equals("Cập Nhật")) {
		    int selectedRow = functionAdminView.tableNV.getSelectedRow();
		    boolean updateSuccess = adminModel.updateCaLam(
		            functionAdminView.comboBox_NhanVienID.getSelectedItem().toString(),
		            functionAdminView.text_TenNV.getText(),
		            functionAdminView.text_NgayLamNV.getText(),
		            functionAdminView.comboBox_nhanVienPhong.getSelectedItem().toString()
		    );

		    if (updateSuccess) {
		        // Cập nhật lại bảng
		    	functionAdminView.tableModelNV.setValueAt(functionAdminView.comboBox_NhanVienID.getSelectedItem().toString(), selectedRow, 0);
		        functionAdminView.tableModelNV.setValueAt(functionAdminView.text_TenNV.getText(), selectedRow, 1);
		        functionAdminView.tableModelNV.setValueAt(functionAdminView.text_NgayLamNV.getText(), selectedRow, 2);
		        functionAdminView.tableModelNV.setValueAt(functionAdminView.comboBox_nhanVienPhong.getSelectedItem().toString(), selectedRow, 3);
		        JOptionPane.showMessageDialog(functionAdminView, "Sửa ca làm thành công");
		    } else {
		        JOptionPane.showMessageDialog(functionAdminView, "Đã có lỗi xảy ra khi sửa ca làm trong cơ sở dữ liệu");
		    }
		}
		
	}
	
	public void hienBang(List<String[]> updatedData) {
		((DefaultTableModel) timKiemNhanVien.tableModel).setRowCount(0); 
        
        for (String[] row : updatedData) {
          	((DefaultTableModel) timKiemNhanVien.tableModel).addRow(row); 
        }

        timKiemNhanVien.table.revalidate(); 
        timKiemNhanVien.table.repaint(); 
            
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                timKiemNhanVien.setVisible(true);
            }
        });
        thread.start();
	}

}


