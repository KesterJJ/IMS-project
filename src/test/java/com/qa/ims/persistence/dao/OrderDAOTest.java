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
		final Order created = new Order(1L, 1L, 1L, "jordan", "harrison",
				items);
		assertEquals(created, DAO.create(created));
	}

	@Test
	public void testReadAll() {
		List<Order> expected = new ArrayList<>();
		final List<Item> items = new ArrayList<>();
		final Item item = new Item(1L, "apple", 0.50);
		items.add(item);
		expected.add(new Order(1L, 1L, 1L, "jordan", "harrison",
				items));
		assertEquals(expected, DAO.readAll());
	}

	@Test
	public void testReadLatest() {
		final List<Item> items = new ArrayList<>();
		final Item item = new Item(1L, "apple", 0.50);
		items.add(item);
		assertEquals(new Order(1L, 1L, 1L, "jordan", "harrison",
				items), DAO.readLatest());
	}

	@Test
	public void testRead() {
		final long ID = 1L;
		final List<Item> items = new ArrayList<>();
		final Item item = new Item(1L, "apple", 0.50);
		items.add(item);
		assertEquals(new Order(1L, 1L, 1L, "jordan", "harrison",
				items), DAO.read(ID));
	}

	@Test
	public void testUpdate() {
		final List<Item> items = new ArrayList<>();
		final Item item = new Item(1L, "apple", 0.50);
		items.add(item);
		final Order updated = new Order(1L, 1L, 1L, "jordan", "harrison",
				items);
		assertEquals(updated, DAO.update(updated));

	}

	@Test
	public void testDelete() {
		assertEquals(1, DAO.delete(1));
	}
}
