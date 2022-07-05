INSERT INTO `ims`.`customers` (`first_name`, `surname`) VALUES 
 ('jordan', 'harrison'),
  ('Jack', 'Smith'),
  ('Shirly', 'Rice'),
  ('Flob', 'Robertson')
 ;
INSERT INTO `ims`.`items` (`item_name`, `price`) VALUES 
('apple', 0.50),
('toothbrush', 1.20),
('milk', 1.50)
;
INSERT INTO `ims`.`orders` (`customer_id`) VALUES 
(3),
(1),
(2)
;
INSERT INTO `ims`.`order_items` (`order_id`, `item_id`) VALUES 
(1, 2),
(3, 2),
(2, 1)
;