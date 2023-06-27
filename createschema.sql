CREATE TABLE Rider
(id VARCHAR(5),
 name VARCHAR(8),
 tel_number CHAR(13),
 area CHAR(1),
 PRIMARY KEY (id)
);
DESCRIBE Rider;

CREATE TABLE Customer
(id VARCHAR(5),
 name VARCHAR(8),
 membership CHAR(1),
 area CHAR(1),
 PRIMARY KEY (id)
);
DESCRIBE Customer;

CREATE TABLE Shop
(id VARCHAR(5),
 name VARCHAR(15),
 delivery_fee NUMERIC(4,0),
 area CHAR(1),
 PRIMARY KEY (id)
);
DESCRIBE Shop;

CREATE TABLE Menu
(id VARCHAR(5),
 name VARCHAR(15),
 shop_id VARCHAR(5),
 price NUMERIC(5,0),
 category VARCHAR(8),
 PRIMARY KEY (id),
 FOREIGN KEY (shop_id) REFERENCES Shop (id)
		ON DELETE CASCADE
);
DESCRIBE Menu;

CREATE TABLE Orders
(id VARCHAR(5),
 customer VARCHAR(5),
 shop_id VARCHAR(5),
 rider VARCHAR(5),
 order_date DATETIME,
 state VARCHAR(12),
 PRIMARY KEY (id),
 FOREIGN KEY (customer) REFERENCES Customer (id)
		ON DELETE CASCADE,
 FOREIGN KEY (shop_id) REFERENCES Shop (id)
		ON DELETE CASCADE,
 FOREIGN KEY (rider) REFERENCES Rider (id)
		ON DELETE CASCADE
);
DESCRIBE Orders;

CREATE VIEW Not_in_delivery as
SELECT DISTINCT rider.id as r_id,
	rider.name as r_name,
	rider.tel_number as r_tel,
	rider.area as r_area
FROM Rider, Orders
WHERE (Rider.id <> ALL(SELECT rider FROM Orders)) 
	OR (Rider.id = Orders.rider AND Orders.state="Complete");
	
CREATE VIEW order_info as
SELECT Orders.id as O_id,shop_id as S_id, customer as C_id
FROM Orders;

CREATE INDEX shop_id_index on Orders(shop_id);
CREATE INDEX state_index on Orders(state);