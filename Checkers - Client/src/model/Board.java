package model;
import java.util.LinkedList;

import enumConstants.Checkers;

/**
 * Client Application -> Board
 * @author  Siyar
 * 
 * Board Model
 */
public class Board {
	
	private /*@ spec_public nullable @*/ Square[][] squares;
	
	public Board(){
		squares = new Square[8][8];
		
		setSquares();
		assignPlayerIDs();
		//printSquareDetails();
	}
	
	/*@
	 @ public invariant this.squares.length == 8;
	 @ public invariant this.squares[1].length == 8;
	 @*/
	
	 /*@
	 @ ensures (\forall int i, j;
	 @ 			0 <= i && i < squares.length && 0 <= j && j < squares.length && ((i%2 == 0 && j%2 == 1)||(i%2 == 1 && j%2 == 0));
	 @ 			squares[i][j].getIsFilled() == true);
	 @
	 @ ensures (\forall int i, j;
	 @ 			0 <= i && i < squares.length && 0 <= j && j < squares.length && ((i%2 == 0 && j%2 == 0)||(i%2 == 1 && j%2 == 1));
	 @ 			squares[i][j].getIsFilled() == false);
	 @*/
	private void setSquares() {
		boolean rowInitialFilled, isFilled;
		int count = 0;
		
		//Rows
		for(int r=0;r<Checkers.NUM_ROWS.getValue();r++){
			rowInitialFilled = (r%2 == 1) ? true : false;
			
			//Columns
			for(int c=0;c<Checkers.NUM_COLS.getValue();c++){
				isFilled = (rowInitialFilled && c%2 == 0) ? true : (!rowInitialFilled && c%2 == 1) ? true : false;
				count++;
				
				squares[r][c] = new Square(count, r, c, isFilled);
//				System.out.println(i+" ---- " + rowInitialFilled + " + "+ k + " ---"+isFilled);
			}
		}		
	}
	
	//@ ensures \result == this.squares;
	public Square[][] getSquares(){
		return this.squares;
	}
	
	//@ ensures \result == this.squares.length;
	public int getTotlaSquares(){
		return squares.length;
	}
	
	public void printSquareDetails(){
		for(int r=0;r<Checkers.NUM_ROWS.getValue();r++){
			for(int c=0;c<Checkers.NUM_COLS.getValue();c++){
				/*System.out.println(squares[r][c].getSquareID() + "--" + squares[r][c].getSquareRow() + "--" + squares[r][c].getSquareCol()
						+ squares[r][c].getPlayerID());*/
				System.out.println(squares[r][c].getSquareID() + " --"+ squares[r][c].isPossibleToMove());
			}
		}
	}
	
	/*@
	 @ requires (\forall int i, j;
	 @			0 <= i && i < this.squares.length && 0 <= j && j < this.squares.length;
	 @ 			this.squares[i][j].getPlayerID() == 0);//talvez n funcione
	 @ 
	 @ ensures (\forall int i, j;
	 @ 			0 <= i && i < 3 && 0 <= j && j < this.squares.length && this.squares[i][j].getIsFilled();
	 @ 			this.squares[i][j].getPlayerID() == Checkers.PLAYER_ONE.getValue());
	 @
	 @ ensures (\forall int i, j;
	 @ 			this.squares.length - 3 <= i && i < this.squares.length && 0 <= j && j < this.squares.length && this.squares[i][j].getIsFilled();
	 @ 			this.squares[i][j].getPlayerID() == Checkers.PLAYER_TWO.getValue());
	 @*/
	private void assignPlayerIDs() {
		
		//Rows 0-2 for player ONE
		for(int r=0;r<3;r++){					
			//Columns
			for(int c=0;c<Checkers.NUM_COLS.getValue();c++){
				if(squares[r][c].getIsFilled()){
					squares[r][c].setPlayerID(Checkers.PLAYER_ONE.getValue());
				}
			}
		}
		
		//Rows 5-7 for player TWO
		for(int r=5;r<8;r++){					
			//Columns
			for(int c=0;c<Checkers.NUM_COLS.getValue();c++){
				if(squares[r][c].getIsFilled()){
					squares[r][c].setPlayerID(Checkers.PLAYER_TWO.getValue());
				}
			}
		}
	}
	
	/*@
	 @ requires selectedSquare.getPlayerID() == 1 || selectedSquare.getPlayerID() == 2;
	 @ requires selectedSquare != null;
	 @ requires selectedSquare.getSquareRow() >= 0 && selectedSquare.getSquareRow() < squares.length;
	 @ ensures \result.size() >= 0 && \result.size() <= 2;
	 @ ensures (\forall int i; i >= 0 && i < \result.size(); \result.get(i) instanceof Square);
	 @ ensures (\forall int i; i >= 0 && i < \result.size(); ((Square)\result.get(i)).isPossibleToMove());
	 @*/
	public LinkedList<Square> findPlayableSquares(Square selectedSquare){
		
		LinkedList<Square> playableSquares = new LinkedList<Square>();
		
		int selectedRow = selectedSquare.getSquareRow();
		int selectedCol = selectedSquare.getSquareCol();
		
		int movableRow = (selectedSquare.getPlayerID()==1) ? selectedRow+1 : selectedRow-1;
		
		//check two front squares
		twoFrontSquares(playableSquares, movableRow, selectedCol);
		crossJumpFront(playableSquares, (selectedSquare.getPlayerID()==1) ? movableRow+1 : movableRow-1, selectedCol, movableRow);
		if(selectedSquare.isKing()){
			movableRow = (selectedSquare.getPlayerID()==1) ? selectedRow-1 : selectedRow+1;
			twoFrontSquares(playableSquares, movableRow , selectedCol);
			crossJumpFront(playableSquares, (selectedSquare.getPlayerID()==1) ? movableRow-1 : movableRow+1, selectedCol, movableRow);
		}
		return playableSquares;		
	}
	
	//check two front squares
	/*@
	 @ requires movableRow >= 0 && movableRow <= squares.length;
	 @ requires selectedCol >= 0 && selectedCol < squares.length;
	 @ ensures pack.size() >= 0 && pack.size() <= 2;
	 @ ensures (\forall int i; i >= 0 && i < pack.size(); pack.get(i) instanceof Square);
	 @ ensures (\forall int i; i >= 0 && i < pack.size(); ((Square) pack.get(i)).isPossibleToMove());
	 @*/
	private void twoFrontSquares(LinkedList<Square> pack, int movableRow, int selectedCol){
		
		if(movableRow>=0 && movableRow<8){
			//right Corner
			if(selectedCol>=0 && selectedCol<7){
				Square rightCorner = squares[movableRow][selectedCol+1];
				if(rightCorner.getPlayerID()==0){
					rightCorner.setPossibleToMove(true);
					pack.add(rightCorner);
				}
			}
			
			//left upper corner
			if(selectedCol>0 && selectedCol <=8){
				Square leftCorner = squares[movableRow][selectedCol-1];
				if(leftCorner.getPlayerID()==0){
					leftCorner.setPossibleToMove(true);
					pack.add(leftCorner);
				}
			}
		}
	}
	
	//cross jump - two front
	/*@
	 @ requires movableRow >= 0 && movableRow <= squares.length-1;
	 @ requires selectedCol >= 0 && selectedCol < squares.length;
	 @ requires middleRow >= 0 && middleRow < squares.length;
	 @ ensures pack.size() >= 0 && pack.size() <= 2;
	 @ ensures (\forall int i; i >= \old(pack.size()) && i < pack.size(); pack.get(i) instanceof Square);
	 @ ensures (\forall int i; i >= \old(pack.size()) && i < pack.size(); ((Square) pack.get(i)).isPossibleToMove());
	 @*/
	private void crossJumpFront(LinkedList<Square> pack, int movableRow, int selectedCol, int middleRow){
		
		int middleCol;
		
		if(movableRow>=0 && movableRow<8){
			//right upper Corner
			if(selectedCol>=0 && selectedCol<6){
				Square rightCorner = squares[movableRow][selectedCol+2];				
				middleCol = (selectedCol+selectedCol+2)/2;				
				if(rightCorner.getPlayerID()==0 && isOpponentInbetween(middleRow, middleCol)){
					rightCorner.setPossibleToMove(true);
					pack.add(rightCorner);
				}
			}
			
			//left upper corner
			if(selectedCol>1 && selectedCol <=7){
				Square leftCorner = squares[movableRow][selectedCol-2];
				middleCol = (selectedCol+selectedCol-2)/2;
				if(leftCorner.getPlayerID()==0 && isOpponentInbetween(middleRow, middleCol)){
					leftCorner.setPossibleToMove(true);
					pack.add(leftCorner);
				}
			}
		}
	}
	
	/*@
	 @ requires row != 0 && col != 0;
	 @ ensures \result == true || \result == false;
	 @*/
	private boolean isOpponentInbetween(int row, int col){
		return squares[row][col].isOpponentSquare();
	}
}
