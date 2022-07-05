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
		Long id = resultSet.getLong("oi.id");
		Long orderId = resultSet.getLong("oi.order_id");
		Long customerId = resultSet.getLong("o.customer_id");
		String customerForename = resultSet.getString("c.first_name");
		String customerSurname = resultSet.getString("c.surname");
		List<Item> items = readItemsInOrder(id);
		//List<String> itemStrings = readItemsInOrder(id);
		/*Long itemId = resultSet.getLong("oi.item_id");
		String itemName = resultSet.getString("i.item_name");
		Double itemPrice = resultSet.getDouble("i.price");*/
	//	return new Order(id, orderId, customerId, customerForename, customerSurname, itemId, itemName, itemPrice);
		return new Order(id, orderId, customerId, customerForename, customerSurname, items);
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
				ResultSet resultSet = statement.executeQuery("SELECT oi.id, oi.order_id, o.customer_id, "
						+ "c.first_name, c.surname, oi.item_id, i.item_name, i.price " + "FROM (((order_items oi "
						+ "JOIN orders o ON oi.order_id = o.id)" + " JOIN customers c ON o.customer_id = c.id)"
						+ " JOIN items i ON oi.item_id = i.id)"
						+ "GROUP BY oi.order_id;");) {
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
				ResultSet resultSet = statement.executeQuery("SELECT oi.id, oi.order_id, o.customer_id, "
						+ "c.first_name, c.surname, oi.item_id, i.item_name, i.price " + "FROM (((order_items oi"
						+ " JOIN orders o ON oi.order_id = o.id)" + " JOIN customers c ON o.customer_id = c.id)"
						+ " JOIN items i ON oi.item_id = i.id)" + " ORDER BY oi.id DESC LIMIT 1;");) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	public List<Item> readItemsInOrder(Long orderId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT i.id, i.item_name, i.price "
						+ "FROM order_items oi "
						+ " JOIN items i ON oi.item_id = i.id"
						+ " WHERE oi.order_id = ?;");) {
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
		if (!customerHasCurrentOrder(order.getCustomerId())) {
			try (Connection connection = DBUtils.getInstance().getConnection();
					PreparedStatement statement1 = connection
							.prepareStatement("INSERT INTO orders(customer_id) VALUES (?)");
					PreparedStatement statement2 = connection
							.prepareStatement("INSERT INTO order_items(order_id, item_id) VALUES (?, ?)");) {
				statement1.setDouble(1, order.getCustomerId());
				statement1.executeUpdate();
				statement2.setDouble(1, getOrderId(order.getCustomerId()));
				statement2.setDouble(2, order.getItemId());

				statement2.executeUpdate();
				return readLatest();
			} catch (Exception e) {
				LOGGER.debug(e);
				LOGGER.error(e.getMessage());
			}
			return null;
		} else {
			LOGGER.info("Customer cannot have two orders at the same time."
					+ " Close previous order to create a new one or update the previous order with new items");
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

	@Override
	public Order read(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT oi.id, oi.order_id, o.customer_id, "
						+ "c.first_name, c.surname, oi.item_id, i.item_name, i.price " + "FROM (((order_items oi "
						+ "JOIN orders o ON oi.order_id = o.id)" + " JOIN customers c ON o.customer_id = c.id)"
						+ " JOIN items i on oi.item_id = i.id)" + "WHERE oi.order_id = ?;");) {
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
			LOGGER.info("Please enter a valid response (ADD/REMOVE)");
		}
		return read(id);
	}

	public Order removeItem(Long id, Long itemId) {
		// TODO Auto-generated method stub
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("DELETE FROM order_items WHERE order_id = ? AND item_id = ?;");) {
			statement.setLong(1, id);
			statement.setLong(2, itemId);
			statement.executeUpdate();
			return read(id);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
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

	/**
	 * Deletes an order in the database
	 * 
	 * @param id - id of the order
	 */
	@Override
	public int delete(long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE id = ?");) {
			statement.setLong(1, id);
			return statement.executeUpdate();
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
