package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.Utils;

public class OrderController implements CrudController<Order> {

	public static final Logger LOGGER = LogManager.getLogger();

	private OrderDAO orderDAO;
	private Utils utils;

	public OrderController(OrderDAO orderDAO, Utils utils) {
		super();
		this.orderDAO = orderDAO;
		this.utils = utils;
	}

	@Override
	public List<Order> readAll() {
		List<Order> orders = orderDAO.readAll();
		for (Order order : orders) {
			LOGGER.info(order);
		}
		return orders;
	}

	/**
	 * Creates an order by taking in user input
	 */
	@Override
	public Order create() {
		LOGGER.info("Please enter a customer id");
		Long customerId = utils.getLong();
		LOGGER.info("Please enter an item id");
		Long itemId = utils.getLong();
		Order order = orderDAO.create(new Order(customerId, itemId));
		if (order != null) {
			LOGGER.info("Order created");
		return order;
		} else {
			return null;
			}

	}

	/**
	 * Updates an existing order by taking in user input
	 */
	@Override
	public Order update() {
		LOGGER.info("Please enter the id of the order you would like to update");
		Long id = utils.getLong();	
		LOGGER.info(orderDAO.read(id));
		LOGGER.info("Would you like to add a new item to the order or remove an item from the order? (add/remove)");
		String addOrRemove = utils.getString().toLowerCase();
		if (addOrRemove.equals("add")) {
			LOGGER.info("Please enter an item id to add to the order");
		} else if (addOrRemove.equals("remove")) {
			LOGGER.info("Which item would you like to remove from the order? Please enter item id:");
		} else {
			LOGGER.info("PLEASE ENTER EITHER 'ADD' OR 'REMOVE'");
			return null;
		}
		Long itemId = utils.getLong();
		LOGGER.info(orderDAO.update(id, addOrRemove, itemId));
		Order order = orderDAO.update(new Order(id, itemId));
		LOGGER.info("Order Updated");
		return order;

	}

	/**
	 * Deletes an existing item by the id of the item
	 * 
	 * @return
	 */
	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the order you would like to delete");
		Long id = utils.getLong();
		return orderDAO.delete(id);
	}

}
