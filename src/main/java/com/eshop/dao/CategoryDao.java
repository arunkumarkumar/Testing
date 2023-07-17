package com.eshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.eshop.domain.Categories;
import com.eshop.util.DBconnection;

@Configuration
public class CategoryDao {
	@Autowired
	DBconnection connect;
	Logger logger = LogManager.getLogger(this.getClass());
	public List<Categories> category() {
		List<Categories> categoriesList = new ArrayList<Categories>();
		try {
			String query = "exec categoryDetails";
			Connection userData = connect.connection();
			PreparedStatement preparestatement = userData.prepareStatement(query);
			ResultSet rs = preparestatement.executeQuery();
			while (rs.next()) {
				Categories categories = new Categories();
				categories.setCategories(rs.getString("Categories"));
				categories.setCategoryId(rs.getInt("CategoryId"));
				categoriesList.add(categories);
			}
			logger.info("data retrieved Successfully");
			if (categoriesList.size() > 0) {
				logger.info("ProductList has values");
				return categoriesList;
			} else {
				logger.info("ProductList is empty");
				return null;
			}
		} catch (Exception e) {
			logger.fatal("SQLException : " + e.getLocalizedMessage());
			return null;
		}

	}

}
