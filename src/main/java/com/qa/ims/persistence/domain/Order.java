package com.qa.ims.persistence.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.qa.ims.persistence.dao.ItemDAO;

public class Order {
	private Long id;
	private Long customerId;
	private Long orderId;
	private String customerForename;
	private String customerSurname;;
	private Long itemId;
	private List<Item> items;
	
	static ItemDAO itemDAO = new ItemDAO();
	
	public static void reconnect() {
		itemDAO = new ItemDAO();
	}
	public Order(Long customerId, Long itemId) {
		this.setCustomerId(customerId);
		this.setItemId(itemId);
		items = new ArrayList<Item>();
		items.add(itemDAO.read(itemId));
		this.setItems(items);
	}

	public Order(Long id, Long customerId, Long itemId) {
		this.setId(id);
		this.setCustomerId(customerId);
		this.setItemId(itemId);
	}

	public Order(Long id, Long orderId, Long customerId, String customerForename, String customerSurname,
			List<Item> items) {
		this.id = id;
		this.customerId = customerId;
		this.orderId = orderId;
		this.items = items;
		this.customerForename = customerForename;
		this.customerSurname = customerSurname;
	}
	 
	private Double calculatePrice(List<Item> items) {
		Double price = (double) 0;
		for (Item item : this.items) {
			price += item.price;
		}
		return price;
	}

	public Order(Long orderId, Long customerId, String customerForename, String customerSurname, List<Item> items) {
		this.customerId = customerId;
		this.orderId = orderId;
		this.items = items;
		this.customerForename = customerForename;
		this.customerSurname = customerSurname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	@Override
	public String toString() {
		return "Order [id=" + id + ", customerId=" + customerId + ", orderId=" + orderId + ", customerForename="
				+ customerForename + ", customerSurname=" + customerSurname + ", items=" + items
				+ "Total cost: £" + calculatePrice(items) + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerForename, customerId, customerSurname, id, itemId, items, orderId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(customerForename, other.customerForename) && Objects.equals(customerId, other.customerId)
				&& Objects.equals(customerSurname, other.customerSurname) && Objects.equals(id, other.id)
				&& Objects.equals(itemId, other.itemId) && Objects.equals(items, other.items)
				&& Objects.equals(orderId, other.orderId);
	}

/*	@Override
	public String toString() {
		return "{order id: " + orderId + ",  customer id: " + customerId + ",  customer first name: " + customerForename
				+ ",  customer last name: " + customerSurname + ",\n items:" + items + "\n Total cost: £" + calculatePrice(items) + "}";
	}*/

	

}
