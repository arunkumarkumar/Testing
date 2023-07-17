package com.eshop.domain;

public class BillingItems {
	private Billing billId;
	private ProductDetails product;
	private int quantity;
	private double price;

	public BillingItems(Billing billId, int quantity,double price,ProductDetails product) {
		this.billId = billId;
		this.product = product;
		this.quantity=quantity;
		this.price=price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Billing getBillId() {
		return billId;
	}
	public void setBillId(Billing billId) {
		this.billId = billId;
	}
	public ProductDetails getProduct() {
		return product;
	}

	public void setProduct(ProductDetails product) {
		this.product = product;
	}

	
	
}
