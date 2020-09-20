package model;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

/**
 * The player class represents a class for showing a player in this game.
 * This class implements serializable.
 *
 * @author MohammadaliKeshtparvar
 * @version 0.0
 */

public class Player implements Serializable {

    private transient double locX, locY;
    private User user;
    private String name;
    private String password;
    private transient int degree;
    private transient int bulletDegree;
    private BulletCollection bulletCollection;
    private transient boolean remove;
    private Prize prize;
    private boolean havePrize;

    /**
     * Create a new Player with given tow strings.
     * @param name the name of player.
     * @param password the password of player.
     */
    public Player(String name, String password) {
        Random random = new Random();
        gettingStart();
        degree = random.nextInt(360);
        this.name = name;
        this.password = password;
        user = new User(this.name, this.password);
        bulletCollection = new BulletCollection();
        this.remove = false;
        this.havePrize = false;
    }

    /**
     * Get the BulletCollection.
     * @return the BulletCollection.
     */
    public BulletCollection getBulletCollection() {
        return bulletCollection;
    }

    /**
     * Set the BulletCollection with given a new BulletCollection.
     * @param bulletCollection the BulletCollection of player.
     */
    public void setBulletCollection(BulletCollection bulletCollection) {
        this.bulletCollection = bulletCollection;
    }

    /**
     * Get the degree of bullet.
     * @return the degree of bullet.
     */
    public int getBulletDegree() {
        return bulletDegree;
    }

    /**
     * Set the degree of bullet.
     * @param bulletDegree the degree of bullet.
     */
    public void setBulletDegree(int bulletDegree) {
        this.bulletDegree = bulletDegree;
    }

    /**
     * Get the degree of moving.
     * @return the degree of moving.
     */
    public int getDegree() {
        return degree;
    }

    /**
     * Set the degree of moving.
     * @param degree the degree of moving.
     */
    public void setDegree(int degree) {
        this.degree = degree;
    }

    /**
     * Get the x value of location.
     * @return the locX.
     */
    public double getLocX() {
        return locX;
    }

    /**
     * Set the width of the player location.
     * @param locX the width of player.
     */
    public void setLocX(double locX) {
        this.locX = locX;
    }

    /**
     * Get the height of player location.
     * @return the locY.
     */
    public double getLocY() {
        return locY;
    }

    /**
     * Set the height of the player location.
     * @param locY the width of player.
     */
    public void setLocY(double locY) {
        this.locY = locY;
    }

    /**
     * Get the name of the player.
     * @return the name of player.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the player.
     * @param name the name of player.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the password of player.
     * @return the password of the player.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of the player.
     * @param password the password of the player.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the User of the player.
     * @return the user of the player.
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the user of the player.
     * @param user of the player.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * The method which updates the game state.
     */
    public void update() {
    }

    /**
     * Get the remove state.
     * @return a boolean value.
     */
    public boolean isRemove() {
        return remove;
    }

    /**
     * Set the remove value with given a boolean value.
     * @param remove the state of removing.
     */
    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    /**
     * Set the random place for start game.
     */
    public void gettingStart() {
        Random random = new Random();
        try {
            String[] maze = new String[countLines()];
            setArray(maze);
            while (true) {
                locX = random.nextInt(1024) + 100;
                if (locX < 110)
                    locX += 20;
                locY = random.nextInt(506) ;
                int counter = 0;
                for (int i = 0; i < 7; i++) {
                    char[] chars = maze[i].toCharArray();
                    for (int j = 0; j < 15; j++) {
                        if (chars[j] != '0') {
                            if (locX <= 196 + j * 64 && locX >= 109 + j * 64 && locY <= 175 + i * 64 && locY >= 79 + i * 64) {
                                counter++;
                            }
                        }
                    }
                }
                if (counter == 0) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Count the lines of the string file.
     * @return the number of lines.
     * @throws IOException When the log file could not be created.
     */
    public int countLines() throws IOException {
        File file = new File("map.txt");
        int lines = 0;

        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[8 * 1024]; // BUFFER_SIZE = 8 * 1024
        int read;

        while ((read = fis.read(buffer)) != -1) {
            for (int i = 0; i < read; i++) {
                if (buffer[i] == '\n') lines++;
            }
        }
        fis.close();
        return lines;
    }

    /**
     * Read the string file and assign its to maze.
     * @param maze the array of string.
     * @throws IOException When the log file could not be created.
     */
    public void setArray(String[] maze) throws IOException {
        try (Scanner scanner = new Scanner(new FileReader("map.txt"))) {
            int noOfLines = 0;
            while (scanner.hasNextLine()) {
                maze[noOfLines] = scanner.nextLine();
                noOfLines++;
            }
        }
    }

    /**
     * Get the prize of the player.
     * @return the prize of the player.
     */
    public Prize getPrize() {
        return prize;
    }

    /**
     * Set the prize of the player.
     * @param prize the prize of the player.
     */
    public void setPrize(Prize prize) {
        this.havePrize = true;
        this.prize = prize;
    }

    /**
     * Get the state of having prize.
     * @return a boolean value.
     */
    public boolean isHavePrize() {
        return havePrize;
    }

    /**
     * Set the havePrize value.
     * @param havePrize the havePrize of the player.
     */
    public void setHavePrize(boolean havePrize) {
        this.havePrize = havePrize;
    }

    /**
     * Check the time of prize is finished or no.
     */
    public void checkPrize() {
        try {
            if (prize.getCounter() >= 100 || prize.getType() == 0) {
                havePrize = false;
            }
        }catch (NullPointerException e) {
            havePrize = false;
        }
    }
}
