package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.loginController;
import model.loginModel;

import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.CardLayout;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JPasswordField;

public class loginView extends JFrame {

	private static final long serialVersionUID = 1L;
	public JPanel contentPane;
	public JTextField text_TaiKhoan;
	public JTextField text_TaiKhoanMoi;
	public JTextField text_MatKhauMoi;
	public JTextField text_MatKhauNhapLai;
	public loginModel loginModel;
	public CardLayout cardLayout;
	public ButtonGroup buttonGroup;
	public JRadioButton checkUser;
	public JRadioButton checkAD;
	public JLabel jl_ThongBao1;
	public JLabel jl_ThongBao2;
	public JLabel jl_ThongBao3;
	public homeAdminView homeAdminView;
	public userView userForm;
	public JPasswordField passwordField;
	public JLabel jl_ThongBao4;
	public JLabel jl_ThongBao5;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginView frame = new loginView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public loginView() {
		this.loginModel = new loginModel();
		this.homeAdminView = new homeAdminView();
		this.userForm = new userView();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 630, 400);
		cardLayout = new CardLayout();
        contentPane = new JPanel(cardLayout);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
		
		loginController lgctl = new loginController(this);
		
		JPanel jp_Login1 = new JPanel();
		contentPane.add(jp_Login1, "Login1");
		jp_Login1.setLayout(null);
		
		JLabel jl_avt = new JLabel("");
		jl_avt.setIcon(new ImageIcon("E:\\T03\\quanLyPhim\\quanLyPhim\\src\\view\\login_avt.png"));
		jl_avt.setHorizontalAlignment(SwingConstants.CENTER);
		jl_avt.setFont(new Font("Tahoma", Font.PLAIN, 20));
		jl_avt.setBounds(175, 0, 216, 115);
		jp_Login1.add(jl_avt);
		
		JButton bt_DangNhap = new JButton("Đăng Nhập");
		bt_DangNhap.addActionListener(lgctl);
		bt_DangNhap.setFont(new Font("Tahoma", Font.BOLD, 16));
		bt_DangNhap.setBounds(102, 292, 165, 51);
		jp_Login1.add(bt_DangNhap);
		
		JButton bt_DangKy = new JButton("Đăng Ký");
		bt_DangKy.addActionListener(lgctl);
		bt_DangKy.setFont(new Font("Tahoma", Font.BOLD, 16));
		bt_DangKy.setBounds(335, 292, 165, 51);
		jp_Login1.add(bt_DangKy);
		
		JLabel jl_TaiKhoan = new JLabel("Tài Khoản");
		jl_TaiKhoan.setHorizontalAlignment(SwingConstants.CENTER);
		jl_TaiKhoan.setFont(new Font("Tahoma", Font.BOLD, 16));
		jl_TaiKhoan.setBounds(90, 135, 165, 43);
		jp_Login1.add(jl_TaiKhoan);
		
		JLabel jl_MatKhau = new JLabel("Mật Khẩu");
		jl_MatKhau.setHorizontalAlignment(SwingConstants.CENTER);
		jl_MatKhau.setFont(new Font("Tahoma", Font.BOLD, 16));
		jl_MatKhau.setBounds(90, 188, 165, 43);
		jp_Login1.add(jl_MatKhau);
		
		text_TaiKhoan = new JTextField();
		text_TaiKhoan.setFont(new Font("Tahoma", Font.PLAIN, 16));
		text_TaiKhoan.setBounds(279, 137, 199, 43);
		jp_Login1.add(text_TaiKhoan);
		text_TaiKhoan.setColumns(10);
		
		checkAD = new JRadioButton("Admin");
		checkAD.addActionListener(lgctl);
		checkAD.setFont(new Font("Tahoma", Font.PLAIN, 16));
		checkAD.setHorizontalAlignment(SwingConstants.CENTER);
		checkAD.setBounds(188, 253, 100, 21);
		jp_Login1.add(checkAD);
		
		checkUser = new JRadioButton("User");
		checkUser.addActionListener(lgctl);
		checkUser.setHorizontalAlignment(SwingConstants.CENTER);
		checkUser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		checkUser.setBounds(313, 253, 71, 21);
		jp_Login1.add(checkUser);
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(checkAD);
		buttonGroup.add(checkUser);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField.setBounds(279, 190, 199, 43);
		passwordField.setEchoChar('*');
		jp_Login1.add(passwordField);
		
		jl_ThongBao4 = new JLabel("");
		jl_ThongBao4.setFont(new Font("Tahoma", Font.PLAIN, 8));
		jl_ThongBao4.setBounds(490, 140, 71, 38);
		jp_Login1.add(jl_ThongBao4);
		
		jl_ThongBao5 = new JLabel("");
		jl_ThongBao5.setFont(new Font("Tahoma", Font.PLAIN, 8));
		jl_ThongBao5.setBounds(488, 193, 71, 38);
		jp_Login1.add(jl_ThongBao5);
		
		JPanel jp_Login2 = new JPanel();
		contentPane.add(jp_Login2, "Login2");
		jp_Login2.setLayout(null);
		
		JLabel jl_TaiKhoanMoi = new JLabel("Tài khoản");
		jl_TaiKhoanMoi.setHorizontalAlignment(SwingConstants.CENTER);
		jl_TaiKhoanMoi.setFont(new Font("Tahoma", Font.BOLD, 16));
		jl_TaiKhoanMoi.setBounds(134, 110, 135, 30);
		jp_Login2.add(jl_TaiKhoanMoi);
		
		JLabel jl_avt_1 = new JLabel("");
		jl_avt_1.setIcon(new ImageIcon("E:\\T03\\quanLyPhim\\quanLyPhim\\src\\view\\login_avt.png"));
		jl_avt_1.setHorizontalAlignment(SwingConstants.CENTER);
		jl_avt_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		jl_avt_1.setBounds(215, 0, 161, 100);
		jp_Login2.add(jl_avt_1);
		
		JLabel jl_MatKhauMoi = new JLabel("Mật khẩu");
		jl_MatKhauMoi.setHorizontalAlignment(SwingConstants.CENTER);
		jl_MatKhauMoi.setFont(new Font("Tahoma", Font.BOLD, 16));
		jl_MatKhauMoi.setBounds(134, 170, 135, 30);
		jp_Login2.add(jl_MatKhauMoi);
		
		JLabel jl_MatKhauNhapLai = new JLabel("Nhập lại mật khẩu");
		jl_MatKhauNhapLai.setHorizontalAlignment(SwingConstants.CENTER);
		jl_MatKhauNhapLai.setFont(new Font("Tahoma", Font.BOLD, 16));
		jl_MatKhauNhapLai.setBounds(89, 231, 169, 30);
		jp_Login2.add(jl_MatKhauNhapLai);
		
		text_TaiKhoanMoi = new JTextField();
		text_TaiKhoanMoi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		text_TaiKhoanMoi.setBounds(298, 110, 161, 30);
		jp_Login2.add(text_TaiKhoanMoi);
		text_TaiKhoanMoi.setColumns(10);
		
		text_MatKhauMoi = new JTextField();
		text_MatKhauMoi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		text_MatKhauMoi.setColumns(10);
		text_MatKhauMoi.setBounds(298, 171, 161, 30);
		jp_Login2.add(text_MatKhauMoi);
		
		text_MatKhauNhapLai = new JTextField();
		text_MatKhauNhapLai.setFont(new Font("Tahoma", Font.PLAIN, 14));
		text_MatKhauNhapLai.setColumns(10);
		text_MatKhauNhapLai.setBounds(298, 232, 161, 30);
		jp_Login2.add(text_MatKhauNhapLai);
		
		JButton bt_OK = new JButton("OK");
		bt_OK.addActionListener(lgctl);
		bt_OK.setFont(new Font("Tahoma", Font.BOLD, 20));
		bt_OK.setBounds(216, 292, 160, 40);
		jp_Login2.add(bt_OK);
		
		jl_ThongBao1 = new JLabel("");
		jl_ThongBao1.setBounds(469, 110, 83, 30);
		jl_ThongBao1.setFont(new Font("Tahoma", Font.PLAIN, 8));
		jp_Login2.add(jl_ThongBao1);
		
		jl_ThongBao2 = new JLabel("");
		jl_ThongBao2.setFont(new Font("Tahoma", Font.PLAIN, 8));
		jl_ThongBao2.setBounds(469, 170, 83, 30);
		jp_Login2.add(jl_ThongBao2);
		
		jl_ThongBao3 = new JLabel("");
		jl_ThongBao3.setFont(new Font("Tahoma", Font.PLAIN, 8));
		jl_ThongBao3.setBounds(469, 231, 83, 30);
		jp_Login2.add(jl_ThongBao3);
		
		this.setVisible(true);
	}
	
	public String TaiKhoan() {
	    String a = this.text_TaiKhoan.getText();
	    this.loginModel.setTaiKhoan(a);
	    return this.loginModel.getTaiKhoan();
	}
	
	public String MatKhau() {
	    char[] passwordChars = this.passwordField.getPassword(); 
	    String b = new String(passwordChars); 
	    this.loginModel.setMatKhau(b); 
	    return this.loginModel.getMatKhau();
	}
}
