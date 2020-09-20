package model;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;


/**
 * The GameFrame class is the main class in this program.
 * It holds the rolls of tank trouble game.
 *
 * The window on which the rendering is performed.
 * This example uses the modern BufferStrategy approach for double-buffering,
 * actually it performs triple-buffering!
 *
 * @author MohammadaliKeshtparvar
 * @version 0.0
 */
public class GameFrame extends JFrame {

    public static final int GAME_HEIGHT = 720;                  // 720p game resolution
    public static final int GAME_WIDTH = 16 * GAME_HEIGHT / 9;  // wide aspect ratio
    private long lastRender;
    private ArrayList<Float> fpsHistory;
    private BufferStrategy bufferStrategy;
    private String[] maze;
    private ArrayList<Prize> prizes;
    private ArrayList<DestructiveWall> walls;

    /**
     * Create a new GameFrame with given a string.
     * @param title the name of the frame.
     */
    public GameFrame(String title) {
        super(title);
        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        lastRender = -1;
        fpsHistory = new ArrayList<>(100);
        try {
            maze = new String[countLines()];
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            setMaze();
        } catch (IOException e) {
            e.printStackTrace();
        }
        prizes = new ArrayList<>();
        walls = new ArrayList<>();
        setWalls();
    }

    /**
     * This must be called once after the JFrame is shown:
     *    frame.setVisible(true);
     * and before any rendering is started.
     */
    public void initBufferStrategy() {
        // Triple-buffering
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }

    /**
     * Game rendering with triple-buffering using BufferStrategy.
     */
    public void render(GameState state) {
        // Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {
                // Get a new graphics context every time through the loop
                // to make sure the strategy is validated
                Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    doRendering(graphics, state);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // Dispose the graphics
                    graphics.dispose();
                }
                // Repeat the rendering if the drawing buffer contents were restored
            } while (bufferStrategy.contentsRestored());

            // Display the buffer
            bufferStrategy.show();
            // Tell the system to do the drawing NOW;
            // otherwise it can take a few extra ms and will feel jerky!
            Toolkit.getDefaultToolkit().sync();

            // Repeat the rendering if the drawing buffer was lost
        } while (bufferStrategy.contentsLost());
    }

    /**
     * Rendering all game elements based on the game state.
     * This method include the rolls of tank trouble game.
     */
    private void doRendering(Graphics2D g2d, GameState state) throws InterruptedException {

        // Draw background
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        drawMap(g2d);

        // Draw ball
        hitting(g2d, state);
        state.removePlayer();
        hittingWall(g2d, state);
        try {
            moving(state);
        } catch (IOException e) {
            e.printStackTrace();
        }
        drawing(g2d, state);

        for (Player p : state.getPlayers()) {
            p.checkPrize();
        }

        Random random = new Random();
        int rndPrize = random.nextInt(300);
        if (rndPrize == 5) {
            int prizeX ;
            int prizeY ;
            while (true) {
                prizeX = random.nextInt(1024) + 100;
                if (prizeX < 121)
                    prizeX += 40;
                if (prizeX > 1050) {
                    prizeX -= 25;
                }
                prizeY = random.nextInt(506) + 80;
                if (prizeY < 100) {
                    prizeY += 45;
                }
                if (prizeY > 535) {
                    prizeY -= 38;
                }
                int counter = 0;
                for (int i = 0; i < 7; i++) {
                    char[] chars = maze[i].toCharArray();
                    for (int j = 0; j < 15; j++) {
                        if (chars[j] == '1' || chars[j] == '2') {
                            if (prizeX <= 196 + j * 64 && prizeX >= 109 + j * 64 && prizeY <= 175 + i * 64 && prizeY >= 79 + i * 64) {
                                counter++;
                            }
                        }
                    }
                }
                if (counter == 0) {
                    break;
                }
            }
            Prize p = new Prize(prizeX, prizeY);
            prizes.add(p);
        }

        for (Prize p : prizes) {
            try {
                BufferedImage prizeImage = ImageIO.read(new File("PNG\\crateWood.png"));
                g2d.drawImage(prizeImage, p.getLocationX(), p.getLocationY(), this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        gettingPrize(state);

        /*g2d.drawImage(state.getPlayers().get(0).getUser().getTank().getImage(), 200, 610, this);
        g2d.setColor(Color.WHITE);
        Font font = new Font("Arial", Font.PLAIN, 16);
        g2d.setFont(font);
        g2d.drawString(, 205, 625+state.getPlayers().get(0).getUser().getTank().getImage().getHeight());*/

        // Print FPS info
        long currentRender = System.currentTimeMillis();
        if (lastRender > 0) {
            fpsHistory.add(1000.0f / (currentRender - lastRender));
            if (fpsHistory.size() > 100) {
                fpsHistory.remove(0); // remove oldest
            }
            float avg = 0.0f;
            for (float fps : fpsHistory) {
                avg += fps;
            }

            g2d.setColor(Color.CYAN);
            g2d.setFont(g2d.getFont().deriveFont(18.0f));
            int strHeight = g2d.getFontMetrics().getHeight();
        }
        lastRender = currentRender;
        // Print user guide

        g2d.setFont(g2d.getFont().deriveFont(18.0f));
        // Draw GAME OVER
        if (state.isGameOver()) {
            String str = "GAME OVER";
            g2d.setColor(Color.WHITE);
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
            int strWidth = g2d.getFontMetrics().stringWidth(str);
            g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
        }
    }

    /**
     * Draw the main ground of the game.
     * @param g2d the Graphic2D of this frame.
     * @param state the GameState of the GameFrame.
     */
    public void drawing(Graphics2D g2d , GameState state) {
        for (Player p : state.getPlayers()) {
            try {
                for (Bullet b : p.getBulletCollection().getBullets()) {
                    if (b.getCounter() <= 2) {
                        try {
                            BufferedImage bufferedImage = ImageIO.read(
                                    new File("PNG\\shotThin.png"));
                            bufferedImage = rotateImageByDegrees(bufferedImage, b.getDegree());
                            g2d.drawImage(bufferedImage, (int) b.getValX() + 18, (int) b.getValY()+5, null);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else {
                        g2d.fillOval((int) b.getValX() + 18, (int) b.getValY(), 12, 12 );
                    }
                }
            } catch (ConcurrentModificationException e) {
                System.out.println(e);
            }
            p.getBulletCollection().run();
            try {
                if (p.getPrize().getType() == 1) {
                    if (p.getPrize().getCounter() == 0) {
                        Timer timer = new Timer();
                        timer.schedule(p.getPrize(), 0, 150);
                        ThreadPool.init();
                        ThreadPool.execute(p.getPrize());
                    }
                    try {
                        BufferedImage bb = ImageIO.read(new File("PNG\\tank_darkLarge.png"));
                        p.getUser().getTank().setImage(bb);
                        BufferedImage image = rotateImageByDegrees(p.getUser().getTank().getImage(), p.getDegree());
                        g2d.drawImage(image, (int) p.getLocX(), (int) p.getLocY(), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                if (p.getPrize().getType() == 2) {
                    if (p.getPrize().getCounter() == 0) {
                        Timer timer = new Timer();
                        timer.schedule(p.getPrize(), 0, 100);
                        ThreadPool.init();
                        ThreadPool.execute(p.getPrize());
                    }
                }
                if (p.getPrize().getType() != 1) {
                    try {
                        BufferedImage b1 = ImageIO.read(new File("PNG\\tank_sand.png"));
                        BufferedImage b2 = ImageIO.read(new File("PNG\\tank_blue.png"));
                        if (p instanceof ActualPlayer) {
                            p.getUser().getTank().setImage(b1);
                        }else {
                            p.getUser().getTank().setImage(b2);
                        }
                        BufferedImage image = rotateImageByDegrees(p.getUser().getTank().getImage(), p.getDegree());
                        g2d.drawImage(image, (int) p.getLocX(), (int) p.getLocY(), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }catch (NullPointerException e) {
                BufferedImage image = rotateImageByDegrees(p.getUser().getTank().getImage(), p.getDegree());
                g2d.drawImage(image, (int) p.getLocX(), (int) p.getLocY(), null);
            }
        }
    }

    /**
     * Handle the action of the hitting the bullet on the tank.
     * @param g2d the Graphic2D of this frame.
     * @param state the GameState of the GameFrame.
     */
    public void hitting(Graphics2D g2d, GameState state) {
        for (Player p : state.getPlayers()) {
            try {
                for (Bullet b : p.getBulletCollection().getBullets()) {
                    for (Player pp : state.getPlayers()) {
                        if ((b.getValX() - pp.getLocX() < 20 && b.getValX() - pp.getLocX() > -20)
                                && (b.getValY() - pp.getLocY() < 20 && b.getValY() - pp.getLocY() > -20) && b.getCounter() > 5) {
                            try {
                                if (p.getPrize().getType() == 1) {
                                    b.setRemove(true);
                                }
                                if (p.getPrize().getType() == 2) {
                                    try {
                                        BufferedImage image = ImageIO.read(
                                                new File("PNG\\explosion3.png"));
                                        g2d.drawImage(image, (int) b.getValX(), (int) b.getValY(), this);
                                        AudioPlayer a = new AudioPlayer("Audios\\Explosion+11.wav");
                                        ThreadPool.init();
                                        ThreadPool.execute(a);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    pp.setRemove(true);
                                }
                                 if (p.getPrize().getType() != 1 && p.getPrize().getType() != 2){
                                     pp.getUser().getTank().setLife(pp.getUser().getTank().getLife() - b.getBulletPower());
                                     if (pp.getUser().getTank().getLife() <= 0) {
                                         try {
                                             BufferedImage image = ImageIO.read(
                                                     new File("PNG\\explosion3.png"));
                                             g2d.drawImage(image, (int) b.getValX(), (int) b.getValY(), this);
                                             AudioPlayer a = new AudioPlayer("Audios\\Explosion+11.wav");
                                             ThreadPool.init();
                                             ThreadPool.execute(a);
                                         } catch (IOException e) {
                                             e.printStackTrace();
                                         }
                                         pp.setRemove(true);
                                     }
                                     b.setRemove(true);
                                }
                            }catch (NullPointerException e) {
                                System.out.println(e);
                                pp.getUser().getTank().setLife(pp.getUser().getTank().getLife() - b.getBulletPower());
                                if (pp.getUser().getTank().getLife() <= 0) {
                                    BufferedImage image = null;
                                    try {
                                        image = ImageIO.read(
                                                new File("PNG\\explosion3.png"));
                                        AudioPlayer a = new AudioPlayer("Audios\\Explosion+11.wav");
                                        ThreadPool.init();
                                        ThreadPool.execute(a);
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                    g2d.drawImage(image, (int) b.getValX(), (int) b.getValY(), this);
                                    pp.setRemove(true);
                                }
                                b.setRemove(true);
                            }
                        }
                    }
                }
            }catch (ConcurrentModificationException e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Draw the map the game.
     * @param g2d the Graphic2D of this frame.
     */
    public void drawMap(Graphics2D g2d) {
        try {
            BufferedImage m1 = ImageIO.read(
                    new File("PNG\\tileSand1.png"));
            BufferedImage m2 = ImageIO.read(
                    new File("PNG\\treeBrown_large.png"));
            BufferedImage m5 = ImageIO.read(
                    new File("PNG\\tileGrass_roadCrossing.png"));
            BufferedImage m6 = ImageIO.read(
                    new File("PNG\\tileGrass_roadCrossingRound.png"));
            BufferedImage m7 = ImageIO.read(
                    new File("PNG\\tileGrass_roadEast.png"));
            BufferedImage m8 = ImageIO.read(
                    new File("PNG\\tileGrass_roadNorth.png"));
            BufferedImage m9 = ImageIO.read(
                    new File("PNG\\treeGreen_small.png"));
            BufferedImage m10 = ImageIO.read(
                    new File("PNG\\tileGrass_roadSplitN.png"));
            BufferedImage m11 = ImageIO.read(
                    new File("PNG\\tileGrass_roadSplitS.png"));
            BufferedImage m12 = ImageIO.read(
                    new File("PNG\\sandbagBeige_open.png"));
            BufferedImage m13 = ImageIO.read(
                    new File("PNG\\tileGrass1.png"));
            BufferedImage m14 = ImageIO.read(
                    new File("PNG\\sandbagBrown_open.png"));
            BufferedImage m15 = ImageIO.read(
                    new File("PNG\\treeBrown_twigs.png"));
            BufferedImage m16 = ImageIO.read(
                    new File("PNG\\treeGreen_twigs.png"));
            BufferedImage m17 = ImageIO.read(
                    new File("PNG\\sandbagBeige.png"));

            int width = m17.getWidth();
            int height = m17.getHeight();

            for (int i = 0 ; i < 32; i++) {
                g2d.drawImage(m17, 115+i*width, 99, this);
            }
            for (int i = 1; i < 22; i++) {
                g2d.drawImage(m17, 115, 100+i*height, this);
            }
            for (int i = 1; i < 22; i++) {
                g2d.drawImage(m17, 115+31*width, 100+i*height, this);
            }
            for (int i = 0 ; i < 32; i++) {
                g2d.drawImage(m17, 115+i*width, 100+22*height-13, this);
            }
            g2d.drawImage(m13, 115+width, 100+height, this);
            g2d.drawImage(m13, 115+width, 100+height+64, this);
            g2d.drawImage(m8, 115+width+64, 100+height, this);
            g2d.drawImage(m8, 115+width+64, 100+height+64, this);
            g2d.drawImage(m5, 115+width+64, 100+height+2*64, this);
            g2d.drawImage(m7, 115+width, 100+height+2*64, this);

            for (int i = 0; i < 13; i++) {
                g2d.drawImage(m13, 115+width+(i+2)*64, 100+height, this);
            }

            for (int i = 0; i < 13; i++) {
                g2d.drawImage(m13, 115+width+(i+2)*64, 100+height+64, this);
            }

            for (int i = 0; i < 13; i++) {
                g2d.drawImage(m13, 115+width+(i+2)*64, 100+height+128+64, this);
            }

            for (int i = 0; i < 13; i++) {
                g2d.drawImage(m13, 115+width+(i+2)*64, 100+height+4*64, this);
            }

            for (int i = 0; i < 13; i++) {
                g2d.drawImage(m13, 115+width+(i+2)*64, 100+height+6*64, this);
            }

            for (int i = 0; i < 13; i++) {
                g2d.drawImage(m7, 115+width+(i+2)*64, 100+height+5*64, this);
            }

            for (int i = 0; i < 4; i++) {
                g2d.drawImage(m8, 115+width+64, 100+height+(3+i)*64, this);
            }

            for (int i = 2; i < 15; i++) {
                g2d.drawImage(m7, 115+width+i*64, 100+height+2*64, this);
            }

            for (int i = 0; i < 4; i++) {
                g2d.drawImage(m13, 115+width, 100+height+(3+i)*64, this);
            }

            g2d.drawImage(m5, 115+width+64, 100+height+5*64, this);
            g2d.drawImage(m7, 115+width, 100+height+5*64, this);


            g2d.drawImage(m6, 115+width+5*64, 100+height+2*64, this);
            for(int i = 0; i < 2; i++) {
                g2d.drawImage(m8, 115+width+5*64, 100+height+i*64, this);
            }
            for(int i = 0; i < 2; i++) {
                g2d.drawImage(m8, 115+width+5*64, 100+height+(i+3)*64, this);
            }
            g2d.drawImage(m5, 115+width+5*64, 100+height+(2+3)*64, this);
            g2d.drawImage(m8, 115+width+5*64, 100+height+(3+3)*64, this);
            g2d.drawImage(m10, 115+width+9*64, 100+height+2*64, this);
            for(int i = 0; i < 2; i++) {
                g2d.drawImage(m8, 115+width+9*64, 100+height+i*64, this);
            }

            g2d.drawImage(m11, 115+width+13*64, 100+height+5*64, this);
            g2d.drawImage(m8, 115+width+13*64, 100+height+6*64, this);

            g2d.drawImage(m16, 115+width+12*64, 100+height+4*64, this);
            g2d.drawImage(m14, 115+width+8*64, 100+height+4*60, this);
            g2d.drawImage(m16, 30+width+4*64, 100+height+7*60, this);
            g2d.drawImage(m15, 115+width+4*64, 100+height+3*72, this);
            g2d.drawImage(m12, 115+width+2*64, 100+height+48, this);
            g2d.drawImage(m9, 115+width+13*64, 100+height+48, this);

            for (int i = 0; i < 7; i++) {
                char[] chars = maze[i].toCharArray();
                for (int j = 0; j < 15; j++) {
                    if (chars[j] == '1') {
                        g2d.drawImage(m1, 115+width+j*64, 100+height+i*64, this);
                    }
                    if (chars[j] == '2') {
                        g2d.drawImage(m1, 115+width+j*64, 100+height+i*64, this);
                        g2d.drawImage(m2, 115+width+j*64, 100+height+i*64, this);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Rotate the image by given degree.
     * @param img the image that rotated.
     * @param angle the degree of the rotate.
     * @return a new bufferedImage.
     */
    public BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {

        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((double) (newWidth - w) / 2, (double) (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, this);
        g2d.dispose();

        return rotated;
    }

    /**
     * Counts the lines of the string file.
     * @return the number of the lines.
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
     * Set the maze array with reading a string file.
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
     * Handle the ways of tank moving.
     * @param state the GameState of the GameFrame.
     * @throws IOException When the log file could not be created.
     */
    public void moving( GameState state) throws IOException {
        for (Player p : state.getPlayers()) {
            for (int i = 0; i < 7; i++) {
                char[] chars = maze[i].toCharArray();
                for (int j = 0; j < 15; j++) {
                    if (chars[j] == '1' || chars[j] == '2') {
                        if (p.getLocX() < 196+j*64 && p.getLocX() > 109+j*64) {
                            if (p.getLocY() < 175+i*64 && p.getLocY() > 79+i*64) {
                                double[] m = new double[4];
                                m[0] = p.getLocX() - 109 - j * 64;
                                m[1] = 196 + j * 64 - p.getLocX();
                                m[2] = p.getLocY() - 79 - i * 64;
                                m[3] = 175 + i * 64 - p.getLocY();
                                double min = findMin(m);
                                if (p.getLocX() - 109 - j * 64 == min) {
                                    p.setLocX(109+j*64);
                                }
                                if (196 + j * 64 - p.getLocX() == min) {
                                    p.setLocX(196+j*64);
                                }
                                if (p.getLocY() - 79 - i * 64 == min) {
                                    p.setLocY(79+i*64);
                                }
                                if (175 + i * 64 - p.getLocY() == min) {
                                    p.setLocY(175+i*64);
                                }
                            }
                        }
                    }
                }
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
     * Hitting the bullets on the walls has handled in this class.
     * @param g2d the Graphic2D of this frame.
     * @param state the GameState of the GameFrame.
     */
    public void hittingWall(Graphics2D g2d, GameState state) {
        for (Player p : state.getPlayers()) {
            for (Bullet b : p.getBulletCollection().getBullets()) {
                for (int i = 0; i < 7; i++) {
                    char[] chars = maze[i].toCharArray();
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int j = 0; j < 15; j++) {
                        if (chars[j] == '1') {
                            if (b.getValX() < 196+j*64 && b.getValX() > 109+j*64) {
                                if (b.getValY() < 175+i*64 && b.getValY() > 79+i*64) {
                                    try {
                                        if (p.getPrize().getType() != 2) {
                                            DestructiveWall d = findWall(j, i);
                                            d.setWallPower(d.getWallPower() - b.getBulletPower());
                                            b.setRemove(true);
                                            if (d.getWallPower() <= 0) {
                                                chars[j] = (char) 0;
                                                removeWall(d);
                                                try {
                                                    BufferedImage image = ImageIO.read(new File("PNG\\explosionSmoke5.png"));
                                                    g2d.drawImage(image, (int) b.getValX(), (int) b.getValY(), this);
                                                    AudioPlayer a = new AudioPlayer("Audios\\Explosion+1.wav");
                                                    ThreadPool.init();
                                                    ThreadPool.execute(a);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                        if (p.getPrize().getType() == 2) {
                                            chars[j] = (char) 0;
                                            DestructiveWall d = findWall(j, i);
                                            removeWall(d);
                                            try {
                                                BufferedImage image = ImageIO.read(new File("PNG\\explosionSmoke5.png"));
                                                g2d.drawImage(image, (int) b.getValX(), (int) b.getValY(), this);
                                                AudioPlayer a = new AudioPlayer("Audios\\Explosion+1.wav");
                                                ThreadPool.init();
                                                ThreadPool.execute(a);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }catch (NullPointerException w) {
                                        b.setRemove(true);
                                        DestructiveWall d = findWall(j, i);
                                        d.setWallPower(d.getWallPower() - b.getBulletPower());
                                        b.setRemove(true);
                                        if (d.getWallPower() <= 0) {
                                            chars[j] = (char) 0;
                                            removeWall(d);
                                            try {
                                                BufferedImage image = ImageIO.read(new File("PNG\\explosionSmoke5.png"));
                                                g2d.drawImage(image, (int) b.getValX(), (int) b.getValY(), this);
                                                AudioPlayer a = new AudioPlayer("Audios\\Explosion+1.wav");
                                                ThreadPool.init();
                                                ThreadPool.execute(a);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        stringBuilder.append(chars[j]);
                    }
                    maze[i] = stringBuilder.toString();
                }
            }
        }
    }

    /**
     * Save the prize by hitting the tank with it.
     * @param state the GameState of the GameFrame.
     */
    public void gettingPrize(GameState state) {
        for (Player p : state.getPlayers()) {
            for (Prize pp : prizes) {
                if (p.getLocY() - pp.getLocationY() < 30 && p.getLocY() - pp.getLocationY() > -30 &&
                p.getLocX() - pp.getLocationX() < 30 && p.getLocX() - pp.getLocationX() > -30 && !p.isHavePrize()) {
                    p.setHavePrize(true);
                    pp.setRemove(true);
                    p.setPrize(pp);
                }
            }
        }
        Iterator<Prize> it = prizes.iterator();
        while (it.hasNext()) {
            Prize p = it.next();
            if (p.isRemove() || p.getCounter() == 100) {
                it.remove();
            }
        }
    }

    /**
     * Set the walls with reading the maze array.
     */
    public void setWalls() {
        for (int i = 0; i < 7; i++) {
            char[] chars = maze[i].toCharArray();
            for (int j = 0; j < 15; j++) {
                if (chars[j] == '1') {
                    DestructiveWall d = new DestructiveWall(j, i);
                    walls.add(d);
                }
            }
        }
    }

    /**
     * Find a wall with given its location.
     * @param x the width of the wall.
     * @param y the height of the wall.
     * @return a DestructiveWall.
     */
    public DestructiveWall findWall(int x, int y) {
        for (DestructiveWall d : walls) {
            if (x == d.getLocX() && y == d.getLocY()) {
                return d;
            }
        }
        return null;
    }

    /**
     * Remove a wall from a array list.
     * @param destructiveWall the wall that removed.
     */
    public void removeWall(DestructiveWall destructiveWall) {
        walls.remove(destructiveWall);
    }
}

