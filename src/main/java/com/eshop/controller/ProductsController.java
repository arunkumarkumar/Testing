package com.eshop.controller;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.dao.ProductsDao;
import com.eshop.domain.ApiResponse;
import com.eshop.domain.Categories;
import com.eshop.domain.ProductDetails;
@Configuration
@RestController
@RequestMapping("/api")
public class ProductsController {
	@Autowired
	ProductsDao query;
	Logger logger=org.apache.logging.log4j.LogManager.getLogger(this.getClass());
	@GetMapping("/listofproducts")
	public ApiResponse categoryChose(@RequestParam (value ="categories") String choicecategory){
		logger.info("ListOfproducts API");
		ApiResponse apiResponseJson=new ApiResponse();
		try {
		List<ProductDetails> productDetails = query.products(choicecategory);
		if(productDetails==null ) {
			logger.warn("Listofproducts is null");
			apiResponseJson.setMessage("Listofproducts is null");
			apiResponseJson.setHttpStatus(HttpStatus.BAD_REQUEST.value());
			JSONObject jsonObject = new JSONObject();
			apiResponseJson.setResponseData(jsonObject);
			return apiResponseJson;
		}
		else if(productDetails.isEmpty()){
			logger.warn("Listofproducts is empty");
			apiResponseJson.setMessage("Listofproducts is empty");
			apiResponseJson.setHttpStatus(HttpStatus.BAD_REQUEST.value());
			JSONObject jsonObject = new JSONObject();
			apiResponseJson.setResponseData(jsonObject);
			return apiResponseJson;
		}
		else {
			logger.info("List of products");
			apiResponseJson.setMessage("List of products");
			apiResponseJson.setHttpStatus(HttpStatus.OK.value());
			apiResponseJson.setResponseData(new JSONObject().put("List Of products", productDetails));
			Categories cate=new Categories();
			cate.setListofproducts(productDetails);
			return apiResponseJson;
		}	
	}
	catch (Exception e) {
		 logger.warn("Exception Occured");
	     apiResponseJson.setMessage("Exception Occured");
	     apiResponseJson.setHttpStatus(HttpStatus.BAD_GATEWAY.value());
	      return apiResponseJson;
	}
}

}
