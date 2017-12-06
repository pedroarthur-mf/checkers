package model;

import enumConstants.SessionVariable;

/**
 * Client Application -> Player
 * @author  Siyar
 * 
 * Board Model
 */
public class Player {
	
	private /*@ spec_public @*/ String name;
	private /*@ spec_public @*/ int playerID;
	private /*@ spec_public @*/ boolean myTurn;
	
	//@ public invariant this.name != null;
	
	//@ public initially this.myTurn == false;
	
	//@ assignable this.name, this.myTurn;
	public Player(String name){
		this.name = name;
		
		setMyTurn(false);
	}
	
	//@ ensures \result == this.name;
	public /*@ pure @*/String getName(){
		return this.name;
	}
	
	//@ ensures \result == this.playerID;
	public int getPlayerID() {
		return playerID;
	}

	/*@ assignable this.playerID;
	  @ ensures this.playerID == playerID;
	  @*/
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
		SessionVariable.myID.setValue(playerID);
	}

	//@ ensures \result == this.myTurn;
	public /*@ pure @*/ boolean isMyTurn() {
		return myTurn;
	}

	/*@ requires myTurn == false || myTurn == true;
	  @ assignable this.myTurn;
	  @ ensures this.myTurn == myTurn;
	  @*/
	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}
	

	
	
}
