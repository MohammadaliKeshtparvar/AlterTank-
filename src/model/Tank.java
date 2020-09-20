package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author MohammadaliKeshtparvar
 * @version 0.0
 */

public class Tank implements Serializable {

    private int power;
    private int life;
    private transient BufferedImage image;
    private int speed;

    public Tank () {
        power = 0;
        life = 30;
        try {
            image = ImageIO.read(new File("PNG\\tank_sand.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.speed = 8;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
