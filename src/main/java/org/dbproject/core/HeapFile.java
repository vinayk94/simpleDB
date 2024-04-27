package org.dbproject.core;


import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class HeapFile {
    private final Path filePath;
    private final RandomAccessFile file;
    private int highestIdWritten = -1; // Tracks the highest ID written to the file


    public HeapFile(String fileName) throws IOException {
        this.filePath = Paths.get(fileName + ".db");
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
        this.file = new RandomAccessFile(filePath.toFile(), "rw");
    }

    public void writeRecord(UserRecord record) throws IOException{
        int recordId = record.getId();
        if (recordId < 0) {
            throw new IllegalArgumentException("Record ID must be non-negative.");
        }
        if (recordId != highestIdWritten + 1) {
            throw new IllegalArgumentException("Records must be written in sequential order.");
        }

        file.seek(file.length()); // Go to the end of the file
        file.write(record.toBytes());
        highestIdWritten = recordId; // Update the highest ID written
    }

    public UserRecord readRecord(int id) throws IOException {
        if (id < 0) {
            throw new IllegalArgumentException("Record ID must be non-negative.");
        }
        if (id > highestIdWritten) {
            throw new IOException("Record ID has not been written yet.");
        }
        long position = (long) id * UserRecord.RECORD_LENGTH; // Calculate the position

        file.seek(position);
        byte[] bytes = new byte[UserRecord.RECORD_LENGTH];
        file.readFully(bytes);
        return UserRecord.fromBytes(bytes);
    }

    public void updateRecord(int id, UserRecord updatedRecord) throws IOException {

        if (id < 0 || id > highestIdWritten) {
            throw new IllegalArgumentException("Record ID must be within the range of written records.");
        }


        long position = (long) id * UserRecord.RECORD_LENGTH; // Calculate the position
        if (position >= file.length()) {
            throw new IOException("No record found at the ID position");
        }

        file.seek(position);
        file.write(updatedRecord.toBytes());
    }

    // Close file when done
    public void close() throws IOException{
        file.close();
    }
}
