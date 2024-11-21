package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;

public class ticketInformationView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ticketInformationView frame = new ticketInformationView();
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
	public ticketInformationView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 315);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel jlabel = new JLabel("<html>Thông tin vé<br>"
		        + "Vé thường:<br>"
		        + "&nbsp;&nbsp;&nbsp;&nbsp;- Áp dụng cho các hàng ghế A, B, C, G, H<br>"
		        + "&nbsp;&nbsp;&nbsp;&nbsp;- Giá vé: 60.000 vnđ<br>"
		        + "Vé VIP:<br>"
		        + "&nbsp;&nbsp;&nbsp;&nbsp;- Áp dụng cho các hàng ghế D, E, F<br>"
		        + "&nbsp;&nbsp;&nbsp;&nbsp;- Giá vé: 80.000 vnđ<br>"
		        + "</html>");
		jlabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jlabel.setBackground(new Color(255, 255, 255));
		jlabel.setOpaque(true);
		jlabel.setBounds(10, 10, 370, 190);
		contentPane.add(jlabel);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton.setBounds(130, 220, 140, 40);
		contentPane.add(btnNewButton);
	}
}
