package com.eshop.domain;

public class UserDetails {
	private int userId;
	private int pin;
	private Cart cartAdd;

	public UserDetails() {
	}

	public UserDetails(int userId, int pin, Cart cartAdd) {
		this.userId = userId;
		this.pin = pin;
		this.cartAdd = cartAdd;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public Cart getCartAdd() {
		return cartAdd;
	}

	public void setCartAdd(Cart cartAdd) {
		this.cartAdd = cartAdd;
	}

	@Override
	public String toString() {
		return "UserDetails [userId=" + userId + ", pin=" + pin + ", cartAdd=" + cartAdd + "]";
	}
}
