package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import controller.userController;
import model.adminModel;
import model.userModel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.RowFilter;

public class userView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public DefaultTableModel tableModel;
	public JTable table;
	public JTextField text_timTenPhim;
	public JButton btn_DatVe;
	public JButton btn_Thoat;
	public userModel userModel;
//	public DefaultRowSorter<DefaultTableModel> sorter;
	private TableRowSorter<DefaultTableModel> sorter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					userView frame = new userView();
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
	public userView() {
		userModel = new userModel();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		userController usctl = new userController(this);

		List<String[]> data = adminModel.thongTinPhim();
		String[] columnNames = { "Tên phim", "Thể loại", "Phòng chiếu", "Thời lượng", "Giờ chiếu" };
		tableModel = new DefaultTableModel(columnNames, 0);

		for (String[] row : data) {
			tableModel.addRow(row);
		}

		table = new JTable(tableModel);
		table.setModel(tableModel);
		// căn giữa ô table
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));

		sorter = new TableRowSorter<>(tableModel);
		table.setRowSorter(sorter);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 109, 786, 150);
		contentPane.add(scrollPane);

		JLabel lblNewLabel = new JLabel("Lịch chiếu phim");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(10, 49, 135, 30);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("CINEMA");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel_1.setBounds(200, 0, 326, 41);
		contentPane.add(lblNewLabel_1);

		btn_DatVe = new JButton("Đặt Vé");
		btn_DatVe.addActionListener(usctl);
		btn_DatVe.setFont(new Font("Tahoma", Font.BOLD, 15));
		btn_DatVe.setBounds(263, 282, 263, 40);
		contentPane.add(btn_DatVe);

		btn_Thoat = new JButton("Thoát");
		btn_Thoat.addActionListener(usctl);
		btn_Thoat.setFont(new Font("Tahoma", Font.BOLD, 15));
		btn_Thoat.setBounds(593, 282, 150, 40);
		contentPane.add(btn_Thoat);

		JLabel lblTnPhim = new JLabel("Tên phim");
		lblTnPhim.setHorizontalAlignment(SwingConstants.LEFT);
		lblTnPhim.setFont(new Font("Tahoma", Font.ITALIC, 16));
		lblTnPhim.setBounds(500, 49, 80, 30);
		contentPane.add(lblTnPhim);

		text_timTenPhim = new JTextField();
		text_timTenPhim.getDocument().addDocumentListener(usctl);
		text_timTenPhim.setFont(new Font("Tahoma", Font.PLAIN, 16));
		text_timTenPhim.setBounds(593, 49, 180, 30);
		contentPane.add(text_timTenPhim);
		text_timTenPhim.setColumns(10);

		JButton btn_thongTinVe = new JButton("Thông Tin Vé");
		btn_thongTinVe.addActionListener(usctl);
		btn_thongTinVe.setFont(new Font("Tahoma", Font.BOLD, 15));
		btn_thongTinVe.setBounds(40, 282, 150, 40);
		contentPane.add(btn_thongTinVe);
	}

	public void userTimKiem() {
		userModel.setTenPhim(text_timTenPhim.getText());
		if (userModel.getTenPhim().trim().length() == 0) {
			sorter.setRowFilter(null); // Hiển tất cả bảng khi ko muốn tìm tên phim
		} else {
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + userModel.getTenPhim(), 0)); // Tìm kiếm không phân biệt
																							// hoa/thường
		}
	}
}
