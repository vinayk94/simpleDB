package org.dbproject.core;
import java.io.IOException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            // Initialize the heap file
            HeapFile heapFile = new HeapFile("users");

            // Create a user record
            UserRecord user = new UserRecord(1, "John Doe", "johndoe@example.com", LocalDate.of(1990, 1, 1), true);

            // Write the record
            heapFile.writeRecord(user);

            // Read the record
            UserRecord readUser = heapFile.readRecord(1);
            System.out.println(readUser);

            // Update the record
            UserRecord updatedUser = new UserRecord(1, "Jane Doe", "janedoe@example.com", LocalDate.of(1990, 1, 1), true);
            heapFile.updateRecord(1, updatedUser);

            // Read the updated record
            UserRecord readUpdatedUser = heapFile.readRecord(1);
            System.out.println(readUpdatedUser);

            // Close the heap file
            heapFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
