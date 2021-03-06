package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.sound.midi.ControllerEventListener;
import javax.swing.*;

import enumConstants.SessionVariable;
import handler.MyMouseListener;
import model.Board;
import model.Square;

/**
 * Client Application -> ClientApp
 * @author Keerthikan
 * 
 * ClientApp
 */
public class BoardPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private /*@ spec_public @*/ Dimension panelSize = new Dimension(720,720);
	private /*@ spec_public @*/ Board boardModel;
	private /*@ spec_public @*/ MyMouseListener listener;
	private /*@ spec_public @*/ LinkedList<SquarePanel> panels;
	private /*@ spec_public @*/ Square[][] squares;

	public BoardPanel(MyMouseListener listener){
		setPreferredSize(panelSize);
		setLayout(new GridLayout(8,8));
		
		boardModel = new Board();
		squares = boardModel.getSquares();
		this.listener = listener;		
		panels = new LinkedList<SquarePanel>();		
		
		initializeSquarePanels();
		
		System.out.println(boardModel.getTotlaSquares());		
	}
	
	/*@ assignable panels;
	  @ ensures \old(this.panels.size()) < this.panels.size();
	 */
	private void initializeSquarePanels() {
		for(int i=0;i<8;i++){
			for(int k=0;k<8;k++){
				SquarePanel sPanel = new SquarePanel(squares[i][k]);
				if(sPanel.getSquare().isPossibleToMove() || sPanel.getSquare().getPlayerID()==SessionVariable.myID.getValue()){
					sPanel.addMouseListener(listener);
				}
				panels.add(sPanel);
				add(sPanel);				
			}			
		}
	}
	
	//@ assignable panels;
	public void repaintPanels(){
		for(SquarePanel panel : panels){
			panel.setListner(listener);	
		}
		
		repaint();
	}
	
	//@ requires s != null; 
	public LinkedList<Square> getPlayableSquares(Square s){
		return boardModel.findPlayableSquares(s);		
	}
	
	public Square getSquare(int ID){
		return panels.get(ID-1).getSquare();
	}
}
