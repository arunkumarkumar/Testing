package com.eshop.domain;

import java.util.List;

public class Categories {
	private int categoryId;
	private String categories;
	private List<ProductDetails> listofproducts;
	
	public Categories(int categoryId, String categories) {
		super();
		this.categoryId = categoryId;
		this.categories = categories;
	}
	public Categories() {
		
	}
	
	@Override
	public String toString() {
		return "Categories [categoryId=" + categoryId + ", categories=" + categories + "]";
	}
	public List<ProductDetails> getListofproducts() {
		return listofproducts;
	}
	public void setListofproducts(List<ProductDetails> listofproducts) {
		this.listofproducts = listofproducts;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategories() {
		return categories;
	}
	public void setCategories(String categories) {
		this.categories = categories;
	}	
}
