package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.TimerTask;

/**
 * The Bullet class represents a class in tank trouble game.
 * The Bullet class extends TimerTask and implements Runnable.
 *
 * @author MohammadaliKeshtparvar
 * @version 0.0
 */

public class Bullet extends TimerTask implements Runnable{

    private double valX;
    private double valY;
    private double speedX, speedY;
    private int speed;
    private int degree;
    private int counter;
    private boolean remove;
    private String[] maze;
    private int bulletPower;

    /**
     * Create a new Bullet with given three integers.
     * @param degree the degree of the Bullet.
     * @param valX the width of the Bullet.
     * @param valY th height of the Bullet.
     */
    public Bullet(int degree, int valX, int valY) {
        this.speed = 14;
        speedX = speed * Math.sin(Math.toRadians(degree));
        speedY = speed * Math.cos(Math.toRadians(degree));
        this.degree = degree;
        this.valX = valX;
        this.valY = valY;
        this.counter = 0;
        this.remove = false;
        try {
            this.maze = new String[countLines()];
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            setMaze();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.bulletPower = 10;
    }

    /**
     * Get the width of the Bullet.
     * @return the width of the Bullet.
     */
    public double getValX() {
        return valX;
    }

    /**
     * Get the height of the Bullet.
     * @return the height of the Bullet.
     */
    public double getValY() {
        return valY;
    }

    /**
     * Moving the Bullet handled in this method.
     */
    @Override
    public void run() {
        if (counter <= 100) {
            hittingWall();
            if (valY < 120 || valY > 560) {
                reflexY();
            }
            setValX();
            if (valX < 120 || valX > 1100) {
                reflexX();
            }
            setValY();
            counter++;
        }else
            cancel();
    }

    /**
     * Set the degree of the Bullet.
     * @param degree the degree of Bullet.
     */
    public void setDegree(int degree) {
        this.degree = degree;
    }

    /**
     * Hitting the bullet on the wall handled in this method.
     */
    public void hittingWall() {
        for (int i = 0; i < 7; i++) {
            char[] chars = maze[i].toCharArray();
            for (int j = 0; j < 15; j++) {
                if (chars[j] == '2') {
                    if (getValX() <= 196+j*64 && getValX() > 109+j*64) {
                        if (getValY() <= 177+i*64 && getValY() > 103+i*64) {
                            double[] m = new double[4];
                            m[0] = getValX() - 109 - j * 64;
                            m[1] = 196 + j * 64 - getValX();
                            m[2] = getValY() - 103 - i * 64;
                            m[3] = 177 + i * 64 - getValY();
                            double min = findMin(m);
                            boolean f = true;
                            if (min  == getValX() - 109 - j * 64) {
                                reflexX();
                                f = false;
                            }
                            if (min == 196 + j * 64 - getValX() && f) {
                                reflexX();
                                f = false;
                            }
                            if (min == getValY() - 103 - i * 64 && f) {
                                reflexY();
                                f = false;
                            }
                            if (min == 177 + i * 64 - getValY() && f) {
                                reflexY();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Set the x speed of the moving.
     */
    public void setValX() {
        this.valX -= speedX;
    }

    /**
     * Set the y speed of the moving.
     */
    public void setValY() {
        this.valY += speedY ;
    }

    /**
     * Cancel the moving of the bullet.
     * @return cancel object.
     */
    @Override
    public boolean cancel() {
        return super.cancel();
    }

    /**
     * Scheduled the time of execute.
     * @return the scheduledExecutionTime object.
     */
    @Override
    public long scheduledExecutionTime() {
        return super.scheduledExecutionTime();
    }

    /**
     * Get the counter.
     * @return the counter.
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Get the degree of the bullet.
     * @return the degree of the bullet.
     */
    public int getDegree() {
        return degree;
    }

    /**
     * Get the state of the bullet.
     * @return the state of the bullet.
     */
    public boolean isRemove() {
        return remove;
    }

    /**
     * Set the state of the removing bullet.
     * @param remove the state of the removing bullet.
     */
    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    /**
     * Reflex the x_speed of the bullet.
     */
    public void reflexX() {
        speedX = -speedX;
    }

    /**
     * Reflex the y_speed of the bullet.
     */
    public void reflexY() {
        speedY = -speedY;
    }

    /**
     * Count the numbers of the string file.
     * @return the numbers of the bullet.
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
     * Set the maze array of the bullet with reading a string file.
     * @throws IOException When the log file could not be created.
     */
    public void setMaze() throws IOException {
        try (Scanner scanner = new Scanner(new FileReader("map.txt"))) {
            int noOfLines = 0;
            while (scanner.hasNextLine()) {
                maze[noOfLines] = scanner.nextLine();
                noOfLines++;
            }
        }
    }

    /**
     * Find the less value of a array.
     * @param m the array that we want min of its.
     * @return the min of this array.
     */
    public double findMin(double[] m) {
        for (int i = 0; i < 4; i++) {
            for (int j = i; j < 4; j++) {
                if (m[i] > m[j]) {
                    double temp = m[i];
                    m[i] = m[j];
                    m[j] = temp;
                }
            }
        }
        return m[0];
    }

    /**
     * Set the speed of the bullet.
     * @param speed the speed of the bullet.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Get the speed of the bullet.
     * @return the speed of the bullet.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Get the bullet power.
     * @return the bulletPower.
     */
    public int getBulletPower() {
        return bulletPower;
    }

    /**
     * Set the bulletPower.
     * @param bulletPower the bulletPower of the bullet.
     */
    public void setBulletPower(int bulletPower) {
        this.bulletPower = bulletPower;
    }
}
