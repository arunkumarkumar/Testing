package com.eshop.controller;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.dao.CartDao;
import com.eshop.domain.ApiResponse;
import com.eshop.domain.CartDetails;
import com.eshop.domain.UserDetails;

@RestController
@RequestMapping("/api")
public class CartController {
	@Autowired
	CartDao query;
	Logger logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());

	@PostMapping(value = "/addcart", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse addToCart(@RequestBody CartDetails cartDetails) {
		logger.info("AddToCart API");
		boolean cartCount = query.cartCount(cartDetails);
		ApiResponse apiResponseJson = new ApiResponse();
		try {
			if (cartCount) {
				logger.info("add the products to cart ");
				apiResponseJson.setMessage("add the products to cart");
				apiResponseJson.setHttpStatus(HttpStatus.CREATED.value());
				apiResponseJson.setResponseData(new JSONObject(cartDetails));
				return apiResponseJson;
			} else if (cartCount == false) {
				logger.warn("Product name and Id was wrong");
				apiResponseJson.setMessage("Product name and Id was wrong");
				apiResponseJson.setHttpStatus(HttpStatus.BAD_REQUEST.value());
				return apiResponseJson;
			} else {
				logger.warn("Datas are invalid");
				apiResponseJson.setMessage("Datas are invalid");
				apiResponseJson.setHttpStatus(HttpStatus.BAD_GATEWAY.value());
				return apiResponseJson;
			}
		} catch (Exception e) {
			logger.warn("Exception Occured");
			apiResponseJson.setMessage("Exception Occured");
			apiResponseJson.setHttpStatus(HttpStatus.BAD_GATEWAY.value());
			return apiResponseJson;
		}

	}

	@GetMapping(value = "/viewcart", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse cartView(@RequestBody UserDetails user)  {
		ApiResponse apiResponseJson = new ApiResponse();
		try {
			logger.info("Viewcart API");
			List<CartDetails> viewcart = query.viewCartProduct(user.getUserId());
			if (viewcart == null) {
				logger.warn("CartDetails datas are not found");
				apiResponseJson.setMessage("Not found");
				apiResponseJson.setHttpStatus(HttpStatus.NOT_FOUND.value());
				return apiResponseJson;

			} else {
				logger.info("CartItems are fetched sucessfully");
				apiResponseJson.setMessage("cartdetails fetched");
				apiResponseJson.setHttpStatus(HttpStatus.FOUND.value());
				apiResponseJson.setResponseData(new JSONObject().put("CartItems", viewcart));
				return apiResponseJson;
			}
		} catch (Exception e) {
			logger.warn("Exception Occured");
			apiResponseJson.setMessage("Exception Occured");
			apiResponseJson.setHttpStatus(HttpStatus.BAD_GATEWAY.value());
			return apiResponseJson;
		}
	}

	@DeleteMapping(value = "/cartremove", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse cartRemove(@RequestBody CartDetails cart) throws SQLException {
		ApiResponse apiResponseJson = new ApiResponse();
		try {
			logger.info("RemoveCart Api");
			boolean deletecart = query.removeCart(cart.getCartId(), cart.getProduct().getName());
			if (deletecart) {
				logger.info("cart product is removed");
				apiResponseJson.setMessage("cart product is removed");
				apiResponseJson.setHttpStatus(HttpStatus.FOUND.value());
				apiResponseJson.setResponseData(new JSONObject().put("cartremove", deletecart));
				return apiResponseJson;
			} else {
				logger.info("cart product is not removed");
				apiResponseJson.setMessage("cart remove method is failed");
				apiResponseJson.setHttpStatus(HttpStatus.BAD_REQUEST.value());
				return apiResponseJson;
			}
		} catch (Exception e) {
			logger.warn("Exception Occured");
			apiResponseJson.setMessage("Exception Occured");
			apiResponseJson.setHttpStatus(HttpStatus.BAD_GATEWAY.value());
			return apiResponseJson;
		}

	}
}
