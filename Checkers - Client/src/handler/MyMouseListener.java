package handler;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.border.Border;

import model.Square;
import view.SquarePanel;

public class MyMouseListener extends MouseAdapter{
	
	private /*@ nullable @*/ SquarePanel squarePanel;
	private /*@ nullable @*/ Controller controller;
	
	public void setController(Controller c){
		this.controller = c;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		
		
		try{			
			if(controller.isHisTurn()){
				ToggleSelectPiece(e);
			}else{
				JOptionPane.showMessageDialog(null, "Not your turn",
					"Error", JOptionPane.ERROR_MESSAGE, null);
			}
		}catch(Exception ex){
			System.out.println("mousePressed: Error");
			ex.printStackTrace();
		}	
		
		
	}
	
	//@ assignable squarePanel;
	private void ToggleSelectPiece(MouseEvent e){
		System.out.println("ToggleSelectPiece");
//		try{
			squarePanel = (SquarePanel) e.getSource();
			Square s = squarePanel.getSquare();
			
			//if square is already selected - deselect
			if(s.isSelected()){
				System.out.println("Square is already selected");
				System.out.println("deselect - "+s.getSquareID());
				controller.squareDeselected();				
			}
			//else select
			else{
				System.out.println("Is not selected");
				System.out.println("select - "+s.getSquareID());
				controller.squareSelected(s);
			}
//		}catch(Exception ex){
//			System.out.println("ToggleSelectPiece: error");
//		}
	}
}
