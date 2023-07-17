package com.eshop.domain;

public class ProductDetails {
	private int id;
	private String product;
	private double price;
	private int quantity;
	private Categories categories;

	@Override
	public String toString() {
		return "ProductDetails [id=" + id + ", product=" + product + ", price=" + price + ", quantity=" + quantity
				+ "]";
	}

	public ProductDetails() {

	}

	public Categories getCategories() {
		return categories;
	}

	public void setCategories(Categories categories) {
		this.categories = categories;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getName() {
		return product;
	}

	public void setName(String name) {
		this.product = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
