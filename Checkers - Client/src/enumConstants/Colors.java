package enumConstants;

import java.awt.Color;

/**
 * Client Application -> Enum Colors
 * @author  Siyar
 * 
 * Colors
 */
public enum Colors {
	BLACK(Color.BLACK),
	WHITE(Color.WHITE),
	RED(Color.RED),
	ORANGE(new Color(255,144,0)),
	PURPLE(new Color(128,100,162)),
	YELLOW(Color.YELLOW);
	
	
	//properties
	private /*@ spec_public nullable @*/ Color color;
	
	//Constructor
	Colors(Color color){
		this.color = color;
	}
	
	//@ ensures \result == this.color;
	public /*@ pure @*/ Color getColor(){
		return this.color;
	}
	
	/*@ public normal_behavior
	  @ requires ID == 1;
	  @ ensures \result == RED.getColor();
	  @ also
	  @ 	public normal_behavior
      @ 		requires ID == 2;
	  @ 		ensures \result == ORANGE.getColor();
	  @ also
	  @ 	public normal_behavior
      @ 		requires ID != 1 || ID != 2;
	  @ 		ensures \result == null;
	  @
	  @*/
	public static /*@ pure @*/ Color getMyDefaultColor(int ID){
		if(ID==1){
			return RED.getColor();
		}
		else if(ID==2){
			return ORANGE.getColor();
		}
		
		return null;
	}
	
	
	/*@ public normal_behavior
	  @ requires ID == 1;
	  @ ensures \result == PURPLE.getColor();
	  @ also
	  @ 	public normal_behavior
      @ 		requires ID == 2;
	  @ 		ensures \result == YELLOW.getColor();
	  @ also
	  @ 	public normal_behavior
      @ 		requires ID != 1 || ID != 2;
	  @ 		ensures \result == null;
	  @
	  @*/
	public /*@ pure @*/ static Color getFocusedColor(int ID){
		if(ID==1){
			return PURPLE.getColor();
		}
		else if(ID==2){
			return YELLOW.getColor();
		}		
		return null;
	}
}
