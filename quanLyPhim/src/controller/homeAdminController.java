package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.functionAdminView;
import view.homeAdminView;

public class homeAdminController implements MouseListener{
	private homeAdminView homeAdminView;
	private functionAdminView functionAdminView;
	
	

	public homeAdminController(view.homeAdminView homeAdminView) {
		this.homeAdminView = homeAdminView;
		this.functionAdminView = new  functionAdminView();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == homeAdminView.jp_Film) {
//			this.homeAdminView.setVisible(false);
			homeAdminView.setVisible(false);
			functionAdminView.setVisible(true);
			functionAdminView.cardLayout.show(functionAdminView.contentPane, "Film");
		} else if(e.getSource() == homeAdminView.jp_Staff) {
			homeAdminView.setVisible(false);
			functionAdminView.setVisible(true);
			functionAdminView.cardLayout.show(functionAdminView.contentPane, "Staff");
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
