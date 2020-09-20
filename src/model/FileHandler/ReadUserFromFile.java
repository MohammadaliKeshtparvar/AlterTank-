package model.FileHandler;

import java.io.*;

/**
 * The ReadUserFromFile class represents class in FileHandler
 * package that read player objects from file.
 *
 * @author MohammadaliKeshtparvar
 * @version 0.0
 */

public class ReadUserFromFile {

    private ObjectInputStream in;

    /**
     * Create a new ReadUserFromFile.
     * @throws IOException When the log file could not be created.
     */
    public ReadUserFromFile() throws IOException {
        try {
            in = new ObjectInputStream(new FileInputStream(new File("UserInformation.alter")));
        }catch (FileNotFoundException | EOFException e) {
            //System.out.println(e);
        }
    }

    /**
     * Read a object from file.
     * @return the object the read from file.
     * @throws ClassNotFoundException
     * @throws IOException When the log file could not be created.
     */
    public Object readFromFile() throws ClassNotFoundException, IOException {
        return in.readObject();
    }

    /**
     * Close the file after processing.
     * @throws IOException When the log file could not be created.
     */
    public void closeConnection() throws IOException {
        in.close();
    }

    /**
     * Check the file exists.
     * @return a boolean value.
     */
    public boolean existFile() {
        try {
            in = new ObjectInputStream(new FileInputStream(new File("UserInformation.alter")));
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (EOFException e) {
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
