package com.eshop.domain;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
@Service
public class ApiResponse {
	private String message;
	private int HttpStatus;
	private Object ResponseBodyData;
	public ApiResponse(String message, int httpStatus, Object ResponseData) {
		super();
		this.message = message;
		HttpStatus = httpStatus;
		this.ResponseBodyData = ResponseData;
	}
	public ApiResponse() {
		
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getResponseData() {
		return ResponseBodyData;
	}
	public void setResponseData(JSONObject data) {
		this.ResponseBodyData = data.toString();
	}
	@Override
	public String toString() {
		return "ApiResponse [message=" + message + ", HttpStatus=" + HttpStatus + ", ResponseBodyData="
				+ ResponseBodyData + "]";
	}
	public int getHttpStatus() {
		return HttpStatus;
	}
	public void setHttpStatus(int httpStatus) {
		HttpStatus = httpStatus;
	}
	

}
