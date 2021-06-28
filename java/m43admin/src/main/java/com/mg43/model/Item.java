package com.mg43.model;

public class Item {
	private String item;
	private String amount;

	public Item(String item, String amount) {
		this.item = item;
		this.amount = amount;
	}

	public String getItem() {
		return item;
	}

	public String getAmount() {
		return amount;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

}