package model.FileHandler;

import model.Player;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The ConfirmInformation class represents a class for signing in
 * and singing up the players.
 *
 * @author MohammadaliKeshtparvar
 * @version 0.0
 */

public class ConfirmInformation {

    private String name;
    private String password;
    private Player playingUser;

    /**
     * Create a object of ConfirmInformation with given
     * a name and its password.
     * @param name the name of the player.
     * @param password the password of the player.
     */
    public ConfirmInformation(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * This method use for signing in the players.
     * @return a boolean value.
     */
    public boolean confirmInfo() {
        try {
            ReadUserFromFile readUserFromFile = new ReadUserFromFile();
            if (readUserFromFile.existFile()) {
                while (true) {
                    Player userInFile = (Player) readUserFromFile.readFromFile();
                    if (userInFile.getName().equals(name) && userInFile.getPassword().equals(password)) {
                        playingUser = userInFile;
                        return true;
                    }
                }
            }else {
                return false;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method checks that the name of player is not iterative.
     * @return a boolean value.
     */
    public boolean createInfo() {
        try {
            ReadUserFromFile readUserFromFile = new ReadUserFromFile();
            if (readUserFromFile.existFile()) {
                try {
                    while (true) {
                        Player userInFile = (Player) readUserFromFile.readFromFile();
                        if (userInFile.getName().equals(name)) {
                            readUserFromFile.closeConnection();
                            return false;
                        }
                    }
                } catch (EOFException e) {
                    System.out.println(e);
                    readUserFromFile.closeConnection();
                }
            }else {
                readUserFromFile.closeConnection();
                return true;
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Sign up a new player and write into the file.
     * @throws IOException When the log file could not be created.
     */
    public void signUpUser() throws IOException {
        if (createInfo()) {
            Player user = new Player(name, password);
            playingUser = user;
            ArrayList<Player> users = new ArrayList<>();
            users.add(user);
            ReadUserFromFile readUserFromFile = new ReadUserFromFile();
            try {
                while (true) {
                    try {
                        Player userInFile = (Player) readUserFromFile.readFromFile();
                        users.add(userInFile);
                    }catch (EOFException q) {
                        System.out.println(q);
                        break;
                    }
                }
            } catch (FileNotFoundException | EOFException | ClassNotFoundException e) {
                e.printStackTrace();
                readUserFromFile.closeConnection();
            }

            try {
                WriteUserToFile writeUserToFile = new WriteUserToFile();
                for (Player u : users) {
                    writeUserToFile.writeToFile(u);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Get the player that created or chosen from file.
     * @return the player.
     */
    public Player getPlayingUser() {
        return playingUser;
    }
}
