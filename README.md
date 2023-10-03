# Point of Sales System For Sharetea.


## User Interface

each page img

## SQL schema and Entities 
- The project defines a Point of Sale (POS) system for managing customer orders, employees, products, and inventory. In the entity-relationship structure, the primary entities are Customer, Employee, Order, OrderProduct, Product, InventoryProduct, and Inventory. Key relationships include a Customer placing multiple orders, each order being processed by one Employee, and orders comprising multiple products. An essential bridge entity is OrderProduct, connecting the many-to-many relationship between Order and Product. The InventoryProduct entity links available products with the actual inventory items.
- SQL schemas for each entity are provided, detailing columns, data types, and relationships. These schemas form the foundation for database creation and subsequent operations. This modular and scalable design ensures smooth addition of new records while preserving data integrity and representation. For a detailed view of the schemas and relationships, refer to the [SQL Documentation](docs/sqlp2.pdf)
