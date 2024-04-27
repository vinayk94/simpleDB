package org.dbproject.core;
import java.nio.ByteBuffer;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@ToString
public class UserRecord {
    public static final int RECORD_LENGTH = 77;
    private int id;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private boolean isActive;

    public UserRecord( int id, String name, String email, LocalDate dateOfBirth, boolean isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.isActive = isActive;
    }


    // Method to serialize this record to bytes
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(77); // the total size of all the fields
        buffer.putInt(id);
        buffer.put(fixedLengthString(name, 32));
        buffer.put(fixedLengthString(email, 32));
        buffer.putLong(dateOfBirth.toEpochDay()); // Convert LocalDate to a numerical representation
        buffer.put((byte) (isActive ? 1 : 0));
        return buffer.array();
    }

    // Method to deserialize a record from bytes
    public static UserRecord fromBytes(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        int id = buffer.getInt();
        String name = new String(bytes, 4, 32).trim(); // Adjust offsets as necessary
        String email = new String(bytes, 36, 32).trim(); // Adjust offsets as necessary
        LocalDate dateOfBirth = LocalDate.ofEpochDay(buffer.getLong(68));
        boolean isActive = buffer.get(76) != 0;

        return new UserRecord(id, name, email, dateOfBirth, isActive);
    }

    // Ensure that the string is exactly 'length' bytes
    private byte [] fixedLengthString(String string, int length) {
        byte[] stringBytes = new byte[length];
        byte[] originalBytes = string.getBytes();
        int toCopy = Math.min(originalBytes.length, stringBytes.length);
        System.arraycopy(originalBytes,0, stringBytes,0, toCopy);
        return stringBytes;


    }

}
