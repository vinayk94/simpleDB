# Learning Journal for SimpleDB Project


## Day 1: Understanding Heap Storage and implementation in Java
The concept of heap storage in the context of databases refers to a way of storing data that is unordered. It's one of the simplest and most basic forms of data storage on disk, making it a great starting point for building a database system, especially for educational purposes. Here’s why it’s commonly used in learning environments:

* Simplicity: A heap storage system doesn't require a complex structure, making it easier to implement and understand.
* Insertion Speed: Since there is no ordering, new records can be added very quickly, just by placing them at the end of the file.
* Full Table Scans: Heap files often require a full scan for queries, which leads to discussions on efficiency and motivates the introduction of indexes.
* Other types of storage that are commonly used in databases include:

  * Tree-Based Storage: Data is stored in a tree-like structure, such as B-trees and B+ trees, which are used for indexed storage. These allow for faster search, insert, and delete operations compared to heap files.
  * Hash-Based Storage: This uses a hash function to determine where to store and retrieve records quickly based on a key. This is typically used for hash indexes and hash-partitioned tables.
  * Columnar Storage: Instead of storing data row by row, it stores data column by column, which can be more efficient for certain types of queries and operations, especially in analytical workloads.
  * Clustered Tables: The data is stored on disk based on the order of the primary key. This allows for quick retrieval of records in sequence but can slow down insertions.
  * In the real world, modern databases use a mix of different storage types, optimized for the kind of work they are expected to do. Heap storage is indeed used, particularly in simpler or less performance-critical contexts.

Building a file storage system involves several design considerations, especially when deciding between formats like CSV or binary. Each format has its advantages and disadvantages, depending on the use case. Here's a rundown to help you decide:

CSV Format:<br>
Pros:
* Human-readable, which makes it easier to debug and understand the data.
* Easy to edit manually with a text editor or import/export with tools that handle CSV files.
* Simple to implement because the data is text-based.<br>

Cons:
* Not as space-efficient as binary, especially if there are lots of numeric data or repeating patterns.
* Can be slower to parse due to the need to interpret text and convert it into the data types needed by the application.
* Doesn’t handle complex data types or relationships well.
* Potential issues with data that contains commas, line breaks, or other special characters that need to be escaped.

Binary Format: <br>
Pros:
* More space-efficient, can be optimized for the data structure.
* Faster read/write times because the data doesn't need to be parsed or converted from text.
* Can handle complex data structures and relationships.
* Better control over how data is encoded and structured.

Cons:
* Not human-readable, which makes debugging and manual editing challenging.
* Requires a defined schema or structure so that the data can be interpreted correctly by applications.
* May not be as portable across different systems without careful design.

For binary:
* Decide on a binary format that suits your data (e.g., fixed-width fields, length-prefixed fields, or a more complex structure like a serialized object graph).
* Write methods to serialize your data to a binary format and deserialize it back.
* Implement file I/O methods to write binary data to a file and read binary data from a file.

When implementing storage for these data types, especially in a binary format, we need to think about:

* How to represent each type in bytes.
* How much space each type will take up.
* How you'll serialize (convert to a byte representation) and deserialize (convert back to the original representation) each type.
* Possible padding or alignment requirements in the binary data for efficient access.

Considered the following decisions:

1. Text (Strings):
   * Representation: Text can be represented as a sequence of bytes, with one common approach being to use UTF-8 encoding.
   * Space: The space text occupies depends on its length and the encoding. UTF-8 uses 1 byte for standard ASCII characters and more for other characters.
   * Serialization: Convert the string to bytes using the chosen encoding (e.g., string.getBytes("UTF-8") in Java).
   * Deserialization: Construct a new string from the byte array with the same encoding (e.g., new String(bytes, "UTF-8")).
   * Padding/Alignment: Strings are often padded with a special character or length-prefixed so that you know how many bytes to read.
2. Numeric Types:
   * Representation: Integers can be represented in 4 bytes for int or 8 bytes for long. Floating-point numbers can use 4 bytes for float or 8 bytes for double.
   * Space: Fixed for the type - 4 or 8 bytes usually.
   * Serialization: Use ByteBuffer to convert numbers to byte arrays in Java (e.g., ByteBuffer.allocate(4).putInt(number).array()).
   * Deserialization: Read the appropriate number of bytes and use ByteBuffer to convert back (e.g., ByteBuffer.wrap(bytes).getInt()).
   * Padding/Alignment: Numeric types are often naturally aligned since they use a fixed number of bytes.
3. Date and Time:
   * Representation: Dates and times are commonly stored as the number of milliseconds since the Unix epoch in a long, which is 8 bytes.
   * Space: 8 bytes.
   * Serialization: Get the time in milliseconds and serialize it as you would a long.
   * Deserialization: Read the 8-byte value and convert it back into a date/time object.
   * Padding/Alignment: Not usually needed since long is 8 bytes.
4. Binary Data (Blobs):
   * Representation: Binary data is already in byte format, so it doesn't need encoding.
   * Space: The size is exactly the size of the binary data, but it can be very large.
   * Serialization: Write the length of the data followed by the data itself.
   * Deserialization: Read the length, then read that number of bytes to get the data.
   * Padding/Alignment: Usually, you'll length-prefix this type of data rather than pad it.
5. Boolean:
   * Representation: A single byte can be used, with 0 representing false and 1 representing true.
   * Space: 1 byte.
   * Serialization: Convert the boolean to a byte (byte value = (byte) (bool ? 1 : 0)).
   * Deserialization: Convert the byte back to a boolean (boolean bool = value != 0).
   * Padding/Alignment: No padding needed since it's a single byte.
   
For all these types, one common approach to creating a binary file format is to use a fixed-length record structure where each record has a fixed number of bytes for each field. If a field can have variable length (like text), you can either truncate/pad the text to a fixed length, or you can store the length of the field as part of the record.

Padding and alignment are important concepts in binary file formats and in-memory data structures for several reasons, primarily related to performance and access efficiency.

The Need for Padding/Alignment:

CPU Efficiency: Modern CPUs are designed to read data at specific alignment boundaries. For instance, reading a 4-byte integer is most efficient at an address that's a multiple of 4. If the data is misaligned, the CPU might need to read from multiple locations and then piece the data together, which is less efficient.

Predictable Data Structure: When data structures are padded to a certain alignment, each field starts at a predictable offset, which simplifies the calculation for locating a specific piece of data. This is akin to how you can access an element in an array if you know the starting address, the index, and the size of each element.

Example of Padding/Alignment:
Let's say we have a record that contains a 1-byte boolean, a 2-byte short, and a 4-byte int. If you were to store these back-to-back, the start of the int would be at byte 3, which is not a multiple of 4. Many systems can access this efficiently, but on some systems, this could be slower.

In real-world applications, both sequential access and random access are used, and each has scenarios where it is the preferred method.

Sequential Access:
This is the process of reading or writing information in the storage sequentially, starting from a certain point and continuing in order until the desired data is found or until the end is reached. Here's where it's typically used:

* Bulk Operations: When performing operations on a large dataset, such as batch processing, where every record needs to be accessed, sequential access is efficient because it minimizes seek time (the time it takes for the read/write head or equivalent to move to the location of the data).
* Streaming Data: Applications that stream data, like media players processing a video or audio file, benefit from sequential access because they read data in the same order that it's stored and played.
* Backups and Logs: Backing up data or writing to log files often involves writing data sequentially to a file.
* Data Import/Export: When importing or exporting data to and from databases, the data is often accessed sequentially.


Random Access: Random access allows you to jump directly to a piece of data without having to read through all the data before it. Use cases include:

* Databases: Most database queries involve looking up specific records without reading the entire dataset. Indexes are used to facilitate this by providing a way to jump directly to the record or records of interest.
* File Systems: Operating systems use random access when reading or writing files where the location is known, such as opening a document by clicking on it.
* Interactive Applications: Applications that allow users to interact and manipulate data, like a spreadsheet program, rely on random access to immediately locate and modify data based on user input.

Choosing Between Sequential and Random Access:

* Performance Considerations: Sequential access can be more efficient than random access because it reduces overhead associated with seeking. Conversely, random access provides faster data retrieval when direct access to any part of the data is needed.
* Storage Medium: The underlying storage medium can influence the choice as well. For instance, SSDs (solid-state drives) have quick seek times, making random access relatively efficient compared to HDDs (hard disk drives).

A database might support both:

* Sequential Access: For operations that benefit from reading or writing data in sequence, databases use simple file reads and writes that start at one point and continue linearly. This is common in full table scans, data export operations, and when reading from or writing to log files.
* Random Access: For operations that require access to specific records quickly, without reading the entire data set, databases use indexing structures like B-trees, hash tables, or other forms of indexes that allow for fast lookups. When a query is executed, the database can use these indexes to find the exact location of the data on disk and access it directly.
* Combination of Both: A common pattern is to use an index to quickly locate the starting point of the desired data (random access) and then read sequentially from there if multiple contiguous records meet the query criteria.

Example Scenario:
* Imagine a database table that stores user data and includes a column for user IDs and a column for user names. If you want to retrieve all users whose names start with "A", the database might use an index on the user names to quickly find the first "A" (random access). After that, it could read sequentially through the table until it reaches names that no longer start with "A".

Can we store photos/files in each record ?

Typically, large binary data like photos are not stored directly in the database records due to size and performance considerations. Instead, the database stores a reference to the file's location on the disk, or uses a specialized mechanism like Binary Large Objects (BLOBs) to handle large binary data. 

Here are two common approaches:
* File Path Storage: Store only the file path in the database record. The actual file is stored on the file system. This method is simpler and keeps the database size manageable.
* BLOB Storage: If the database system supports BLOBs, you can store the binary data directly in the database. This approach can simplify data management but may affect performance and database size.

Ultimately, settled on this record structure:

* ID (4 bytes) 
* Name (32 bytes)
* Email (32 bytes)
* Date of Birth (8 bytes)
* IsActive (1 byte)

