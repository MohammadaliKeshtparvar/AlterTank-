package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The BulletCollection represents a class in tank trouble game.
 * It holds a array list of the Bullets.
 *
 * @author MohammadaliKeshtparvar
 * @version 0.0
 */

public class BulletCollection implements Runnable, Serializable {

    private ArrayList<Bullet> bullets;

    /**
     * Create a new BulletCollection.
     */
    public BulletCollection() {
        bullets = new ArrayList<>();
    }

    /**
     * The run method remove some bullets.
     */
    @Override
    public void run() {
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet b = it.next();
            if (b.getCounter() > 100 || b.isRemove()) {
                it.remove();
            }
        }
    }

    /**
     * Add a new Bullet to the array list.
     * @param bullet the bullet that added to the array list.
     */
    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    /**
     * Set the Bullets with given a array list.
     * @param bullets the bullets of the BulletCollection.
     */
    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

    /**
     * Get the array list of the bullets.
     * @return the array list of the bullets.
     */
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}
