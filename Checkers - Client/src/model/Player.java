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
	
	//@ requires name != null;
	public Player(String name){
		this.name = name;
		
		setMyTurn(false);
	}
	
	//@ requires this.name != null;
	public /*@ pure @*/String getName(){
		return this.name;
	}
	
	public int getPlayerID() {
		return playerID;
	}

	//@ ensures this.playerID == playerID;
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
		SessionVariable.myID.setValue(playerID);
	}

	public boolean isMyTurn() {
		return myTurn;
	}

	//@ ensures this.myTurn == myTurn;
	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}
	

	
	
}
