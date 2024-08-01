# eshop-database-system

## description
A database system for managing an e-shop with computer parts. I used PostgreSQL for the database and Hibernate ORM to simplify managing the e-shop by performing CRUD operations in Java. The database contains users, employees, products, orders, and other entities related to an e-shop. This project taught me a lot about relational databases.

## showcase
I created a simple DAO layer and Store API to access the eshop via Java. The API uses the simple DAO layer to perform CRUD operations on the database. Here are some simple examples (included them in the readme so its easy to find):

User example 1:

```Java
//hard coded for demonstration purposes
static int DELL_MONITOR_ID = 7;
static int MACBOOK_ID = 4;
static int RTX_4060_ID = 3;

static String adminUsername = "Admin";
static String adminPassword = "Admin";
static int adminID = 33;

static String normalUserUsername = "User";
static String normalUserPassword = "User";
```

