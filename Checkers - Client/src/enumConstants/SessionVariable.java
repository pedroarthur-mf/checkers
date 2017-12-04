package enumConstants;

public enum SessionVariable{
	
	myID(3);
	//public static MyMouseListener mouseListener = new MyMouseListener();
	
	private /*@ spec_public nullable @*/ int value;
	
	SessionVariable(int value){
		this.setValue(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
