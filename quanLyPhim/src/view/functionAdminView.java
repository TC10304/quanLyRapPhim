package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.CardLayout;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.adminControllerNV;
import controller.adminControllerPhim;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.util.List;
import javax.swing.JComboBox;

public class functionAdminView extends JFrame {

	private static final long serialVersionUID = 1L;
	public JPanel contentPane;
	public CardLayout cardLayout;
	private JPanel jp_Film;
	public JTable tablePhim;
	public JTextField text_tenPhim;
	public JTextField text_TheLoai;
	public JTextField text_ThoiLuong;
	public JTextField text_GioChieu;
	public JTextField text_TenNV;
	public JTextField text_NgayLamNV;
	public JTable tableNV;
	public model.adminModel adminModel;
	public DefaultTableModel tableModelPhim;
	public DefaultTableModel tableModelNV;
	public JComboBox<String> comboBox;
	public JComboBox<?> comboBox_nhanVienPhong;
	public JComboBox<String> comboBox_NhanVienID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					functionAdminView frame = new functionAdminView();
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
	public functionAdminView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 810, 550);
		cardLayout = new CardLayout();
        contentPane = new JPanel(cardLayout);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        
        jp_Film = new JPanel();
        contentPane.add(jp_Film, "Film");
        jp_Film.setLayout(null);
        
        adminControllerPhim adctl = new adminControllerPhim(this);
        adminControllerNV adctlnv = new adminControllerNV(this);
        
        JLabel jl_QuanLyPhim = new JLabel("Quản Lý Phim");
        jl_QuanLyPhim.setHorizontalAlignment(SwingConstants.CENTER);
        jl_QuanLyPhim.setFont(new Font("Tahoma", Font.BOLD, 20));
        jl_QuanLyPhim.setBounds(280, 10, 270, 50);
        jp_Film.add(jl_QuanLyPhim);
        
        // hiện phim lên table
        List<String[]> data = model.adminModel.thongTinPhim();
        String[] columnNames = {"Tên phim", "Thể loại", "Phòng chiếu", "Thời lượng", "Giờ chiếu"}; 
        tableModelPhim = new DefaultTableModel(columnNames, 0);
        
        for (String[] row : data) {
            tableModelPhim.addRow(row);
        }
        
        tablePhim = new JTable(tableModelPhim);
        tablePhim.setModel(tableModelPhim);
        // căn giữa ô table
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        for (int i = 0; i < tablePhim.getColumnCount(); i++) {
            tablePhim.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        tablePhim.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(tablePhim);
        scrollPane.setBounds(0, 125, 786, 99);
        jp_Film.add(scrollPane);
        
        JLabel lblNewLabel = new JLabel("Tên phim");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel.setBounds(20, 300, 105, 25);
        jp_Film.add(lblNewLabel);
        
        JLabel lblThLoi = new JLabel("Thể loại");
        lblThLoi.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblThLoi.setBounds(20, 350, 105, 25);
        jp_Film.add(lblThLoi);
        
        JLabel lblThiLng = new JLabel("Thời lượng");
        lblThiLng.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblThiLng.setBounds(20, 400, 90, 25);
        jp_Film.add(lblThiLng);
        
        JLabel lblDanhSchPhim = new JLabel("Danh sách phim");
        lblDanhSchPhim.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblDanhSchPhim.setBounds(20, 60, 215, 40);
        jp_Film.add(lblDanhSchPhim);
        
        JLabel lblPhngChiu = new JLabel("Phòng chiếu");
        lblPhngChiu.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPhngChiu.setBounds(368, 300, 120, 25);
        jp_Film.add(lblPhngChiu);
        
        JLabel lblGiChiu = new JLabel("Giờ chiếu");
        lblGiChiu.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblGiChiu.setBounds(368, 350, 120, 25);
        jp_Film.add(lblGiChiu);
        
//        JLabel lblChnhSaDanh = new JLabel("Chỉnh sửa danh sách phim");
//        lblChnhSaDanh.setFont(new Font("Tahoma", Font.BOLD, 16));
//        lblChnhSaDanh.setBounds(20, 234, 250, 40);
//        jp_Film.add(lblChnhSaDanh);
        
        JButton btn_ThemFilm = new JButton("Thêm");
        btn_ThemFilm.addActionListener(adctl);
        btn_ThemFilm.setFont(new Font("Tahoma", Font.BOLD, 16));
        btn_ThemFilm.setBounds(20, 448, 140, 42);
        jp_Film.add(btn_ThemFilm);
        
        text_tenPhim = new JTextField();
        text_tenPhim.setFont(new Font("Tahoma", Font.PLAIN, 14));
        text_tenPhim.setBounds(119, 300, 176, 25);
        jp_Film.add(text_tenPhim);
        text_tenPhim.setColumns(10);
        
        text_TheLoai = new JTextField();
        text_TheLoai.setFont(new Font("Tahoma", Font.PLAIN, 14));
        text_TheLoai.setColumns(10);
        text_TheLoai.setBounds(119, 350, 176, 25);
        jp_Film.add(text_TheLoai);
        
        text_ThoiLuong = new JTextField();
        text_ThoiLuong.setFont(new Font("Tahoma", Font.PLAIN, 14));
        text_ThoiLuong.setColumns(10);
        text_ThoiLuong.setBounds(120, 400, 176, 25);
        jp_Film.add(text_ThoiLuong);
        
        text_GioChieu = new JTextField();
        text_GioChieu.setFont(new Font("Tahoma", Font.PLAIN, 14));
        text_GioChieu.setColumns(10);
        text_GioChieu.setBounds(490, 350, 176, 25);
        jp_Film.add(text_GioChieu);
        
        JButton btn_XoaPhim = new JButton("Xóa");
        btn_XoaPhim.addActionListener(adctl);
        btn_XoaPhim.setFont(new Font("Tahoma", Font.BOLD, 16));
        btn_XoaPhim.setBounds(227, 448, 140, 42);
        jp_Film.add(btn_XoaPhim);
        
        JButton btn_TimKiemPhim = new JButton("Tìm Kiếm");
        btn_TimKiemPhim.addActionListener(adctl);
        btn_TimKiemPhim.setFont(new Font("Tahoma", Font.BOLD, 16));
        btn_TimKiemPhim.setBounds(425, 448, 140, 42);
        jp_Film.add(btn_TimKiemPhim);
        
        JButton btn_ThoatPhim = new JButton("Thoát");
        btn_ThoatPhim.addActionListener(adctl);
        btn_ThoatPhim.setFont(new Font("Tahoma", Font.BOLD, 16));
        btn_ThoatPhim.setBounds(624, 448, 140, 42);
        jp_Film.add(btn_ThoatPhim);
        
        String[] phongChieu = {"Phòng 1", "Phòng 2", "Phòng 3", "Phòng 4", "Phòng 5", "Phòng 6"};
        comboBox = new JComboBox<String>(phongChieu);
        comboBox.setSelectedItem(null);
        comboBox.setBounds(490, 302, 176, 25);
        jp_Film.add(comboBox);
        
        JPanel jp_NhanVien = new JPanel();
        jp_NhanVien.setLayout(null);
        contentPane.add(jp_NhanVien, "Staff");
        
        JLabel jl_QuanLyPhim_1 = new JLabel("Quản Lý Nhân Viên");
        jl_QuanLyPhim_1.setHorizontalAlignment(SwingConstants.CENTER);
        jl_QuanLyPhim_1.setFont(new Font("Tahoma", Font.BOLD, 20));
        jl_QuanLyPhim_1.setBounds(260, 11, 270, 50);
        jp_NhanVien.add(jl_QuanLyPhim_1);
        
        // hiện phim lên table
        List<String[]> data_NV = model.adminModel.thongTinCaLam();
        String[] columnNames_NV = {"Mã nhân viên", "Tên nhân viên", "Ngày Làm", "Phòng"}; 
        tableModelNV = new DefaultTableModel(columnNames_NV, 0);
        
        for (String[] row : data_NV) {
            tableModelNV.addRow(row);
        }
        
        tableNV = new JTable(tableModelNV);
        tableNV.setModel(tableModelNV);
        
        for (int i = 0; i < tableNV.getColumnCount(); i++) {
            tableNV.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        tableNV.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JScrollPane scrollPane_1 = new JScrollPane(tableNV);
        scrollPane_1.setBounds(0, 125, 774, 99);
        jp_NhanVien.add(scrollPane_1);
        
        JLabel lblNewLabel_1 = new JLabel("Mã nhân viên");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1.setBounds(20, 284, 105, 25);
        jp_NhanVien.add(lblNewLabel_1);
        
        JLabel lblThLoi_1 = new JLabel("Tên nhân viên");
        lblThLoi_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblThLoi_1.setBounds(20, 319, 105, 25);
        jp_NhanVien.add(lblThLoi_1);
        
        JLabel lblThiLng_1 = new JLabel("Ngày làm");
        lblThiLng_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblThiLng_1.setBounds(20, 354, 105, 25);
        jp_NhanVien.add(lblThiLng_1);
        
        JLabel lblDanhSchNhn = new JLabel("Danh sách nhân viên");
        lblDanhSchNhn.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblDanhSchNhn.setBounds(20, 60, 215, 40);
        jp_NhanVien.add(lblDanhSchNhn);
        
        JLabel lblChnhSaDanh_1 = new JLabel("Chỉnh sửa phân công công việc");
        lblChnhSaDanh_1.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblChnhSaDanh_1.setBounds(20, 234, 330, 40);
        jp_NhanVien.add(lblChnhSaDanh_1);
        
        JButton btn_ThemNV = new JButton("Thêm");
        btn_ThemNV.addActionListener(adctlnv);
        btn_ThemNV.setFont(new Font("Tahoma", Font.BOLD, 16));
        btn_ThemNV.setBounds(416, 284, 120, 42);
        jp_NhanVien.add(btn_ThemNV);
        
        text_TenNV = new JTextField();
        text_TenNV.setEditable(false);
        text_TenNV.setFont(new Font("Tahoma", Font.PLAIN, 14));
        text_TenNV.setColumns(10);
        text_TenNV.setBounds(174, 319, 176, 25);
        jp_NhanVien.add(text_TenNV);
        
        text_NgayLamNV = new JTextField();
        text_NgayLamNV.setFont(new Font("Tahoma", Font.PLAIN, 14));
        text_NgayLamNV.setColumns(10);
        text_NgayLamNV.setBounds(174, 354, 176, 25);
        jp_NhanVien.add(text_NgayLamNV);
        
        JButton btn_SuaNV = new JButton("Sửa");
        btn_SuaNV.addActionListener(adctlnv);
        btn_SuaNV.setFont(new Font("Tahoma", Font.BOLD, 16));
        btn_SuaNV.setBounds(416, 337, 120, 42);
        jp_NhanVien.add(btn_SuaNV);
        
        JButton btn_XoaNV = new JButton("Xóa");
        btn_XoaNV.addActionListener(adctlnv);
        btn_XoaNV.setFont(new Font("Tahoma", Font.BOLD, 16));
        btn_XoaNV.setBounds(617, 284, 120, 42);
        jp_NhanVien.add(btn_XoaNV);
        
        JButton btn_TimKiemNV = new JButton("Tìm Kiếm");
        btn_TimKiemNV.addActionListener(adctlnv);
        btn_TimKiemNV.setFont(new Font("Tahoma", Font.BOLD, 16));
        btn_TimKiemNV.setBounds(416, 390, 120, 42);
        jp_NhanVien.add(btn_TimKiemNV);
        
        JButton btn_ThoatNV = new JButton("Thoát");
        btn_ThoatNV.addActionListener(adctlnv);
        btn_ThoatNV.setFont(new Font("Tahoma", Font.BOLD, 16));
        btn_ThoatNV.setBounds(617, 390, 120, 42);
        jp_NhanVien.add(btn_ThoatNV);
        
        JLabel lblThiLng_1_1 = new JLabel("Phòng");
        lblThiLng_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblThiLng_1_1.setBounds(20, 389, 105, 25);
        jp_NhanVien.add(lblThiLng_1_1);
        
        String[] maNV = model.adminModel.layMaNhanVien();
        comboBox_NhanVienID = new JComboBox<String>(maNV);
        comboBox_NhanVienID.setSelectedItem(null);
        comboBox_NhanVienID.addActionListener(e -> {
            Object selectedItem = comboBox_NhanVienID.getSelectedItem();
            if (selectedItem != null) {
                String selectedStaffID = selectedItem.toString();
                
                String staffName = model.adminModel.layTenNhanVien(selectedStaffID);
                
                text_TenNV.setText(staffName);
            } else {
                text_TenNV.setText("");
            }
        });
        comboBox_NhanVienID.setBounds(174, 286, 176, 25);
        jp_NhanVien.add(comboBox_NhanVienID);
        
		comboBox_nhanVienPhong = new JComboBox<String>(phongChieu);
        comboBox_nhanVienPhong.setSelectedItem(null);
        comboBox_nhanVienPhong.setBounds(174, 390, 176, 25);
        jp_NhanVien.add(comboBox_nhanVienPhong);
        
        JButton btn_updateNV = new JButton("Cập Nhật");
        btn_updateNV.addActionListener(adctlnv);
        btn_updateNV.setFont(new Font("Tahoma", Font.BOLD, 16));
        btn_updateNV.setBounds(617, 337, 120, 42);
        jp_NhanVien.add(btn_updateNV);
        

	}
}
