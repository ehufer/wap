package edu.mum.wap.project.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBProperties {

	Properties prop = new Properties();
	
	public DBProperties(){
		
		InputStream input = null;

		try {
		    input = getClass().getClassLoader().getResourceAsStream("config.properties");
		    prop.load(input);
		} catch (IOException ex) {
		    ex.printStackTrace();
		} finally {
		    if (input != null) {
		        try {
		            input.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}
	}
	
	public String getDatabase(){ 
		return prop.getProperty("database");
	}
	
	public String getDbUser(){ 
		return prop.getProperty("dbuser");
	}
	
	public String getDbPassword(){ 
		return prop.getProperty("dbpassword");
	}
}
