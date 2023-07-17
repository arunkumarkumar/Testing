package com.eshop.domain;

public class CartDetails {
	private int cartId;
	private ProductDetails product;
	private int quantity;
	private Cart cartdetails;

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public ProductDetails getProduct() {
		return product;
	}

	public void setProduct(ProductDetails product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Cart getCartdetails() {
		return cartdetails;
	}

	public void setCartdetails(Cart cartdetails) {
		this.cartdetails = cartdetails;
	}

	@Override
	public String toString() {
		return "CartDetails [cartId=" + cartId + ", product=" + product + ", quantity=" + quantity + ", cartdetails="
				+ cartdetails + "]";
	}

}
