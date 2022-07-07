package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class OrderDAOTest {

	private final OrderDAO DAO = new OrderDAO();

	@Before
	public void setup() {
		DBUtils.connect();
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}

	@Test
	public void testCreate() {
		setup();
		final List<Item> items = new ArrayList<>();
		final Item item = new Item(1L, "apple", 0.50);
		items.add(item);
		Order.reconnect();
		final Order created = new Order(2L, 2L, "Jack", "Jones",
				items);
	created.setItems(items);
		assertEquals(created, DAO.create(created));
		final Order created2 = new Order(2L, 2L, "Jack", "Jones",
				items);
		assertEquals(null, DAO.create(created2));
		final Order created3 = new Order(100L, 100L, "Jack", "Jones",
				items);
		assertEquals(null, DAO.create(created3));
		final List<Item> items2 = new ArrayList<>();
		final Item item2 = new Item(100L, "star", 200.50);
		items2.add(item2);
		final Order created4 = new Order(1L, 2L, "Jack", "Jones",
				items2);
		assertEquals(null, DAO.create(created4));
	}

	@Test
	public void testGetOrderId() {
		Long expected = 1L;
		assertEquals(expected, DAO.getOrderId(1L));
	}
	
	@Test
	public void testReadAll() {
		List<Order> expected = new ArrayList<>();
		final List<Item> items = new ArrayList<>();
		final Item item = new Item(1L, "apple", 0.50);
		items.add(item);
		expected.add(new Order(null, 1L, 1L, "jordan", "harrison",
				items));
		assertEquals(expected, DAO.readAll());
	}

	@Test
	public void testReadLatest() {
		final List<Item> items = new ArrayList<>();
		final Item item = new Item(1L, "apple", 0.50);
		items.add(item);
		assertEquals(new Order(null, 1L, 1L, "jordan", "harrison",
				items), DAO.readLatest());
	}
	
	@Test
	public void testCustomerExists() {
		boolean expected = true;
		assertEquals(expected, DAO.customerExists(1L));
		assertEquals(false, DAO.customerExists(100L));
	}
	
	
	@Test
	public void testItemExists() {
		boolean expected = true;
		assertEquals(expected, DAO.itemExists(1L));
		assertEquals(false, DAO.itemExists(100L));
	}
	
	@Test
	public void testOrderExists() {
		boolean expected = true;
		assertEquals(expected, DAO.orderExists(1L));
		assertEquals(false, DAO.orderExists(100L));
	}
	
	@Test
	public void testCustomerHasCurrentOrder() {
		boolean expected = true;
		assertEquals(expected, DAO.customerHasCurrentOrder(1L));
		assertEquals(false, DAO.customerHasCurrentOrder(2L));
	}

	@Test
	public void testRead() {
		final long ID = 1L;
		final List<Item> items = new ArrayList<>();
		final Item item = new Item(1L, "apple", 0.50);
		items.add(item);
		assertEquals(new Order(null, 1L, 1L, "jordan", "harrison",
				items), DAO.read(ID));
	}

	@Test
	public void testUpdate() {
		final List<Item> items2 = new ArrayList<>();
		final Item item = new Item(1L, "apple", 0.50);
		final Item item2 = new Item(2L, "milk", 1.50);
		items2.add(item);
		items2.add(item2);
		Order expected = new Order(null, 1L, 1L, "jordan", "harrison",
				items2);
		assertEquals(expected, DAO.update(1L, "add", 2L));
		final List<Item> items = new ArrayList<>();
		items.add(item2);
		final Order updated = new Order(null, 1L, 1L, "jordan", "harrison",
				items);
		assertEquals(updated, DAO.update(1L, "remove", 1L));
		assertEquals(null, DAO.update(1L, "iihbiuh", 1L));
		assertEquals(null, DAO.update(100L, "add", 1L));
		assertEquals(null, DAO.update(1L, "add", 100L));
	}
	
	@Test
	public void testRemoveItem() {
		Order expected = null;
		assertEquals(expected, DAO.removeItem(1L, 3L));
		assertEquals(expected, DAO.removeItem(1L, 2L));
		assertEquals(expected, DAO.removeItem(1L, 1L));
		assertEquals(expected, DAO.removeItem(2L, 1L));
	}
	
	@Test
	public void testAddItem() {
		final List<Item> items = new ArrayList<>();
		final Item item = new Item(1L, "apple", 0.50);
		items.add(item);
		items.add(item);
		Order expected = new Order(null, 1L, 1L, "jordan", "harrison",
				items);
		assertEquals(expected, DAO.addItem(1L, 1L));
	}
	
	@Test
	public void testUpdate2() {
		final List<Item> items = new ArrayList<>();
		final Item item = new Item(1L, "apple", 0.50);
		items.add(item);
		final Order updated = new Order(null, 1L, 1L, "jordan", "harrison",
				items);
		assertEquals(updated, DAO.update(updated));
		

	}
	
	@Test
	public void testOrderHasItems() {
		boolean expected = true;
		assertEquals(expected, DAO.orderHasItems(1L));
		assertEquals(false, DAO.orderHasItems(2L));
	}
	
	@Test
	public void testOrderContainsItem() {
		boolean expected = false;
		assertEquals(expected, DAO.orderContainsItem(1L, 10L));
	}
	

	@Test
	public void testDelete() {
		assertEquals(1, DAO.delete(1));
	}
	
	@Test
	public void testDeleteByCustomer() {
		
		assertEquals(1, DAO.deleteByCustomer(1L));
		assertEquals(0, DAO.deleteByCustomer(2L));
	}
}
