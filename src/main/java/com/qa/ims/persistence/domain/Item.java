package com.qa.ims.persistence.domain;

import java.util.Objects;

public class Item {
	private Long id;
	private String itemName;
	protected Double price;

	public Item(String itemName, Double price) {
		this.setItemName(itemName);
		this.setPrice(price);
	}

	public Item(Long id, String itemName, Double price) {
		this.setId(id);
		this.setItemName(itemName);
		this.setPrice(price);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "[item id: " + id + ", item name: " + itemName + ", price: £" + price + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, itemName, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(id, other.id) && Objects.equals(itemName, other.itemName)
				&& Objects.equals(price, other.price);
	}



}


