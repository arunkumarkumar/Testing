package com.eshop.controller;

import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.dao.UserandPinDao;
import com.eshop.domain.ApiResponse;
import com.eshop.domain.UserDetails;

@RestController
@RequestMapping("/api")
public class UserContoller {
	@Autowired
	UserandPinDao queries;
	Logger logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());
	@GetMapping("/newuser")
	public ApiResponse addUsers(@RequestParam(value = "userId") int userId, @RequestParam(value = "pin") int pin) {
		logger.info("add new user by userId and pin API");
		boolean newuser;
		ApiResponse apiResponseJson = new ApiResponse();
		try {
			UserDetails user = queries.getUserById(userId);
			if (user == null) {
				logger.info("userdetails are null");
				apiResponseJson.setMessage("UserDetails are null");
				apiResponseJson.setHttpStatus(HttpStatus.BAD_GATEWAY.value());
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("userId", userId);
				apiResponseJson.setResponseData(jsonObject);
				return apiResponseJson;
			} else {
				if (user.getUserId() == userId) {
					logger.info("This id is already taken");
					apiResponseJson.setMessage("This id is already taken");
					apiResponseJson.setHttpStatus(HttpStatus.BAD_REQUEST.value());
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("userId", userId);
					apiResponseJson.setResponseData(jsonObject);
					return apiResponseJson;
				} else if (user.getUserId() != userId) {
					if ((String.valueOf(userId).length()) == 4) {
						newuser = queries.newUser(userId, pin);
						if (newuser == false) {
							logger.warn("Problem in database Connection is null");
							apiResponseJson.setMessage("Problem in database Connection is null");
							apiResponseJson.setHttpStatus(HttpStatus.BAD_REQUEST.value());
							return apiResponseJson;
						} else if (newuser == true) {
							logger.info("User Details Inserted");
							apiResponseJson.setMessage("User Details Inserted ");
							apiResponseJson.setHttpStatus(HttpStatus.CREATED.value());
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("userId", userId);
							jsonObject.put("pin", pin);
							apiResponseJson.setResponseData(jsonObject);
							return apiResponseJson;
						} else {
							logger.warn("Datas are invalid");
							apiResponseJson.setMessage("Datas are invalid");
							apiResponseJson.setHttpStatus(HttpStatus.BAD_REQUEST.value());
							return apiResponseJson;
						}
					} else {
						logger.info("Userid number size is invalid");
						apiResponseJson.setMessage(" this Userid number size is invalid ");
						apiResponseJson.setHttpStatus(HttpStatus.BAD_REQUEST.value());
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("userId", userId);
						apiResponseJson.setResponseData(jsonObject);
						return apiResponseJson;
					}
				} else {
					logger.info("Invalid data and Response");
					apiResponseJson.setMessage("Invalid data and Response ");
					apiResponseJson.setHttpStatus(HttpStatus.EXPECTATION_FAILED.value());
					return apiResponseJson;
				}

			}
		} catch (Exception e) {
			logger.info("Exception Occured");
			apiResponseJson.setMessage("Exception Occured");
			apiResponseJson.setHttpStatus(HttpStatus.BAD_GATEWAY.value());
			return apiResponseJson;
		}
	}

	@GetMapping("/existuser")
	public ApiResponse oldUser(@RequestParam(value = "userId") int userId, @RequestParam(value = "pin") int pin) {
		logger.info("Exist user API");
		ApiResponse apiResponseJson = new ApiResponse();
		try {
			UserDetails user = queries.getUserById(userId);
			if (user == null) {
				logger.warn("userdetails response is null");
				apiResponseJson.setMessage("userdetails response is null");
				apiResponseJson.setHttpStatus(HttpStatus.EXPECTATION_FAILED.value());
				JSONObject jsonObject = new JSONObject();
				apiResponseJson.setResponseData(jsonObject);
				return apiResponseJson;
			} else {
				if (user.getUserId() == userId) {
					if (user.getPin() == pin) {
						logger.info("User Details is Verified");
						apiResponseJson.setMessage("User Details is Verified ");
						apiResponseJson.setHttpStatus(HttpStatus.ACCEPTED.value());
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("userId", userId);
						jsonObject.put("pin", pin);
						apiResponseJson.setResponseData(jsonObject);
						return apiResponseJson;
					} else {
						logger.info("user pin was wrong");
						apiResponseJson.setMessage("user pin was wrong");
						apiResponseJson.setHttpStatus(HttpStatus.NOT_FOUND.value());
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("pin was wrong", pin);
						apiResponseJson.setResponseData(jsonObject);
						return apiResponseJson;
					}
				} else if (user.getUserId() != userId) {
					logger.info("userid was wrong");
					apiResponseJson.setMessage("userid was wrong");
					apiResponseJson.setHttpStatus(HttpStatus.NOT_FOUND.value());
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("userId was wrong", userId);
					apiResponseJson.setResponseData(jsonObject);
					return apiResponseJson;
				} else {
					logger.info("Datas are invalid");
					apiResponseJson.setMessage("Datas are invalid");
					apiResponseJson.setHttpStatus(HttpStatus.BAD_REQUEST.value());
					JSONObject jsonObject = new JSONObject();
					apiResponseJson.setResponseData(jsonObject);
					return apiResponseJson;
				}
			}
		} catch (Exception e) {
			logger.info("Exception Occured");
			apiResponseJson.setMessage("Exception Occured");
			apiResponseJson.setHttpStatus(HttpStatus.BAD_GATEWAY.value());
			return apiResponseJson;
		}
	}

}
