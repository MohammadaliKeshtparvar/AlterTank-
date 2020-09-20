package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Note that to make this work, none of the 2 methods
 * in the while loop (update() and render()) should be
 * long running! Both must execute very quickly, without
 * any waiting and blocking!
 *
 * @author MohammadaliKeshtparvar
 * @version 0.0
 */
public class GameLoop implements Runnable {

    /**
     * Frame Per Second.
     * Higher is better, but any value above 24 is fine.
     */
    public static final int FPS = 30;

    private GameFrame canvas;
    private GameState state;

    public GameLoop(GameFrame frame) {
        canvas = frame;
    }

    public void init() {
        state = new GameState();
        ActualPlayer a = new ActualPlayer("ali", "mohammadi");
       // VirtualPlayer v = new VirtualPlayer("hasan", "yazdani");
        VirtualPlayer v1 = new VirtualPlayer("kkk", "kkkkk");
        state.addPlayer(a);
        //state.addPlayer(v);
        state.addPlayer(v1);
        try {
            BufferedImage image = ImageIO.read(
                    new File("PNG\\tank_blue.png"));
            v1.getUser().getTank().setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (state.getPlayers().get(0) instanceof ActualPlayer) {
            canvas.addKeyListener(((ActualPlayer) state.getPlayers().get(0)).getKeyHandler());
            canvas.addMouseListener(((ActualPlayer) state.getPlayers().get(0)).getMouseHandler());
            canvas.addMouseMotionListener(((ActualPlayer) state.getPlayers().get(0)).getMouseMotionListener());
        }
        canvas.addKeyListener(state.getKeyEscape());
    }


    @Override
    public void run() {
        boolean gameOver = false;
        while (!gameOver) {
            try {
                long start = System.currentTimeMillis();
                //
                for (Player p : state.getPlayers()) {
                    p.update();
                }

                canvas.render(state);
                gameOver = state.isGameOver();
                //
                long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
                if (delay > 0)
                    Thread.sleep(delay);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
        canvas.render(state);
    }
}

