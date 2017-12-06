package handler;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import enumConstants.Checkers;
import model.Player;
import model.Square;
import view.BoardPanel;

/**
 * Client Application -> Controller
 * @author Keerthikan
 * 
 * ClientApp
 */
public class Controller implements Runnable {
	private /*@ spec_public nullable @*/ boolean continueToPlay;
	private /*@ spec_public nullable @*/ boolean waitingForAction;
	private /*@ spec_public nullable @*/ boolean isOver;
	
	//Network
	private /*@ spec_public @*/ DataInputStream fromServer;
	private /*@ spec_public @*/ DataOutputStream toServer;
	
	private /*@ spec_public nullable @*/ BoardPanel boardPanel;
	private /*@ spec_public @*/ Player player;
	
	//Data
	private /*@ spec_public @*/ LinkedList<Square> selectedSquares;
	private /*@ spec_public @*/ LinkedList<Square> playableSquares;
	//private LinkedList<Square> crossableSquares;
	
	//@ public initially this.selectedSquares.size() == 0 && this.playableSquares.size() == 0;
	
	//@ assignable this.player, this.fromServer, this.toServer, this.selectedSquares, this.playableSquares;
	public Controller(model.Player player, DataInputStream input, DataOutputStream output){
		this.player = player;
		this.fromServer = input;
		this.toServer= output;
		
		selectedSquares = new LinkedList<Square>();
		playableSquares = new LinkedList<Square>();
	}
	
	/*@ assignable this.boardPanel;
	  @ ensures this.boardPanel == panel;
	  @*/
	public void setBoardPanel(BoardPanel panel){
		this.boardPanel = panel;
	}
	
	@Override
	public void run() {
		continueToPlay = true;
		waitingForAction = true;
		isOver=false;
		
		try {
			
			//Player One
			if(player.getPlayerID()==Checkers.PLAYER_ONE.getValue()){
				//wait for the notification to start
				fromServer.readInt();
				player.setMyTurn(true);
			}
					
			while(continueToPlay && !isOver){
				if(player.getPlayerID()==Checkers.PLAYER_ONE.getValue()){
					waitForPlayerAction();
					if(!isOver)
						receiveInfoFromServer();
				}else if(player.getPlayerID()==Checkers.PLAYER_TWO.getValue()){
					receiveInfoFromServer();
					if(!isOver)
						waitForPlayerAction();
				}
			}
			
			if(isOver){
				JOptionPane.showMessageDialog(null, "Game is over",
						"Information", JOptionPane.INFORMATION_MESSAGE, null);
				System.exit(0);
			}
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Connection lost",
					"Error", JOptionPane.ERROR_MESSAGE, null);
			System.exit(0);
		} catch (InterruptedException e) {
			JOptionPane.showMessageDialog(null, "Connection interrupted",
					"Error", JOptionPane.ERROR_MESSAGE, null);
		}			
	}
	
	/*@ public normal_behavior
	  @		assignable this.isOver, this.continueToPlay, this.player;
	  @		ensures this.isOver == true || this.isOver == false;
	  @		ensures this.continueToPlay == true || this.continueToPlay == false; 
	  @ also
	  @ 	public exceptional_behavior
	  @			requires this.fromServer == null;
	  @			assignable this.isOver, this.continueToPlay, this.player;
	  @			signals_only IOException;
	  @				
	  @*/
	private /*@ spec_public @*/ void receiveInfoFromServer() throws IOException {
		player.setMyTurn(false);
		int from = fromServer.readInt();
		if(from==Checkers.YOU_LOSE.getValue()){
			from = fromServer.readInt();
			int to = fromServer.readInt();
			updateReceivedInfo(from, to);
			isOver=true;
		}else if(from==Checkers.YOU_WIN.getValue()){
			isOver=true;
			continueToPlay=false;
		}else{
			int to = fromServer.readInt();
			updateReceivedInfo(from, to);
		}
	}	
	
	/*@ public normal_behavior
	  @		requires from != null && to != null; 
	  @ 	assignable toServer;
	  @ also
	  @ 	public exceptional_behavior
	  @			requires from == null || to == null;
	  @			assignable toServer;
	  @			signals_only IOException;
	  @				
	  @*/
	private /*@ spec_public @*/ void sendMove(Square from, Square to) throws IOException {
		toServer.writeInt(from.getSquareID());
		toServer.writeInt(to.getSquareID());
	}
	
	/*@ assignable this.waitingForAction;
	  @ ensures this.waitingForAction == true;
	  @*/
	private void waitForPlayerAction() throws InterruptedException {
		player.setMyTurn(true);
		while(waitingForAction){
			Thread.sleep(100);
		}
		waitingForAction = true;		
	}
	
	/*@ requires from != null && to != null;
	  @ assignable waitingForAction;
	  @ ensures this.waitingForAction == false;
	  @*/
	public void move(Square from, Square to){
		to.setPlayerID(from.getPlayerID());
		from.setPlayerID(Checkers.EMPTY_SQUARE.getValue());
		checkCrossJump(from, to);
		checkKing(from, to);
		squareDeselected();
		
		waitingForAction = false;
		try {
			sendMove(from, to);
		} catch (IOException e) {
			System.out.println("move(): Sending failed");
		}		
	}
	
	//When a square is selected
	//@ requires s != null;
	public void squareSelected(Square s) {
		if(selectedSquares.isEmpty()){
			addToSelected(s);
		}		
		//if one is already selected, check if it is possible move
		else if(selectedSquares.size()>=1){
			if(playableSquares.contains(s)){
				move(selectedSquares.getFirst(),s);
			}else{
				squareDeselected();
				addToSelected(s);
			}
		}
	}
	
	/*@ requires s != null;
	  @ assignable selectedSquares;
	  @ ensures this.selectedSquares.size() == \old(this.selectedSquares.size()) + 1;
	  @*/
	private void addToSelected(Square s){
		s.setSelected(true);
		selectedSquares.add(s);
		getPlayableSquares(s);
	}

	/*@ assignable this.selectedSquares, this.playableSquares;
	  @ ensures this.selectedSquares.size() == 0;
	  @ ensures this.playableSquares.size() == 0;
	  @*/
	public void squareDeselected() {
		
		for(Square square:selectedSquares)
			square.setSelected(false);
		
		selectedSquares.clear();
		
		for(Square square:playableSquares){
			square.setPossibleToMove(false);
		}
		
		playableSquares.clear();
		boardPanel.repaintPanels();
	}
	
	/*@ requires s != null;
	  @	assignable this.selectedSquares;
	  @*/
	private void getPlayableSquares(Square s){
		playableSquares.clear();		
		playableSquares = boardPanel.getPlayableSquares(s);
		
		for(Square square:playableSquares){
			System.out.println(square.getSquareID());
		}		
		boardPanel.repaintPanels();
	}
	
	//@ ensures \result == this.player.isMyTurn();
	public boolean isHisTurn(){
		return player.isMyTurn();
	}
	
	//@ requires from != null && to != null;
	private void checkCrossJump(Square from, Square to){		
		if(Math.abs(from.getSquareRow()-to.getSquareRow())==2){		
			int middleRow = (from.getSquareRow() + to.getSquareRow())/2;
			int middleCol = (from.getSquareCol() + to.getSquareCol())/2;
			
			Square middleSquare = boardPanel.getSquare((middleRow*8)+middleCol+1);
			middleSquare.setPlayerID(Checkers.EMPTY_SQUARE.getValue());
			middleSquare.removeKing();
		}
	}
	
	//@ requires from != null && movedSquare != null;
	private void checkKing(Square from, Square movedSquare){		
		if(from.isKing()){
			movedSquare.setKing();
			from.removeKing();
		}else if(movedSquare.getSquareRow()==7 && movedSquare.getPlayerID()==Checkers.PLAYER_ONE.getValue()){
			movedSquare.setKing();
		}else if(movedSquare.getSquareRow()==0 && movedSquare.getPlayerID()==Checkers.PLAYER_TWO.getValue()){
			movedSquare.setKing();
		}
	}
	
	//@ requires from >= 0 && to >= 0;
	private void updateReceivedInfo(int from, int to){
		Square fromSquare = boardPanel.getSquare(from);
		Square toSquare = boardPanel.getSquare(to);
		toSquare.setPlayerID(fromSquare.getPlayerID());
		fromSquare.setPlayerID(Checkers.EMPTY_SQUARE.getValue());
		checkCrossJump(fromSquare, toSquare);
		checkKing(fromSquare, toSquare);
		boardPanel.repaintPanels();
	}
}
