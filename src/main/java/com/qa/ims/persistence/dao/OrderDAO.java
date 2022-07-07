package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class OrderDAO implements Dao<Order> {

	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public Order modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("order_items.id");
		Long orderId = resultSet.getLong("order_items.order_id");
		Long customerId = resultSet.getLong("orders.customer_id");
		String customerForename = resultSet.getString("customers.first_name");
		String customerSurname = resultSet.getString("customers.surname");
		List<Item> items = readItemsInOrder(orderId);
		return new Order(orderId, customerId, customerForename, customerSurname, items);
	}

	/**
	 * Reads all items from the database
	 * 
	 * @return A list of items
	 */
	@Override
	public List<Order> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement
						.executeQuery("SELECT order_items.id, order_items.order_id, orders.customer_id, "
								+ "customers.first_name, customers.surname " + "FROM ((order_items "
								+ "JOIN orders ON order_items.order_id = orders.id)"
								+ " JOIN customers ON orders.customer_id = customers.id)"
								+ "GROUP BY order_items.order_id;");) {
			List<Order> orders = new ArrayList<>();
			while (resultSet.next()) {
				orders.add(modelFromResultSet(resultSet));
			}
			return orders;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	public Order readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement
						.executeQuery("SELECT order_items.id, order_items.order_id, orders.customer_id, "
								+ "customers.first_name, customers.surname, order_items.item_id, items.item_name, items.price "
								+ "FROM (((order_items" + " JOIN orders ON order_items.order_id = orders.id)"
								+ " JOIN customers ON orders.customer_id = customers.id)"
								+ " JOIN items ON order_items.item_id = items.id)"
								+ " ORDER BY order_items.id DESC LIMIT 1;");) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Order read(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("SELECT order_items.id, order_items.order_id, orders.customer_id, "
								+ "customers.first_name, customers.surname, order_items.item_id, items.item_name, items.price "
								+ "FROM (((order_items " + "JOIN orders ON order_items.order_id = orders.id)"
								+ " JOIN customers  ON orders.customer_id = customers.id)"
								+ " JOIN items on order_items.item_id = items.id)"
								+ "WHERE order_items.order_id = ?;");) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery();) {
				resultSet.next();
				return modelFromResultSet(resultSet);
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	private List<Item> readItemsInOrder(Long orderId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("SELECT items.id, items.item_name, items.price " + "FROM order_items"
								+ " JOIN items ON order_items.item_id = items.id"
								+ " WHERE order_items.order_id = ?;");) {
			statement.setLong(1, orderId);
			List<Item> items = new ArrayList<Item>();
			List<String> itemStrings = new ArrayList<String>();
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				ItemDAO itemDAO = new ItemDAO();
				items.add(itemDAO.modelFromResultSet(resultSet));
				itemStrings.add(itemDAO.modelFromResultSet(resultSet).toString());
			}
			return items;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	/**
	 * Creates an order in the database
	 * 
	 * @param order - takes in an order object. id will be ignored
	 */
	@Override
	public Order create(Order order) {
		if (customerExists(order.getCustomerId())) {
			if (itemExists(order.getItems().get(0).getId())) {
				if (!customerHasCurrentOrder(order.getCustomerId())) {
					try (Connection connection = DBUtils.getInstance().getConnection();
							PreparedStatement statement1 = connection
									.prepareStatement("INSERT INTO orders(customer_id) VALUES (?)");
							PreparedStatement statement2 = connection
									.prepareStatement("INSERT INTO order_items(order_id, item_id) VALUES (?, ?)");) {
						statement1.setLong(1, order.getCustomerId());
						statement1.executeUpdate();
						statement2.setLong(1, getOrderId(order.getCustomerId()));
						statement2.setLong(2, order.getItems().get(0).getId());
						statement2.executeUpdate();
						Order newOrder = readLatest();
						System.out.println(newOrder);
						return newOrder;
					} catch (Exception e) {
						LOGGER.debug(e);
						LOGGER.error(e.getMessage());
					}
					return null;
				} else {
					LOGGER.info("CUSTOMER CANNOT HAVE TWO ORDERS AT THE SAME TIME."
							+ " CLOSE PREVIOUS ORDERS TO CREATE A NEW ONE OR UPDATE THE PREVIOUS ORDER WITH NEW ITEMS");
					return null;
				}
			} else {
				LOGGER.info("ITEM DOES NOT EXIST. PLEASE CHOOSE AN EXISTING ITEM.");
				return null;
			}
		} else {
			LOGGER.info("CUSTOMER DOES NOT EXIST. PLEASE CHOOSE AN EXISTING CUSTOMER.");
			return null;
		}
	}

	public Long getOrderId(Long customerId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("SELECT id FROM orders WHERE customer_id = ?");) {
			statement.setLong(1, customerId);
			try (ResultSet resultSet = statement.executeQuery();) {
				resultSet.next();
				return resultSet.getLong("id");
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	public boolean customerExists(Long customerId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM customers WHERE id = ?");) {
			statement.setLong(1, customerId);
			try (ResultSet resultSet = statement.executeQuery();) {
				if (resultSet.next()) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return false;
	}

	public boolean itemExists(Long itemId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM items WHERE id = ?");) {
			statement.setLong(1, itemId);
			try (ResultSet resultSet = statement.executeQuery();) {
				if (resultSet.next()) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return false;
	}

	public boolean orderExists(Long orderId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders WHERE id = ?");) {
			statement.setLong(1, orderId);
			try (ResultSet resultSet = statement.executeQuery();) {
				if (resultSet.next()) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return false;
	}

	public boolean customerHasCurrentOrder(Long customerId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("SELECT * FROM orders WHERE customer_id = ?");) {
			statement.setLong(1, customerId);
			try (ResultSet resultSet = statement.executeQuery();) {
				if (resultSet.next()) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return false;
	}

	/**
	 * Updates an order in the database
	 * 
	 * @param order - takes in an order object, the id field will be used to update
	 *              that order in the database
	 * @return
	 */
	// @Override
	public Order update(Long id, String addOrRemove, Long itemId) {
		if (addOrRemove.equals("add")) {
			addItem(id, itemId);
		} else if (addOrRemove.equals("remove")) {
			removeItem(id, itemId);
		} else {
			LOGGER.info("PLEASE ENTER A VALID RESPONSE (ADD/REMOVE)");
			return null;
		}
		return read(id);
	}

	public Order removeItem(Long id, Long itemId) {
		if (orderExists(id)) {
			if (itemExists(itemId)) {
				if (orderContainsItem(id, itemId)) {
					try (Connection connection = DBUtils.getInstance().getConnection();
							PreparedStatement statement = connection
									.prepareStatement("DELETE FROM order_items WHERE order_id = ? AND item_id = ?;");) {
						statement.setLong(1, id);
						statement.setLong(2, itemId);
						statement.executeUpdate();
						if (orderHasItems(id)) {
							return read(id);
						} else {
							delete(id);
							return null;
						}
					} catch (Exception e) {
						LOGGER.debug(e);
						LOGGER.error(e.getMessage());
					}
					return null;
				} else {
					LOGGER.info(
							"ORDER DOES NOT CONTAIN SELECTED ITEM. YOU CAN ONLY REMOVE AN ITEM THAT THE ORDER CONTAINS");
					return null;
				}
			} else {
				LOGGER.info("ITEM DEOS NOT EXIST. PLEASE ENTER AN EXISTING ITEM");
				return null;
			}
		} else {
			LOGGER.info("ORDER DOES NOT EXIST PLEASE ENTER AN EXISTING ORDER");
			return null;
		}
	}

	public Order addItem(Long orderId, Long itemId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO order_items (order_id, item_id) VALUES " + " (?, ?)");) {
			statement.setLong(1, orderId);
			statement.setLong(2, itemId);
			statement.executeUpdate();
			return read(orderId);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	public boolean orderHasItems(Long orderId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("SELECT * FROM order_items WHERE order_id = ?");) {
			statement.setLong(1, orderId);
			try (ResultSet resultSet = statement.executeQuery();) {
				if (resultSet.next()) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return false;
	}

	public boolean orderContainsItem(Long orderId, Long itemId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("SELECT * FROM order_items WHERE order_id = ? AND item_id = ?");) {
			statement.setLong(1, orderId);
			statement.setLong(2, itemId);
			try (ResultSet resultSet = statement.executeQuery();) {
				if (resultSet.next()) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return false;
	}

	/**
	 * Deletes an order in the database
	 * 
	 * @param id - id of the order
	 */
	@Override
	public int delete(long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement1 = connection
						.prepareStatement("DELETE FROM order_items WHERE order_id = ?");
				PreparedStatement statement2 = connection.prepareStatement("DELETE FROM orders WHERE id = ?");) {
			statement1.setLong(1, id);
			statement2.setLong(1, id);
			statement1.executeUpdate();
			return statement2.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}

	@Override
	public Order update(Order t) {
		// TODO Auto-generated method stub
		return t;
	}

}
