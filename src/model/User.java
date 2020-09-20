package model;


import java.io.Serializable;

/**
 * @author MohammadaliKeshtparvar
 * @version 0.0
 */

public class User implements Serializable {

    private int winsComputer;
    private int lossesComputer;
    private int winsNetwork;
    private int lossesNetwork;
    private String name;
    private String password;
    // The times that user plays.
    private int minutes;
    private Tank tank;
    private boolean save;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        winsComputer = 0;
        lossesComputer = 0;
        winsNetwork = 0;
        lossesNetwork = 0;
        minutes = 0;
        tank = new Tank();
        save = false;
    }


    public int getLossesComputer() {
        return lossesComputer;
    }


    public int getLossesNetwork() {
        return lossesNetwork;
    }


    public int getMinutes() {
        return minutes;
    }


    public String getName() {
        return name;
    }


    public String getPassword() {
        return password;
    }


    public Tank getTank() {
        return tank;
    }


    public int getWinsComputer() {
        return winsComputer;
    }


    public int getWinsNetwork() {
        return winsNetwork;
    }

}
