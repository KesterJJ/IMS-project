package com.qa.ims.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Scanner;

import org.junit.Test;
import org.mockito.Mockito;

import com.qa.ims.persistence.domain.Customer;

public class UtilsTest {
	Utils utils = new Utils();;

	@Test
	public void testUtils1() {
		
		assertTrue(utils instanceof Utils);
	}
	
	@Test
	public void testUtils2() {
		Scanner scanner = new Scanner(System.in);
		utils = new Utils(scanner);
		assertTrue(utils instanceof Utils);
	}
	/*
	@Test
	public void testUtils2() {
		Scanner scanner = new Scanner(System.in);
		utils = new Utils(scanner);
		assertTrue(utils instanceof Utils);
	}*/
	/*
	@Test

	public void testGetLong() {

		Mockito.when(utils.getString()).thenReturn("1", "1");

		
		assertEquals((Long) 1L, (Long) utils.getLong());

		Mockito.verify(utils, Mockito.times(2)).getString();
	}*/
	
	/*
	@Test
	public void testGetString() {
		utils = new Utils();
		assertEquals("1", utils.getString());
	}
	
	@Test
	public void testGetDouble() {
		utils = new Utils();
		assertEquals((Double) 1d, utils.getDouble());
	}*/
	

}
