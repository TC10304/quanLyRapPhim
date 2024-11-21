package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.bookTicketModel;
import view.ticketInformationView;
import view.bookTicketView;
import view.userView;


public class userController implements DocumentListener, ActionListener{
	private userView userView;
	private ticketInformationView thongTinVeView;
	private bookTicketView ticketView;
	private bookTicketModel bookTicketModel;
	
	public userController(view.userView userView) {
		this.userView = userView;
		thongTinVeView = new ticketInformationView();
		bookTicketModel = new bookTicketModel();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		userView.userTimKiem();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		userView.userTimKiem();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		userView.userTimKiem();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		if(str.equals("Thoát")) {
			userView.dispose();
		} else if(str.equals("Thông Tin Vé")) {
			thongTinVeView.setVisible(true);
			thongTinVeView.setLocationRelativeTo(null);
		} else if(str.equals("Đặt Vé")) {
			int selectedRow = userView.table.getSelectedRow();
			if(selectedRow==-1) {
				JOptionPane.showMessageDialog(userView, "Bạn chưa chọn phim");
			} else {
				ticketView = new bookTicketView(userView);
				ticketView.setVisible(true);
				ticketView.setLocationRelativeTo(null);
			}
			
			model.bookTicketModel.addTicket(bookTicketModel.tongTien, ticketView.jl_TenPhim.getText());
		}
	}
}
