package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

import model.bookTicketModel;
import view.bookTicketView;
//import view.checkBillView;
import view.userView;

public class bookTicketController implements MouseListener, ActionListener {
	private bookTicketView bookTicketView;
//	private checkBillView checkBillView;
	private userView userView;
	public static String row;
	public static int seatNumber;
//	public List<String> seat_Nam;
//	private ArrayList<Integer> seatID;

//	public bookTicketController(view.bookTicketView bookTicketView, view.checkBillView checkBillView) {
//		this.bookTicketView = bookTicketView;
//		 this.checkBillView = checkBillView;
//	    this.userView = new userView();
//	}
	
//	public bookTicketController(view.checkBillView checkBillView) {
//		this.checkBillView = checkBillView;
//		this.userView = new userView();
////		seat_Nam = new ArrayList<>();
//	}

	public bookTicketController(view.bookTicketView bookTicketView) {
		this.bookTicketView = bookTicketView;
//		 this.checkBillView = checkBillView;
	    this.userView = new userView();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		bookTicketView.dispose();
		userView.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();

		String buttonText = btn.getText();
		
		row = buttonText.substring(0, 1);
		seatNumber = -1;

		if (buttonText.length() > 1) {
			String seatStr = buttonText.substring(1); // Lấy phần sau ký tự đầu tiên
			try {
				seatNumber = Integer.parseInt(seatStr); // Chuyển chuỗi thành số
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
			}
		}
		
		int tongTien = 0;

		String hallName = bookTicketView.jl_PhongChieu.getText();
		String movieName = bookTicketView.jl_TenPhim.getText(); 
		boolean seatAvailability = bookTicketModel.getSeatAvailability(row, seatNumber, hallName, movieName);
		
		if (seatAvailability) {
			// Khi đặt ghế
			btn.setBackground(Color.CYAN);
			bookTicketModel.updateComplete(row, seatNumber, hallName, movieName);
			
//			checkBillView.seat_Name.add(seatName);
			
//			System.out.println("Ghế đã đặt");
//			for (String tenGhe : checkBillView.seat_Name) {
//				System.out.println(tenGhe);
//			}
			
			bookTicketView.seatName.add(buttonText);
			// lấy ID ghế
//			int ID = bookTicketModel.getSeatID(row, seatNumber);
//			bookTicketView.soGhe.add(ID);
//			for (int  ID1: bookTicketView.soGhe) {
//				System.out.println(ID1);
//			}

			if (row.equals("D") || row.equals("E") || row.equals("F")) {
				bookTicketView.bookTicketModel.gheVip += 1;
			} else {
				bookTicketView.bookTicketModel.gheThuong += 1;
			}
			tongTien = bookTicketView.bookTicketModel.gheVip * bookTicketView.bookTicketModel.giaVeVip
					+ bookTicketView.bookTicketModel.gheThuong * bookTicketView.bookTicketModel.giaVeThuong;
//			
//			bookTicketModel.addTicket(tongTien, bookTicketView.jl_TenPhim.getText());
			bookTicketModel.addTicket_Seat(row, seatNumber);

			bookTicketView.bookTicketModel.setTongTien(tongTien);
			bookTicketView.jl_TongTien.setText(String.valueOf(tongTien));

		} else {
			// Khi hủy đặt ghế
			btn.setBackground(Color.WHITE);
			bookTicketModel.updateCancle(row, seatNumber, hallName,movieName);
			
//			checkBillView.seat_Name.remove(seatName);
//			System.out.println("Ghế sau khi hủy:");
//			for (String tenGhe : checkBillView.seat_Name) {
//				System.out.println(tenGhe);
//			}
			
			bookTicketModel.deleteTicketSeat(bookTicketModel.getSeatID(row, seatNumber));
			
			bookTicketView.seatName.remove(buttonText);
//			Integer seatID = bookTicketModel.getSeatID(row, seatNumber);
//			if (seatID != null && bookTicketView.soGhe.contains(seatID)) {
//			    bookTicketView.soGhe.remove(seatID);
//			}

			if (row.equals("D") || row.equals("E") || row.equals("F")) {
				if (bookTicketView.bookTicketModel.gheVip > 0) {
					bookTicketView.bookTicketModel.gheVip -= 1;
				}
			} else {
				if (bookTicketView.bookTicketModel.gheThuong > 0) {
					bookTicketView.bookTicketModel.gheThuong -= 1;
				}
			}
			tongTien = bookTicketView.bookTicketModel.gheVip * bookTicketView.bookTicketModel.giaVeVip
					+ bookTicketView.bookTicketModel.gheThuong * bookTicketView.bookTicketModel.giaVeThuong;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
