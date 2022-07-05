package com.qa.ims.persistence.domain;

import java.util.List;

import com.qa.ims.persistence.dao.OrderDAO;

public class Order {
	private Long id;
	private Long customerId;
	private Long orderId;
	private String customerForename;
	private String customerSurname;;
	private Long itemId;
	private String itemName;
	private Double itemPrice;
	private List<Item> items;

	public Order(Long customerId, Long itemId) {
		this.setCustomerId(customerId);
		this.setItemId(itemId);
	}

	public Order(Long id, Long customerId, Long itemId) {
		this.setId(id);
		this.setCustomerId(customerId);
		this.setItemId(itemId);
	}
	
	
	public Order(Long id, Long orderId, Long customerId, String customerForename, String customerSurname, 
			Long itemId, String itemName, Double itemPrice) {
		this.id = id;
		this.customerId = customerId;
		this.orderId = orderId;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.customerForename = customerForename;
		this.customerSurname = customerSurname;
	}
	
	public Order(Long orderId, Long customerId, String customerForename, String customerSurname,
			Long itemId, String itemName, Double itemPrice) {
		this.customerId = customerId;
		this.orderId = orderId;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.customerForename = customerForename;
		this.customerSurname = customerSurname;
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
	
	public Order(Long orderId, Long customerId, String customerForename, String customerSurname,
			List<Item> items) {
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
		return "{order-item id: " + id + ",  order id: " + orderId + ",  customer id: " 
	+ customerId + ",  customer first name: " + customerForename + ",  customer last name: "
				+ customerSurname + ",\n items:" + items + "}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		return result;
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
		if (getItemId() == null) {
			if (other.getItemId() != null)
				return false;
		} else if (!getItemId().equals(other.getItemId()))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		return true;
	}
}
