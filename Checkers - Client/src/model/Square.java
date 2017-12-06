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
	
	private /*@ spec_public @*/ int SquareID;
	private /*@ spec_public @*/ int SquareRow;
	private /*@ spec_public @*/ int SquareCol;	
	private /*@ spec_public nullable @*/ boolean isKing;
	private /*@ spec_public @*/ boolean filled;
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
	
	//@ ensures \result == this.filled;
	public /*@ pure @*/ boolean getIsFilled() {
		return filled;
	}
	
	/*@ assignable this.filled;
	  @ ensures this.filled == filled;
	  @*/
	private void setFilled(boolean filled) {
		this.filled = filled;
	}
	
	/*@ assignable this.playerID; 
	  @ ensures this.playerID == ID;
	  @*/
	public void setPlayerID(int ID){
		this.playerID=ID;
	}
	
	//@ ensures \result == this.playerID;
	public /*@ pure @*/ int getPlayerID(){
		return this.playerID;
	}
	
	//@ ensures \result == this.SquareID;
	public int getSquareID(){
		return this.SquareID;
	}
	
	//@ ensures \result == this.SquareRow;
	public /*@ pure @*/ int getSquareRow(){
		return this.SquareRow;
	}
	
	//@ ensures \result == this.SquareCol;
	public /*@ pure @*/ int getSquareCol(){
		return this.SquareCol;
	}
	
	//@ ensures \result == this.selected;
	public /*@ pure @*/ boolean isSelected() {
		return selected;
	}
	
	/*@ assignable this.selected; 
	  @ ensures this.selected == selected;
	  @*/
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	//@ ensures \result == this.isPossibleToMove;
	public /*@ pure @*/ boolean isPossibleToMove() {
		return isPossibleToMove;
	}
	
	/*@ assignable this.isPossibleToMove; 
	  @ ensures this.isPossibleToMove == isPossibleToMove;
	  @*/
	public void setPossibleToMove(boolean isPossibleToMove) {
		this.isPossibleToMove = isPossibleToMove;
	}
	//@ ensures \result == true || \result == false;
	public boolean isOpponentSquare(){
		if(playerID!=SessionVariable.myID.getValue() && playerID!=Checkers.EMPTY_SQUARE.getValue())
			return true;
		else
			return false;
	}

	//@ ensures \result == this.isKing;
	public boolean isKing() {
		return isKing;
	}
	
	/*@ requires this.isKing == false;
	  @ assignable this.isKing;
	  @ ensures this.isKing == true;
	  @*/
	public void setKing() {
		this.isKing = true;
	}
	
	/*@ assignable this.isKing;
	  @ ensures this.isKing == false;
	  @*/
	public void removeKing(){
		this.isKing = false;
	}
}
