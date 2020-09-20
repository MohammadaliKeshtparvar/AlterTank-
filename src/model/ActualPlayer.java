package model;

import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The ActualPlayer represents a class in tank trouble game.
 * It extends the player class.
 *
 * @author MohammadaliKeshtparvar
 * @version 0.0
 */

public class ActualPlayer extends Player{

    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private boolean mousePress;
    private int mouseX, mouseY;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;

    /**
     * Create a new ActualPlayer with given a name and a password.
     * @param name the name of the ActualPlayer.
     * @param password the password of the ActualPlayer.
     */
    public ActualPlayer(String name, String password) {
        super(name, password);
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();

        keyUP = false;
        keyDOWN = false;
        keyRIGHT = false;
        keyLEFT = false;
        //
        mousePress = false;
        mouseX = 0;
        mouseY = 0;
    }

    /**
     * Override the update method.
     */
    @Override
    public void update() {
        if (mousePress) {
            setLocY(mouseY - getUser().getTank().getImage().getHeight());
            setLocX(mouseX - getUser().getTank().getImage().getWidth());
        }
        if (keyUP) {
            setLocY(getLocY() - (getUser().getTank().getSpeed() * Math.cos(Math.toRadians(getDegree()))));
            setLocX(getLocX() + (getUser().getTank().getSpeed() * Math.sin(Math.toRadians(getDegree()))));
        }
        if (keyDOWN) {
            setLocY(getLocY() + (getUser().getTank().getSpeed() * Math.cos(Math.toRadians(getDegree()))));
            setLocX(getLocX() - (getUser().getTank().getSpeed() * Math.sin(Math.toRadians(getDegree()))));
        }
        if (keyRIGHT) {
            setDegree(getDegree() - 8);
            if (getDegree() < 0)
                setDegree(360);
        }
        if (keyLEFT) {
            setDegree(getDegree() + 8);
            if (getDegree() > 360)
                setDegree(0);
        }

        setLocX(Math.max(getLocX(), 130));
        setLocX(Math.min(getLocX(), 1068));
        setLocY(Math.max(getLocY(), 120));
        setLocY(Math.min(getLocY(), 525));
    }

    /**
     * The KeyHandler class is a inner class that extends KeyAdaptor.
     */
    class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    keyUP = true;
                    break;
                case KeyEvent.VK_DOWN:
                    keyDOWN = true;
                    break;
                case KeyEvent.VK_LEFT:
                    keyLEFT = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    keyRIGHT = true;
                    break;

                case KeyEvent.VK_M:
                    try {
                        if (getPrize().getType() == 2) {
                            Timer timer = new Timer();
                            setBulletDegree(getDegree());
                            TimerTask timerTask = new Bullet(getBulletDegree(), (int) getLocX(), (int) getLocY());
                            timer.schedule(timerTask, 0, 10);
                            Bullet b = (Bullet) timerTask;
                            b.setSpeed(3 * b.getSpeed());
                            b.setDegree(getBulletDegree());
                            getBulletCollection().addBullet(b);
                            ThreadPool.init();
                            ThreadPool.execute(b);
                            break;
                        } else {
                            Timer timer = new Timer();
                            setBulletDegree(getDegree());
                            TimerTask timerTask = new Bullet(getBulletDegree(), (int) getLocX(), (int) getLocY());
                            timer.schedule(timerTask, 0, 30);
                            Bullet b = (Bullet) timerTask;
                            b.setDegree(getBulletDegree());
                            getBulletCollection().addBullet(b);
                            ThreadPool.init();
                            ThreadPool.execute(b);
                            break;
                        }
                    } catch (NullPointerException ee) {
                        Timer timer = new Timer();
                        setBulletDegree(getDegree());
                        TimerTask timerTask = new Bullet(getBulletDegree(), (int) getLocX(), (int) getLocY());
                        timer.schedule(timerTask, 0, 40);
                        Bullet b = (Bullet) timerTask;
                        b.setDegree(getBulletDegree());
                        getBulletCollection().addBullet(b);
                        ThreadPool.init();
                        ThreadPool.execute(b);
                        break;
                    }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_UP:
                    keyUP = false;
                    break;
                case KeyEvent.VK_DOWN:
                    keyDOWN = false;
                    break;
                case KeyEvent.VK_LEFT:
                    keyLEFT = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    keyRIGHT = false;
                    break;
            }
        }

    }

    /**
     * The MouseHandler class is a inner class
     * that extends MouseAdaptor.
     */
    class MouseHandler extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            mousePress = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mousePress = false;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }

    /**
     * Get the keyHandler of the ActualPlayer.
     * @return the keyHandler of the ActualPlayer.
     */
    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    /**
     * Get the mouseHandler of ActualPlayer.
     * @return the mouseHandler of ActualPlayer.
     */
    public MouseHandler getMouseHandler() {
        return mouseHandler;
    }

    /**
     * Get the mouseMotionListener of ActualPlayer.
     * @return the mouseMotionLister of ActualPlayer.
     */
    public MouseMotionListener getMouseMotionListener() {
        return mouseHandler;
    }
}
