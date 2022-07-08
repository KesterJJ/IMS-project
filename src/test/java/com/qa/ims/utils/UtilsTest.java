package com.qa.ims.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Scanner;

import org.junit.Test;

public class UtilsTest {
	Utils utils = new Utils();;
	
	@Test
	public void testUtils1() {
		
		assertTrue(utils instanceof Utils);
	}
	/*
	@Test
	public void testUtils2() {
		Scanner scanner = new Scanner(System.in);
		utils = new Utils(scanner);
		assertTrue(utils instanceof Utils);
	}
	
	@Test
	public void testGetLong() {
		assertEquals((Long) 1L, (Long) utils.getLong());
	}
	
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
