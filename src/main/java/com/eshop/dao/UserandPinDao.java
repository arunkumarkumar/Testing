package com.eshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.eshop.domain.UserDetails;
import com.eshop.util.DBconnection;

@Configuration
public class UserandPinDao {
	@Autowired
	DBconnection connect;
	Logger logger = LogManager.getLogger(this.getClass());

	public UserDetails getUserById(int userId) {
		logger.info("Existuser Dao");
		try {
			String query = "exec Sp_userDetail_getBy_Id @UserId=?";
			Connection userData = connect.connection();
			PreparedStatement preparestatement = userData.prepareStatement(query);
			preparestatement.setInt(1, userId);
			ResultSet rs = preparestatement.executeQuery();
			UserDetails userDetails = new UserDetails();
			while (rs.next()) {
				userDetails.setUserId(rs.getInt("UserId"));
				userDetails.setPin(rs.getInt("Pin"));
			}
			logger.info("data retrieved Successfully");
			return userDetails;
		} catch (Exception e) {
			logger.fatal("SQLException : " + e.getLocalizedMessage());
			return null;
		}
	}

	public boolean newUser(int userId, int pin) {
		logger.info("addnewuser DAO");
		try {
			String newUserdata = "exec Sp_userDetail @UserId=?,@Pin=?,@CartId=?";
			Connection userData = connect.connection();
			PreparedStatement preparestatement = userData.prepareStatement(newUserdata);
			preparestatement.setInt(1, userId);
			preparestatement.setInt(2, pin);
			preparestatement.setInt(3, userId);
			if (preparestatement.executeUpdate() > 0) {
				logger.info("data inserted successfully");
				return true;
			} else {
				logger.warn("data insertion failed");
				return false;
			}
		} catch (Exception e) {
			logger.info("SQLException" + e.getLocalizedMessage());
			return false;
		}
	}

}
