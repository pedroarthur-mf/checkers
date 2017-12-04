package main;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {

	private static /*@ spec_public nullable @*/ PropertyManager INSTANCE = null;
	private /*@ spec_public nullable @*/ Properties prop;
	
	private PropertyManager() throws IOException{
		prop = new Properties();
		InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties");
		System.out.println(is);
		
		if(is != null){
			prop.load(is);
		}else{
			System.out.println(is);
			throw new FileNotFoundException("Property file is not found");
		}
	}
	
	public static PropertyManager getInstance() throws IOException{
		if(INSTANCE == null){
			INSTANCE = new PropertyManager();
		}
		return INSTANCE;
	}
	
	public int getPort(){
		return Integer.parseInt(prop.getProperty("port"));
	}
	
}
