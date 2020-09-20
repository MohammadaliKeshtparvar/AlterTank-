package model;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author MohammadaliKeshtparvar
 * @version 0.0
 */
public class VirtualPlayer extends Player{

    public VirtualPlayer(String name, String password) {
        super(name, password);
    }

    @Override
    public void update() {
        Random random = new Random();
        int ran = random.nextInt(10) + 1;
        if (ran == 1) {
            setLocY(getLocY() - (10 * Math.cos(Math.toRadians(getDegree()))));
            setLocX(getLocX() - (10 * Math.sin(Math.toRadians(getDegree()))));
        }
        if (ran == 2) {
            setLocY(getLocY() + (10 * Math.cos(Math.toRadians(getDegree()))));
            setLocX(getLocX() - (10 * Math.sin(Math.toRadians(getDegree()))));
        }
        if (ran == 3) {
            setDegree(getDegree() - 30);
            if (getDegree() < 0)
                setDegree(360);
        }
        if (ran == 4) {
            setDegree(getDegree() + 30);
            if (getDegree() > 360)
                setDegree(0);
        }
        /*if (ran == 5) {
            Timer timer = new Timer();
            setBulletDegree(getDegree());
            TimerTask timerTask = new Bullet(getBulletDegree(), (int) getLocX(), (int) getLocY());
            timer.schedule(timerTask, 0, 40);
            Bullet b = (Bullet) timerTask;
            b.setDegree(getBulletDegree());
            getBulletCollection().addBullet(b);
            ThreadPool.init();
            ThreadPool.execute(b);
        }*/

        setLocX(Math.max(getLocX(), 138));
        setLocX(Math.min(getLocX(), 1068));
        setLocY(Math.max(getLocY(), 120));
        setLocY(Math.min(getLocY(), 525));
    }
}
