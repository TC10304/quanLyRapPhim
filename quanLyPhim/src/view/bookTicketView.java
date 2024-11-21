package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import controller.bookTicketController;
import model.bookTicketModel;

import javax.swing.JLabel;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class bookTicketView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
//	private userView userView;
	public JLabel jl_TenPhim;
	public JLabel jl_TheLoai;
	public JLabel jl_ThoiLuong;
	public JLabel jl_PhongChieu;
	public JLabel jl_GioChieu;
	public bookTicketModel bookTicketModel;
	public JLabel jl_TongTien;
	public List<String> seatName = new ArrayList<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					userView userView = new userView();
					bookTicketView frame = new bookTicketView(userView);
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
	public bookTicketView(userView userView) {
//		this.userView = userView;
		bookTicketModel = new bookTicketModel();
		
//		seatName = new ArrayList<String>();
		
//		soGhe = new ArrayList<Integer>();
//		for (Integer integer2 : seatID) {
//			System.out.println();
//		}
		
//		checkBillView cbv = new checkBillView(userView);
		bookTicketController btkctl = new bookTicketController(this);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Đặt Vé");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(290, 10, 200, 40);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Tên Phim");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblNewLabel_1.setBounds(90, 60, 90, 20);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Thể Loại");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblNewLabel_1_1.setBounds(90, 95, 90, 20);
		contentPane.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Thời Lượng");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblNewLabel_1_2.setBounds(90, 130, 90, 20);
		contentPane.add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_3 = new JLabel("Giờ Chiếu");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblNewLabel_1_3.setBounds(475, 95, 90, 20);
		contentPane.add(lblNewLabel_1_3);

		JLabel lblNewLabel_1_4 = new JLabel("Phòng Chiếu");
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblNewLabel_1_4.setBounds(475, 60, 90, 20);
		contentPane.add(lblNewLabel_1_4);

		jl_TenPhim = new JLabel("");
		jl_TenPhim.setBackground(new Color(255, 255, 255));
		jl_TenPhim.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jl_TenPhim.setBounds(190, 60, 130, 20);
		contentPane.add(jl_TenPhim);

		jl_TheLoai = new JLabel("");
		jl_TheLoai.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jl_TheLoai.setBackground(Color.WHITE);
		jl_TheLoai.setBounds(190, 95, 130, 20);
		contentPane.add(jl_TheLoai);

		jl_ThoiLuong = new JLabel("");
		jl_ThoiLuong.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jl_ThoiLuong.setBackground(Color.WHITE);
		jl_ThoiLuong.setBounds(190, 130, 130, 20);
		contentPane.add(jl_ThoiLuong);

		jl_PhongChieu = new JLabel("");
		jl_PhongChieu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jl_PhongChieu.setBackground(Color.WHITE);
		jl_PhongChieu.setBounds(575, 60, 130, 20);
		contentPane.add(jl_PhongChieu);

		jl_GioChieu = new JLabel("");
		jl_GioChieu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jl_GioChieu.setBackground(Color.WHITE);
		jl_GioChieu.setBounds(575, 95, 150, 20);
		contentPane.add(jl_GioChieu);

		// Phần xử lý hiển thị nút ghế
		if (userView != null && userView.table != null) {
			int selectedRow = userView.table.getSelectedRow();
			if (selectedRow != -1) {
				jl_TenPhim.setText(userView.tableModel.getValueAt(selectedRow, 0).toString());
				jl_TheLoai.setText(userView.tableModel.getValueAt(selectedRow, 1).toString());
				jl_ThoiLuong.setText(userView.tableModel.getValueAt(selectedRow, 3).toString());
				jl_PhongChieu.setText(userView.tableModel.getValueAt(selectedRow, 2).toString());
				jl_GioChieu.setText(userView.tableModel.getValueAt(selectedRow, 4).toString());
			}

			JPanel jp_ghe = new JPanel();
			jp_ghe.setBounds(10, 166, 814, 350);
			contentPane.add(jp_ghe);
			jp_ghe.setLayout(new GridLayout(8, 15, 5, 5));

			// Lấy dữ liệu hàng và số ghế dựa trên tên phòng chiếu
			String hallName = jl_PhongChieu.getText();
			String movieName = jl_TenPhim.getText();
			List<String> seatRow = model.bookTicketModel.getSeatRow(hallName);
			List<Integer> seatNumber = model.bookTicketModel.getSeatNumber(hallName);

			for (String row : seatRow) {
				for (int num : seatNumber) {
					JButton btn = new JButton(row + num);
					boolean isAvailable = model.bookTicketModel.getSeatAvailability(row, num, hallName,movieName);
					if (!isAvailable) {
						btn.setBackground(Color.GREEN); // ghế đã đặt
						btn.setToolTipText("Đã được đặt");
						btn.setEnabled(false); // hủy sự kiện
					} else {
						btn.setBackground(Color.WHITE); // ghế trống
					}
					btn.setOpaque(true);
					btn.setFont(new Font("Tahoma", Font.PLAIN, 9));
					btn.setHorizontalAlignment(SwingConstants.CENTER);
					btn.setVerticalAlignment(SwingConstants.CENTER);
					if (row.equals("D") || row.equals("E") || row.equals("F")) {
						btn.setBorder(new MatteBorder(2, 2, 2, 2, new Color(255, 105, 180))); // Viền hồng
					} else {
						btn.setBorder(new MatteBorder(2, 2, 2, 2, Color.GRAY)); // Viền xám
					}
					btn.addActionListener(btkctl);
					jp_ghe.add(btn);
				}
			}
		}

		JLabel jl_Back = new JLabel("");
		jl_Back.addMouseListener(btkctl);
		jl_Back.setHorizontalAlignment(SwingConstants.CENTER);
		jl_Back.setIcon(new ImageIcon("E:\\T03\\quanLyPhim\\quanLyPhim\\src\\view\\icons8-back-24.png"));
		jl_Back.setBounds(20, 15, 30, 30);
		contentPane.add(jl_Back);

		JLabel lblNewLabel_1_5 = new JLabel("Tổng tiền");
		lblNewLabel_1_5.setFont(new Font("Tahoma", Font.ITALIC, 16));
		lblNewLabel_1_5.setBounds(90, 534, 90, 20);
		contentPane.add(lblNewLabel_1_5);

		jl_TongTien = new JLabel("");
		jl_TongTien.setFont(new Font("Tahoma", Font.PLAIN, 16));
		jl_TongTien.setBackground(Color.WHITE);
		jl_TongTien.setBounds(190, 534, 130, 20);
		contentPane.add(jl_TongTien);
		
//		model.bookTicketModel.addTicket(bookTicketModel.tongTien, jl_TenPhim.getText());

		JLabel jl_ThanhToan = new JLabel("Thanh Toán");
		jl_ThanhToan.setBackground(Color.WHITE);
		Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
		jl_ThanhToan.setBorder(blackBorder);
		jl_ThanhToan.setForeground(Color.BLACK);
		jl_ThanhToan.setOpaque(true);
		jl_ThanhToan.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				model.bookTicketModel.updateTicket(bookTicketModel.tongTien, jl_TenPhim.getText());
				
				 checkBillView checkBillView = new checkBillView(userView);
				 checkBillView.setSeatNames(seatName);
				 checkBillView.setVisible(true);
				checkBillView.setLocationRelativeTo(null);
			}
		});
		jl_ThanhToan.setHorizontalAlignment(SwingConstants.CENTER);
		jl_ThanhToan.setFont(new Font("Tahoma", Font.BOLD, 20));
		jl_ThanhToan.setBounds(557, 524, 168, 37);
		contentPane.add(jl_ThanhToan);
	}
}
