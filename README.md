I wanted to understand database internals and thought of doing it by building a simple functional database.
This project follows typical structure of a sample DB project in any Database course.

## Heap File Storage System - Implement a basic file storage system that allows you to read and write database records.
- [x] Define a file format for storing records, such as a binary format or a CSV-like format.
- [x] Develop methods to insert, update, and delete records in the heap file.
- [x] Implement methods to scan and retrieve records from the heap file based on certain conditions.

### Testing
- [x] Create a sample heap file.
- [x] Insert records into it.
- [x] Verify that the records can be retrieved correctly.

### Buffer Pool:
The buffer pool task builds on top of the heap storage and involves implementing a caching mechanism to keep frequently accessed database pages in memory, reducing disk I/O.

* Create a buffer pool manager that handles the caching of database pages in memory.
* Implement a page replacement policy, such as LRU (Least Recently Used), to evict pages when the buffer pool is full.
* Develop methods to read pages from disk into the buffer pool and write dirty pages back to disk.
* Optimize disk I/O by minimizing the number of disk accesses.

### Testing: 
* Simulate database queries and verify that the buffer pool effectively caches and manages pages, minimizing disk access.

### Operator Implementation:

* Implement various query processing operators, such as table scans, selections, projections, and joins.
* Develop algorithms for efficient execution of these operators, considering factors like memory usage and disk I/O.
* Implement support for different join algorithms, such as nested loop join, hash join, and sort-merge join.
* Optimize the operators by leveraging indexing and other performance techniques.

### Testing: 
* Create sample queries and verify that the implemented operators produce the expected results efficiently.

### Query Optimization:
The query optimization task focuses on generating efficient query execution plans.

* Develop a query optimizer that generates efficient query execution plans.
* Implement techniques for estimating the selectivity of predicates and the cost of different query plans.
* Explore different join ordering strategies to minimize the cost of query execution.
* Implement a query parser that translates SQL-like queries into an internal representation for optimization.

### Testing:
* Create a set of sample queries and verify that the query optimizer generates efficient execution plans.

### Transactions and Concurrency Control:

* Implement support for transactions, ensuring ACID properties (Atomicity, Consistency, Isolation, Durability).
* Develop a locking mechanism, such as two-phase locking, to handle concurrent access to the database.
* Implement isolation levels, such as serializable and snapshot isolation, to control the level of concurrency.
* Handle deadlock detection and resolution to prevent conflicts between concurrent transactions.
### Testing: 
* Simulate concurrent transactions and verify that the implemented concurrency control mechanisms maintain data integrity and prevent conflicts.

### Recovery:
The recovery task focuses on ensuring data durability and recoverability.

* Implement a log-based recovery system to ensure data durability and recoverability.
* Develop methods to write transaction logs and periodically flush them to disk.
* Implement crash recovery techniques, such as undo and redo logging, to restore the database to a consistent state after a failure.
* Explore techniques for checkpointing and log truncation to optimize recovery time.

### Testing: 
* Simulate database crashes or failures and verify that the recovery mechanism successfully restores the database to a consistent state.