package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class timKiemPhim extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTable table;
	public DefaultTableModel tableModel;
	public model.adminModel adminModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					timKiemPhim frame = new timKiemPhim();
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
	public timKiemPhim() {
	    adminModel = new model.adminModel();

	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(100, 100, 800, 250);
	    contentPane = new JPanel();
	    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    
	    setLocationRelativeTo(null);

	    setContentPane(contentPane);
	    contentPane.setLayout(null);
	    
	    JLabel lblNewLabel = new JLabel("Danh sách phim bạn muốn tìm");
	    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
	    lblNewLabel.setBounds(198, 10, 389, 39);
	    contentPane.add(lblNewLabel);
	    
	    // Lấy dữ liệu từ adminModel
	    List<String[]> data = model.adminModel.thongTinPhim();
	    String[] columnNames = {"Tên phim", "Thể loại", "Phòng chiếu", "Thời lượng", "Giờ chiếu"}; 
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

	    JScrollPane scrollPane = new JScrollPane(table);
	    scrollPane.setBounds(0, 60, 786, 99);
	    contentPane.add(scrollPane);
	    
	    JButton btnNewButton = new JButton("OK");
	    btnNewButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            dispose();  
	        }
	    });
	    btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
	    btnNewButton.setBounds(325, 169, 140, 39);
	    contentPane.add(btnNewButton);
	}
}
