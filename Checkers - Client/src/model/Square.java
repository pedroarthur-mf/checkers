package model;

import enumConstants.Checkers;
import enumConstants.SessionVariable;

/**
 * Client Application -> ClientApp
 * @author Siyar
 * 
 * ClientApp
 */
public class Square {
	
	private /*@ spec_public nullable @*/ int SquareID;
	private /*@ spec_public nullable @*/ int SquareRow;
	private /*@ spec_public nullable @*/ int SquareCol;	
	private /*@ spec_public nullable @*/ boolean isKing;
	private /*@ spec_public nullable @*/ boolean filled;
	private /*@ spec_public nullable @*/ boolean selected;
	private /*@ spec_public nullable @*/ boolean isPossibleToMove;
	private /*@ spec_public nullable @*/ int playerID;	
	
	//Constructor
	public Square(int SquareID, int SquareRow, int SquareCol, boolean isFilled){
		this.SquareID=SquareID;
		this.SquareRow=SquareRow;
		this.SquareCol=SquareCol;
		this.setFilled(isFilled);
		
		if(this.filled){
			this.playerID = Checkers.EMPTY_SQUARE.getValue();
		}
		
		this.isKing = false;
		this.selected = false;
		this.isPossibleToMove = false;
	}

	public /*@ pure @*/ boolean getIsFilled() {
		return filled;
	}
	
	//@ ensures this.filled == filled;
	private void setFilled(boolean filled) {
		this.filled = filled;
	}
	
	//@ ensures this.playerID == ID;
	public void setPlayerID(int ID){
		this.playerID=ID;
	}
	
	public /*@ pure @*/ int getPlayerID(){
		return this.playerID;
	}
	
	public int getSquareID(){
		return this.SquareID;
	}
	
	public /*@ pure @*/ int getSquareRow(){
		return this.SquareRow;
	}
	
	public /*@ pure @*/ int getSquareCol(){
		return this.SquareCol;
	}

	public boolean isSelected() {
		return selected;
	}
	
	//@ ensures this.selected == selected;
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public /*@ pure @*/ boolean isPossibleToMove() {
		return isPossibleToMove;
	}
	
	//@ ensures this.isPossibleToMove == isPossibleToMove;
	public void setPossibleToMove(boolean isPossibleToMove) {
		this.isPossibleToMove = isPossibleToMove;
	}
	
	public boolean isOpponentSquare(){
		if(playerID!=SessionVariable.myID.getValue() && playerID!=Checkers.EMPTY_SQUARE.getValue())
			return true;
		else
			return false;
	}

	public boolean isKing() {
		return isKing;
	}
	
	/*@ requires this.isKing == false;
	  @ ensures this.isKing == true;
	  @*/
	public void setKing() {
		this.isKing = true;
	}
	
	/*@ ensures this.isKing == false;
	  @*/
	public void removeKing(){
		this.isKing = false;
	}
}
