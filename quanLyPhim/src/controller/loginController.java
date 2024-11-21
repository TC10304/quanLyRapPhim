package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import connect.connectDatabase;
import view.loginView;

public class loginController implements ActionListener{
	private loginView loginView;

	public loginController(loginView loginView) {
		this.loginView = loginView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

        String str = e.getActionCommand();
        if(loginView.checkAD.isSelected()) {
	        if(str.equals("Đăng Ký")) {
	        	JOptionPane.showMessageDialog(loginView, "Không thể đăng ký tài khoản admin");
//	        	loginView.cardLayout.show(loginView.contentPane, "Login2");
	        } else if(str.equals("Đăng Nhập")) {
	        	if(loginView.text_TaiKhoan.getText().isEmpty()) {
	        		loginView.jl_ThongBao4.setText("Nhập tài khoản");
	        		loginView.jl_ThongBao4.setForeground(Color.RED);
	        	} else if(loginView.passwordField.getPassword().length == 0) {
	        		loginView.jl_ThongBao4.setText("");
	        		loginView.jl_ThongBao5.setText("Nhập mật khẩu");
	        		loginView.jl_ThongBao5.setForeground(Color.RED);
	        	} else {
		        	String taiKhoan = loginView.TaiKhoan();
			        String matKhau = loginView.MatKhau();
			
			        // Kiểm tra thông tin đăng nhập
			        boolean isValidUser = connectDatabase.AD(taiKhoan, matKhau);
			
			        if (isValidUser) {
	//		            System.out.println("Đăng nhập thành công!");
			            this.loginView.dispose();
			            loginView.homeAdminView.setVisible(true);
			            
			            // Tiếp tục bước tiếp theo, chẳng hạn như mở giao diện mới
			        } else {
	//		            System.out.println("Tên tài khoản hoặc mật khẩu không chính xác.");
			            JOptionPane.showMessageDialog(loginView, "Tài khoản hoặc mật khẩu không đúng");
			        }
	        	}  
	        } else if(str.equals("OK")) {
	        	JOptionPane.showMessageDialog(loginView, "Đăng ký thành công" + "\nMời bạn đăng nhập");
	        	loginView.cardLayout.show(loginView.contentPane, "Login1");
	        }
        } else if(loginView.checkUser.isSelected()) {
	        if(str.equals("Đăng Ký")) {
//	        	JOptionPane.showMessageDialog(loginView, "Không thể đăng ký tài khoản admin");
	        	loginView.cardLayout.show(loginView.contentPane, "Login2");
	        } else if(str.equals("Đăng Nhập")) {
	        	if(loginView.text_TaiKhoan.getText().isEmpty()) {
	        		loginView.jl_ThongBao1.setText("Nhập tài khoản");
	        		loginView.jl_ThongBao1.setForeground(Color.RED);
	        	} else if(loginView.passwordField.getPassword().length == 0) {
	        		loginView.jl_ThongBao1.setText("");
	        		loginView.jl_ThongBao2.setText("Nhập mật khẩu");
	        		loginView.jl_ThongBao2.setForeground(Color.RED);
	        	} else {
		        	String taiKhoan = loginView.TaiKhoan();
			        String matKhau = loginView.MatKhau();
			
			        // Kiểm tra thông tin đăng nhập
			        boolean isValidUser = connectDatabase.User(taiKhoan, matKhau);
			
			        if (isValidUser) {
	//		            System.out.println("Đăng nhập thành công!");
			            this.loginView.dispose();
			            loginView.userForm.setVisible(true);
			            
			            // Tiếp tục bước tiếp theo, chẳng hạn như mở giao diện mới
			        } else {
	//		            System.out.println("Tên tài khoản hoặc mật khẩu không chính xác.");
			            JOptionPane.showMessageDialog(loginView, "Tài khoản hoặc mật khẩu không đúng");
			        }
	        	}			        
	        } else if(str.equals("OK")) {
	        	if(loginView.text_TaiKhoanMoi.getText().isEmpty()) {
	        		loginView.jl_ThongBao1.setText("Nhập tài khoản");
	        		loginView.jl_ThongBao1.setForeground(Color.RED);
	        	} else if(loginView.text_MatKhauMoi.getText().isEmpty()) {
	        		loginView.jl_ThongBao1.setText("");
	        		loginView.jl_ThongBao2.setText("Nhập mật khẩu");
	        		loginView.jl_ThongBao2.setForeground(Color.RED);
	        	} else if(loginView.text_MatKhauNhapLai.getText().isEmpty()) {
	        		loginView.jl_ThongBao1.setText("");
	        		loginView.jl_ThongBao2.setText("");
	        		loginView.jl_ThongBao3.setText("Nhập lại mật khẩu");
	        		loginView.jl_ThongBao3.setForeground(Color.RED);
	        	} else if(loginView.text_MatKhauMoi.getText().equals(loginView.text_MatKhauNhapLai.getText())){
	        		
	        		boolean isAdded = connectDatabase.addUser(loginView.text_TaiKhoanMoi.getText(), loginView.text_MatKhauNhapLai.getText());
	        		if (isAdded) {
//	        		    System.out.println("Thêm người dùng thành công!");
		        		JOptionPane.showMessageDialog(loginView, "Đăng ký thành công" + "\nMời bạn đăng nhập");
			        	loginView.cardLayout.show(loginView.contentPane, "Login1");
	        		} else {
//	        		    System.out.println("Thêm người dùng thất bại!");
	        			JOptionPane.showMessageDialog(loginView, "Đăng ký thất bại");
	        		}
		        	
	        	} else {
	        		loginView.jl_ThongBao3.setText("Sai mật khẩu");
	        		loginView.jl_ThongBao3.setForeground(Color.RED);
	        	}
	        	
	        }
        }
        if(!loginView.checkAD.isSelected() && !loginView.checkUser.isSelected()) {
        	if(str.equals("Đăng Ký") || str.equals("Đăng Nhập")) {
        		JOptionPane.showMessageDialog(loginView, "Vui lòng tích chọn kiểu tài khoản");
        	}
        }
	
	}	

}