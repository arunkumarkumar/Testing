package com.eshop.controller;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.dao.CategoryDao;
import com.eshop.domain.ApiResponse;
import com.eshop.domain.Categories;

@RestController
@RequestMapping("/api")
public class CategoryController {
	@Autowired
	CategoryDao query;
	Logger logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());

	@GetMapping("/category")
	public ApiResponse categoryDetails() {
		logger.info("Category API");
		ApiResponse apiResponseJson = new ApiResponse();
		try {
			List<Categories> categoryDetails = query.category();
			if (categoryDetails == null) {
				logger.warn("Categorydetails are null");
				apiResponseJson.setMessage("Categorydetails are null");
				apiResponseJson.setHttpStatus(HttpStatus.BAD_REQUEST.value());
				return apiResponseJson;
			} else {
				logger.info("List of Categories");
				apiResponseJson.setMessage("List of Categories");
				apiResponseJson.setHttpStatus(HttpStatus.OK.value());
				apiResponseJson.setResponseData(new JSONObject().put("List of Categories", categoryDetails));
				return apiResponseJson;
			}
		} catch (Exception e) {
			logger.info("Exception Occured");
			apiResponseJson.setMessage("Exception Occured");
			apiResponseJson.setHttpStatus(HttpStatus.BAD_GATEWAY.value());
			return apiResponseJson;
		}
	}

}
