package model;

/**
 * The DestructiveWall class represents a class that holds
 * the destructive walls.
 * It holds the location and the power of the wall.
 *
 * @author MohammadaliKeshtparvar
 * @version 0.0
 */

public class DestructiveWall {

    private int wallPower;
    private int locX;
    private int locY;

    /**
     * Create a new DestructiveWall with given location of the wall.
     * @param locX the width of the wall.
     * @param locY the height of the wall.
     */
    public DestructiveWall(int locX, int locY) {
        this.wallPower = 20;
        this.locX = locX;
        this.locY = locY;
    }

    /**
     * Get the power of the wall.
     * @return the wallPower.
     */
    public int getWallPower() {
        return wallPower;
    }

    /**
     * Set the power of the wall.
     * @param wallPower the power of the wall.
     */
    public void setWallPower(int wallPower) {
        this.wallPower = wallPower;
    }

    /**
     * Get the width of the wall location.
     * @return the width of the wall location.
     */
    public int getLocX() {
        return locX;
    }

    /**
     * Get the height of the wall location.
     * @return the height of the wall location.
     */
    public int getLocY() {
        return locY;
    }
}
