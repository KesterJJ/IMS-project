
USE ims;
SELECT * FROM order_items;

SELECT oi.id, oi.order_id, o.customer_id, c.first_name, c.surname, oi.item_id, i.item_name, i.price 
						FROM (((order_items oi
						 JOIN orders o ON oi.order_id = o.id)
						 JOIN customers c ON o.customer_id = c.id)
						 JOIN items i on oi.item_id = i.id);
                         
                      --   SELECT * FROM orders;