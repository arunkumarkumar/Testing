package com.eshop.domain;
import java.util.List;

public class Cart {
	private UserDetails userId;
	private List<CartDetails> cartDetail;
	private int cartId;
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	public UserDetails getId() {
		return userId;
	}
	public void setId(UserDetails id) {
		this.userId = id;
	}
	public List<CartDetails> getCartDetail() {
		return cartDetail;
	}

	public void setCartDetail(List<CartDetails> cartDetail) {
		this.cartDetail = cartDetail;
		
	}
	
}
