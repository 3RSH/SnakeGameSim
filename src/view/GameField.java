package view;

import controller.SnakeController;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import model.Snake.Direction;

/**
 * Created by DDerendiaev on 13-Jan-22.
 */
public class GameField extends JPanel {

  private static final int CELL_SIZE = 16;
  private static final int SIZE = 20;

  private Image cell;
  private Image frog;
  private int frogX;
  private int frogY;

  SnakeController snakeController;

  /**
   * Game field constructor.
   */
  public GameField() {
    loadImages();
    init();
    addKeyListener(new KeyListener());
  }

  @Override
  protected void paintComponent(Graphics g) {
    if (!isFocusOwner()) {
      requestFocus();
    }

    if (snakeController.snakeIsLive()) {
      super.paintComponent(g);
      g.drawImage(frog, frogX, frogY, this);

      int[] snakeX = snakeController.getSnakeX();
      int[] snakeY = snakeController.getSnakeY();

      for (int i = 0; i < snakeController.getSnakeSize(); i++) {
        g.drawImage(cell, snakeX[i], snakeY[i], this);
      }

      if (frogX == snakeX[0] && frogY == snakeY[0]) {
        snakeController.growSnake();
        createFrog();
      }
    } else {
      String message = "Game Over";
      Font font = new Font("Monospaced", Font.BOLD, 20);
      g.setColor(Color.white);
      g.setFont(font);
      g.drawString(message, 110, CELL_SIZE * SIZE / 2);
    }
  }

  void init() {
    createFrog();
    snakeController = new SnakeController(this);
    snakeController.initThread();
  }

  private void createFrog() {
    frogX = (new Random().nextInt(20) * CELL_SIZE);
    frogY = (new Random().nextInt(20) * CELL_SIZE);
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

      if (key == KeyEvent.VK_RIGHT
          && !snakeController.getSnakeDirection().equals(Direction.LEFT)) {
        snakeController.changeDirection(Direction.RIGHT);
      }
      if (key == KeyEvent.VK_LEFT
          && !snakeController.getSnakeDirection().equals(Direction.RIGHT)) {
        snakeController.changeDirection(Direction.LEFT);
      }
      if (key == KeyEvent.VK_UP
          && !snakeController.getSnakeDirection().equals(Direction.DOWN)) {
        snakeController.changeDirection(Direction.UP);
      }
      if (key == KeyEvent.VK_DOWN
          && !snakeController.getSnakeDirection().equals(Direction.UP)) {
        snakeController.changeDirection(Direction.DOWN);
      }
    }
  }
}