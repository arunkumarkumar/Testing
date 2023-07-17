package com.eshop.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
@Configuration
public class DBconnection {
	Logger logger = LogManager.getLogger(this.getClass());
	private static BasicDataSource dataSource = null;
	 String Driver=null;
	    String Dburl=null;
	    String username=null;
	    String password=null;
		public BasicDataSource dbConnection(){
			try {
			FileInputStream file = new FileInputStream("C:\\Users\\User\\Documents\\workspace-spring-tool-suite-4-4.17.0.RELEASE\\eshop.zip_expanded\\eshop\\src\\main\\resources\\application.properties");
			Properties property = new Properties();
			property.load(file);
			Driver=property.getProperty("spring.datasource.driver-class-name");
			Dburl=property.getProperty("spring.datasource.url");
			username=property.getProperty("spring.datasource.username");
			password=property.getProperty("spring.datasource.password");
			logger.info("Property File is worked");
			dataSource = new BasicDataSource();
			dataSource.setDriverClassName(Driver);
			dataSource.setUrl(Dburl);
			dataSource.setUsername(username);
			dataSource.setPassword(password);
			dataSource.setMinIdle(5);
			dataSource.setMaxIdle(10);
			dataSource.setMaxActive(25);
		    logger.warn("Database datasource works fine");
				return dataSource;
			}
		catch (Exception e) {
				logger.fatal("SQLException : " + e.getLocalizedMessage());
				return null;
		}
			
		}

	public Connection connection() throws SQLException {
		Connection connection = null;
		try {
			dataSource =new DBconnection().dbConnection();
			connection = dataSource.getConnection();
		} 
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("hello everyone");
		return connection;
	}

}
