package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class OrderTest {
	
	Order order;
	
	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(Order.class).verify();
	}


	
	@Test
	public void testConstructor() {
		order = new Order(1L, 1L);
		
		assertTrue(order instanceof Order);
	}
	

}