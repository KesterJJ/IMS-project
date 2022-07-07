package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class ItemTest {
	
	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(Order.class).verify();

	}

	
	@Test
	public void testEquals2() {
	Item item = new Item("apple", 0.50);
	assertEquals(false, item.equals(null));
	}
}
