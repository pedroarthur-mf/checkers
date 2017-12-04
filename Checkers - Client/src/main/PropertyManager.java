package main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {

	private /*@spec_public nullable@*/ static PropertyManager INSTANCE = null;
	private /*@spec_public nullable@*/ Properties prop;
	
	private PropertyManager() throws IOException{
		prop = new Properties();
		String dir = System.getProperty("user.dir") + "/resources/config.properties";
		//		InputStream is = getClass().getClassLoader().getResourceAsStream(System.getProperty("user.dir") + "/resources/config.properties");
		
		File file = new File(dir);
	    InputStream is = new FileInputStream(file);
	    
		if(is != null){
			prop.load(is);
		}else{
			throw new FileNotFoundException("Property file is not found");
		}
	}
	
	public static PropertyManager getInstance() throws IOException{
		if(INSTANCE == null){
			INSTANCE = new PropertyManager();
		}
		return INSTANCE;
	}
	
	public String getAddress(){
		return prop.getProperty("server");
	}
	
	public int getPort(){
		return Integer.parseInt(prop.getProperty("port"));
	}
	
}
