package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.LineBorder;

import controller.homeAdminController;

import java.awt.Color;
import javax.swing.ImageIcon;

public class homeAdminView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JPanel jp_Film;
	public JPanel jp_Staff;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					homeAdminView frame = new homeAdminView();
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
	public homeAdminView() {
		setTitle("Cinema\r\n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		JPanel jp_homeAd = new JPanel();
		contentPane.add(jp_homeAd, "name_195303888824900");
		jp_homeAd.setLayout(new BorderLayout(0, 0));
		
		JLabel jl_Home = new JLabel("Trang Chủ");
		jl_Home.setFont(new Font("Tahoma", Font.BOLD, 25));
		jl_Home.setHorizontalAlignment(SwingConstants.CENTER);
		jp_homeAd.add(jl_Home, BorderLayout.NORTH);
		
		JPanel jp_center = new JPanel();
		jp_homeAd.add(jp_center, BorderLayout.CENTER);
		jp_center.setLayout(null);
		
		homeAdminController adctl = new homeAdminController(this);
		
//		functionAdminView functionAdminView = new functionAdminView();
		
		jp_Film = new JPanel();
		jp_Film.addMouseListener(adctl);
		jp_Film.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		jp_Film.setBounds(50, 80, 160, 160);
		jp_center.add(jp_Film);
		jp_Film.setLayout(null);
		
		JLabel jl_ImgFilm = new JLabel("");
		jl_ImgFilm.setHorizontalAlignment(SwingConstants.CENTER);
		jl_ImgFilm.setIcon(new ImageIcon("E:\\T03\\quanLyPhim\\quanLyPhim\\src\\view\\film.png"));
		jl_ImgFilm.setBounds(30, 10, 100, 100);
		jp_Film.add(jl_ImgFilm);
		
		JLabel lblNewLabel = new JLabel("Quản Lý Phim");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 120, 140, 30);
		jp_Film.add(lblNewLabel);
		
		jp_Staff = new JPanel();
		jp_Staff.addMouseListener(adctl);
		jp_Staff.setLayout(null);
		jp_Staff.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		jp_Staff.setBounds(286, 80, 160, 160);
		jp_center.add(jp_Staff);
		
		JLabel jl_ImgStaff = new JLabel("");
		jl_ImgStaff.setIcon(new ImageIcon("E:\\T03\\quanLyPhim\\quanLyPhim\\src\\view\\staff.png"));
		jl_ImgStaff.setHorizontalAlignment(SwingConstants.CENTER);
		jl_ImgStaff.setBounds(30, 10, 100, 100);
		jp_Staff.add(jl_ImgStaff);
		
		JLabel lblQunLNhn = new JLabel("Nhân Viên");
		lblQunLNhn.setHorizontalAlignment(SwingConstants.CENTER);
		lblQunLNhn.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblQunLNhn.setBounds(10, 120, 140, 30);
		jp_Staff.add(lblQunLNhn);
		
	}
}
