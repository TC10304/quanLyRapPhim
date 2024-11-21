package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.QRView;
import view.checkBillView;

public class checkBillConTroller implements MouseListener, ActionListener {
	private checkBillView checkBill;
	private QRView qrView;

	public checkBillConTroller(view.checkBillView checkBill) {
		this.checkBill = checkBill;
		this.qrView = new QRView();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		if (str.equals("Thanh Toán")) {
			qrView.setVisible(true);
			qrView.setLocationRelativeTo(null);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		checkBill.dispose();
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
