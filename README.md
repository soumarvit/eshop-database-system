# eshop-database-system

## description
A database system for managing an e-shop with computer parts. I used PostgreSQL for the database and Hibernate ORM to map the tables to objects in Java. The database contains users, employees, products, orders, and other entities related to an e-shop.

I also created a simple DAO layer and Store API to access the eshop via Java. The API uses the simple DAO layer to perform CRUD operations on the database. There are 4 examples in [Main.java](https://github.com/soumarvit/eshop-database-system/blob/main/StoreDB/src/main/java/cvut/fel/store/Main.java) and the output logs for these examples can be found [here](https://github.com/soumarvit/eshop-database-system/tree/main/showcase_images).

## database
The database models and diagrams can be found [here](https://github.com/soumarvit/eshop-database-system/blob/main/database.pdf). This file also includes the creation process of the database.
