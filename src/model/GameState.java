package model;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author MohammadaliKeshtparvar
 * @version 0.0
 */
public class GameState {

   private ArrayList<Player> players;
   private boolean gameOver;
   private KeyEscape keyEscape;


    public GameState() {
        players = new ArrayList<>();
        gameOver = false;
        keyEscape = new KeyEscape();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    class KeyEscape extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                gameOver = true;
            }
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public KeyEscape getKeyEscape() {
        return keyEscape;
    }

    public void removePlayer() {
        Iterator<Player> it = players.iterator();
        while (it.hasNext()) {
            Player p = it.next();
            if (p.isRemove())
                it.remove();
        }
    }
}
