package com.eshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.eshop.domain.Categories;
import com.eshop.domain.ProductDetails;
import com.eshop.util.DBconnection;
import com.fasterxml.jackson.core.io.JsonStringEncoder;

@Configuration
public class ProductsDao {
	@Autowired
	DBconnection connect;
	Logger logger = LogManager.getLogger(this.getClass());

	public List<ProductDetails> products(String userCategory) {
		logger.info("ProductDetails Dao");
		List<ProductDetails> productList = new ArrayList<ProductDetails>();
		try {
			String query = "exec productDetail @Categoriess=?";
			Connection userData = connect.connection();
			PreparedStatement preparestatement = userData.prepareStatement(query);
			preparestatement.setString(1, userCategory);
			ResultSet rs = preparestatement.executeQuery();
			while (rs.next()) {
				ProductDetails product = new ProductDetails();
				Categories categoriess = new Categories();
				categoriess.setCategories(userCategory);
				categoriess.setCategoryId(rs.getInt("CategoryId"));
				product.setId(rs.getInt("ProductId"));
				product.setName(rs.getString("ProductName"));
				product.setPrice(rs.getDouble("ProductPrice"));
				product.setQuantity(rs.getInt("Quantity"));
				product.setCategories(categoriess);
				productList.add(product);
			}
			logger.info("data retrieved Successfully");
			if (productList.size() > 0) {
				logger.warn("ProductList has values");
				return productList;
			} else {
				logger.warn("ProductList is empty");
				return productList;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
