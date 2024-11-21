package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.checkBillConTroller;
import model.checkBillModel;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class checkBillView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private checkBillModel checkBillModel;
	public List<String> seat_Name;
	public JLabel jl_Ghe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					userView userView = new userView();
					checkBillView frame = new checkBillView(userView);
//					frame.seat_Name = new ArrayList<String>();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param userView
	 */
	public checkBillView(userView userView) {
//		System.out.println("Tên ghế trong checkBillView: ");
//		seat_Name = new ArrayList<String>();
//		seat_Name = bookTicketView
//		for (String string : seat_Name) {
//			System.out.print(string +", ");
//		}
		
//		setSeatNames(seat_Name);
		
		if (seat_Name == null) {
	        seat_Name = new ArrayList<>();
	    }
		
		checkBillModel = new checkBillModel();

		checkBillConTroller cbctl = new checkBillConTroller(this);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Thông Tin Vé");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(70, 10, 293, 46);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Tên Phim");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblNewLabel_1.setBounds(25, 120, 90, 20);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Thể loại");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblNewLabel_1_1.setBounds(25, 170, 90, 20);
		contentPane.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Thời lượng");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblNewLabel_1_2.setBounds(25, 220, 90, 20);
		contentPane.add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_3 = new JLabel("Phòng chiếu");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblNewLabel_1_3.setBounds(25, 270, 90, 20);
		contentPane.add(lblNewLabel_1_3);

		JLabel lblNewLabel_1_4 = new JLabel("Giờ chiếu");
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblNewLabel_1_4.setBounds(25, 320, 90, 20);
		contentPane.add(lblNewLabel_1_4);

		JLabel lblNewLabel_1_5 = new JLabel("Ghế");
		lblNewLabel_1_5.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblNewLabel_1_5.setBounds(25, 370, 90, 20);
		contentPane.add(lblNewLabel_1_5);

		JLabel lblNewLabel_1_6 = new JLabel("Giá vé");
		lblNewLabel_1_6.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblNewLabel_1_6.setBounds(25, 420, 90, 20);
		contentPane.add(lblNewLabel_1_6);

		JLabel jl_TenPhim = new JLabel("");
		jl_TenPhim.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jl_TenPhim.setBackground(Color.WHITE);
		jl_TenPhim.setBounds(140, 120, 130, 20);
		contentPane.add(jl_TenPhim);

		JLabel jl_TheLoai = new JLabel("");
		jl_TheLoai.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jl_TheLoai.setBackground(Color.WHITE);
		jl_TheLoai.setBounds(140, 170, 130, 20);
		contentPane.add(jl_TheLoai);

		JLabel jl_ThoiLuong = new JLabel("");
		jl_ThoiLuong.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jl_ThoiLuong.setBackground(Color.WHITE);
		jl_ThoiLuong.setBounds(140, 220, 130, 20);
		contentPane.add(jl_ThoiLuong);

		JLabel jl_PhongChieu = new JLabel("");
		jl_PhongChieu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jl_PhongChieu.setBackground(Color.WHITE);
		jl_PhongChieu.setBounds(140, 270, 130, 20);
		contentPane.add(jl_PhongChieu);

		JLabel jl_GioChieu = new JLabel("");
		jl_GioChieu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jl_GioChieu.setBackground(Color.WHITE);
		jl_GioChieu.setBounds(140, 320, 170, 20);
		contentPane.add(jl_GioChieu);

		jl_Ghe = new JLabel("");
		
		if (!seat_Name.isEmpty()) {
	        jl_Ghe.setText(String.join(", ", seat_Name)); 
	    } else {
	        jl_Ghe.setText("Chưa chọn ghế");
	    }
		
		jl_Ghe.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jl_Ghe.setBackground(Color.WHITE);
		jl_Ghe.setBounds(140, 370, 130, 20);
		contentPane.add(jl_Ghe);

		JLabel jl_GiaVe = new JLabel("");
		jl_GiaVe.setText(checkBillModel.getPrice());
		jl_GiaVe.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jl_GiaVe.setBackground(Color.WHITE);
		jl_GiaVe.setBounds(140, 420, 130, 20);
		contentPane.add(jl_GiaVe);

		if (userView != null && userView.table != null) {
			int selectedRow = userView.table.getSelectedRow();
			if (selectedRow != -1) {
				jl_TenPhim.setText(userView.tableModel.getValueAt(selectedRow, 0).toString());
				jl_TheLoai.setText(userView.tableModel.getValueAt(selectedRow, 1).toString());
				jl_ThoiLuong.setText(userView.tableModel.getValueAt(selectedRow, 3).toString());
				jl_PhongChieu.setText(userView.tableModel.getValueAt(selectedRow, 2).toString());
				jl_GioChieu.setText(userView.tableModel.getValueAt(selectedRow, 4).toString());
			}
		}

		JLabel jl_Back = new JLabel("");
		jl_Back.addMouseListener(cbctl);
		jl_Back.setIcon(new ImageIcon("E:\\T03\\quanLyPhim\\quanLyPhim\\src\\view\\icons8-back-24.png"));
		jl_Back.setHorizontalAlignment(SwingConstants.CENTER);
		jl_Back.setBounds(30, 20, 30, 30);
		contentPane.add(jl_Back);

		JButton btnNewButton = new JButton("Thanh Toán");
		btnNewButton.addActionListener(cbctl);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton.setBounds(89, 455, 274, 46);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1_7 = new JLabel("Mã Hóa Đơn");
		lblNewLabel_1_7.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblNewLabel_1_7.setBounds(25, 70, 90, 20);
		contentPane.add(lblNewLabel_1_7);
		
		JLabel jl_MaHoaDon = new JLabel("");
		jl_MaHoaDon.setText(checkBillModel.getTicketID());
		jl_MaHoaDon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jl_MaHoaDon.setBackground(Color.WHITE);
		jl_MaHoaDon.setBounds(140, 70, 130, 20);
		contentPane.add(jl_MaHoaDon);
		
		
	}

	public void setSeatNames(List<String> seatNames) {
	    this.seat_Name = seatNames != null ? seatNames : new ArrayList<>();
	    if (jl_Ghe != null) {
	        jl_Ghe.setText(String.join(", ", this.seat_Name));
	    }
	}

}
