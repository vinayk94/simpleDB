package org.dbproject.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class HeapFileTest {
    private HeapFile heapFile;

    @BeforeEach
    void setUp() throws IOException {
        // Setup method that runs before each test.
        heapFile = new HeapFile("testUsers");
    }

    @AfterEach
    void tearDown() throws IOException {
        // Teardown method that runs after each test.
        heapFile.close();
        Files.deleteIfExists(Paths.get("testUsers.db"));
    }


    @Test
    void writeReadUpdateRecord() throws IOException {
        // Arrange
        int id = 0;
        UserRecord originalUser = new UserRecord(id, "John Doe", "johndoe@example.com", LocalDate.of(1990, 1, 1), true);

        // Act
        heapFile.writeRecord(originalUser); // Write the record

        // Assert
        UserRecord readUser = heapFile.readRecord(id); // Read the record
        assertNotNull(readUser);
        assertEquals(originalUser.getName(), readUser.getName());

        // Act
        UserRecord updatedUser = new UserRecord(id, "Jane Doe", "janedoe@example.com", LocalDate.of(1990, 1, 1), false);
        heapFile.updateRecord(id, updatedUser); // Update the record

        // Assert
        UserRecord readUpdatedUser = heapFile.readRecord(id); // Read the updated record
        assertNotNull(readUpdatedUser);
        assertEquals(updatedUser.getName(), readUpdatedUser.getName());
        assertFalse(readUpdatedUser.isActive());
    }
}
