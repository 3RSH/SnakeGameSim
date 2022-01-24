package view;

import controller.FrogController;
import controller.SnakeController;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import model.Snake.Direction;

/**
 * Created by DDerendiaev on 13-Jan-22.
 */
public class GameField extends JPanel {

  private static final int CELL_SIZE = 16;
  private static final int SIZE = 20;

  private boolean inGame;
  private Image cell;
  private Image frog;

  SnakeController snakeController;
  FrogController frogController;

  /**
   * Game field constructor.
   */
  public GameField() {
    snakeController = new SnakeController(this);
    frogController = new FrogController(this);
    loadImages();
    init();
    addKeyListener(new KeyListener());
  }

  @Override
  protected void paintComponent(Graphics g) {
    if (!isFocusOwner()) {
      requestFocus();
    }

    inGame = snakeController.isLive();

    if (inGame) {
      super.paintComponent(g);

      int frogX = frogController.getX();
      int frogY = frogController.getY();

      g.drawImage(frog, frogX, frogY, this);

      int[] snakeX = snakeController.getX();
      int[] snakeY = snakeController.getY();

      for (int i = 0; i < snakeController.getSize(); i++) {
        g.drawImage(cell, snakeX[i], snakeY[i], this);
      }

      if (frogX == snakeX[0] && frogY == snakeY[0]) {
        frogController.kill();
        frogController.init();
        snakeController.grow();
      }


    } else {
      frogController.kill();
      String message = "Game Over";
      Font font = new Font("Monospaced", Font.BOLD, 20);
      g.setColor(Color.white);
      g.setFont(font);
      g.drawString(message, 110, CELL_SIZE * SIZE / 2);
    }
  }

  public void init() {
    inGame = true;
    frogController.init();
    snakeController.init();
  }

  public boolean isInGame() {
    return inGame;
  }

  private void loadImages() {
    ImageIcon cellIcon = new ImageIcon("snake.png");
    cell = cellIcon.getImage();
    frog = cellIcon.getImage();
  }


  private class KeyListener extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
      super.keyPressed(e);
      int key = e.getKeyCode();

      if (key == KeyEvent.VK_RIGHT) {
        snakeController.changeDirection(Direction.RIGHT);
      }
      if (key == KeyEvent.VK_LEFT) {
        snakeController.changeDirection(Direction.LEFT);
      }
      if (key == KeyEvent.VK_UP) {
        snakeController.changeDirection(Direction.UP);
      }
      if (key == KeyEvent.VK_DOWN) {
        snakeController.changeDirection(Direction.DOWN);
      }
    }
  }
}