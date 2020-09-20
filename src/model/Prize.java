package model;

import java.util.Random;
import java.util.TimerTask;

/**
 *
 * @author MohammadaliKeshtparvar
 * @version 0.0
 */

public class Prize extends TimerTask implements Runnable{

    private int locationX;
    private int locationY;
    private int type;
    private int counter;
    private boolean remove;


    public Prize(int locationX, int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
        Random random = new Random();
       // type = random.nextInt(5) + 1;
        type = 2;
        this.counter = 0;
        remove = false;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    @Override
    public void run() {
        if (type == 1) {
            if (counter <= 100) {
                counter++;
            }else{
                type = 0;
                cancel();
            }
        }
        if (type == 2) {
            if (counter <= 100) {
                counter++;
            }else {
                type = 0;
                cancel();
            }
        }

    }

    @Override
    public boolean cancel() {
        return super.cancel();
    }

    @Override
    public long scheduledExecutionTime() {
        return super.scheduledExecutionTime();
    }

    public int getCounter() {
        return counter;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }
}
