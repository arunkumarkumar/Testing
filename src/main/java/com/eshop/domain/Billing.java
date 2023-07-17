package com.eshop.domain;

import java.util.List;

public class Billing {
	private int billId;
	private UserDetails user;
	private List<BillingItems> billItem;
	public List<BillingItems> getBillItem() {
		return billItem;
	}
	public void setBillItem(List<BillingItems> billItem) {
		this.billItem = billItem;
	}
	public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
	public UserDetails getUser() {
		return user;
	}
	public void setUser(UserDetails user) {
		this.user = user;
	
	}
	
	
}
