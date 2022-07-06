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
		final List<Item> items = new ArrayList<>();
		final Item item = new Item(1L, "apple", 0.50);
		items.add(item);
		final Order created = new Order(null, 2L, 2L, "Jack", "Jones",
				items);
		created.setItems(items);
		assertEquals(created, DAO.create(created));
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
		items2.add(item);
		items2.add(item);
		Order expected = new Order(null, 1L, 1L, "jordan", "harrison",
				items2);
		assertEquals(expected, DAO.update(1L, "remove", 1L));
		final List<Item> items = new ArrayList<>();
		final Order updated = new Order(null, 1L, 1L, "jordan", "harrison",
				items);
		assertEquals(updated, DAO.update(1L, "remove", 1L));

	}
	
	@Test
	public void testRemoveItem() {
		final List<Item> items = new ArrayList<>();
		Order expected = new Order(null, 1L, 1L, "jordan", "harrison",
				items);
		assertEquals(expected, DAO.addItem(1L, 1L));
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
	public void testDelete() {
		assertEquals(1, DAO.delete(1));
	}
}
