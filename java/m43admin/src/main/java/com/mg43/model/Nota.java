package com.mg43.model;

import java.util.ArrayList;

public class Nota {
	private ArrayList<String> notes;
	private ArrayList<Item> items;
	private String total;

	public Nota() {
		notes = new ArrayList<String>();
		items = new ArrayList<Item>();
		total = "0";
	}

	public ArrayList<String> getNotes() {
		return notes;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public String getTotal() {
		return total;
	}

	public void setNotes(ArrayList<String> notes) {
		this.notes = notes;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public void setTotal(String total) {
		this.total = total;
	}
}
