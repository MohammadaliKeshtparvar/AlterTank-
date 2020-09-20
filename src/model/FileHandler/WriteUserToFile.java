package model.FileHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * The WriteUserToFile class represents a class in FileHandler package
 * that write the objects in to the file.
 *
 * @author MohammadaliKeshtparvar
 * @version 0.0
 */

public class WriteUserToFile {
    private ObjectOutputStream out;

    /**
     * Create a new file .
     * @throws FileNotFoundException if file does not exist.
     * @throws IOException When the log file could not be created.
     */
    public WriteUserToFile() throws FileNotFoundException, IOException {
        out = new ObjectOutputStream(new FileOutputStream(new File("UserInformation.alter")));
    }

    /**
     * Write a object into the file.
     * @param o the object that written into the file.
     * @throws IOException When the log file could not be created.
     */
    public void writeToFile(Object o) throws IOException {
        out.writeObject(o);
    }

    /**
     * Closre the file after processing.
     * @throws IOException When the log file could not be created.
     */
    public void closeConnection() throws IOException {
        out.close();
    }
}
