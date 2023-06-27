# Delivery-Service
2023-05-27 14:00 @신** @신**

This JAVA application is an application for shop owners with DB using 5 tables and 6 menus.
There are Shop, Rider, Customer, Menu, and Orders table.

You can use menus after logging in with your shop's id.
You can create and delete menus from your shop,
update the status of your orders,
check the average salary of a rider by area,
find customer for specific membership rank,
and find rider in the same area that is not currently on delivery for your food delivery.

How to Use this Application:

1. Enter your shop id
2. Choose menu
3. Read the system message and enter information
4. Exit with menu (0)

How to Start this Application:

1. Open up your terminal(cmd line)
2. Move to the directory where delivery_service.jar is placed
3. enter the code below to execute jar program
-> java -jar delivery_service.jar

Requirements
1. MySQL
2. MySQL user name "testuser" with pw of "testpw"
3. MySQL database name "delivery_sevice"
4. Create tables and put every initial datas in delivery_service.
+ Use source command to execute .sql file. (Modify the path according to your computer and use it.)
-> source C:\Users\dropschema.sql
-> source C:\Users\createschema.sql
-> source C:\Users\initdata.sql
