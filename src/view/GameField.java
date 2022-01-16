package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Created by DDerendiaev on 13-Jan-22.
 */
public class GameField extends JPanel implements ActionListener {
  private static final int SIZE = 20;
  private static final int CELL_SIZE = 16;
  private static final int ALL_CELLS = 400;

  private Image cell;
  private Image frog;
  private int frogX;
  private int frogY;
  private int[] snakeX = new int[ALL_CELLS];
  private int[] snakeY = new int[ALL_CELLS];
  private int snakeCellCount;
  private Timer timer;
  private boolean right = true;
  private boolean left;
  private boolean up;
  private boolean down;
  private boolean inGame;

  /**
   * Game field constructor.
   */
  public GameField() {
    timer = new Timer(250, this);
    loadImages();
    init();
    addKeyListener(new KeyListener());
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (inGame) {
      if (!isFocusOwner()) {
        requestFocus();
      }

      checkCollisions();
      checkFrog();
      move();
    } else {
      timer.stop();
    }

    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(frog, frogX, frogY, this);

    for (int i = 0; i < snakeCellCount; i++) {
      g.drawImage(cell, snakeX[i], snakeY[i], this);
    }

    if (!inGame) {
      String message = "Game Over";
      Font font = new Font("Monospaced", Font.BOLD, 20);
      g.setColor(Color.white);
      g.setFont(font);
      g.drawString(message, 110, CELL_SIZE * SIZE / 2);
    }
  }

  void init() {
    if (!inGame) {
      right = true;
      left = false;
      up = false;
      down = false;

      snakeCellCount = 3;

      for (int i = 0; i < snakeCellCount; i++) {
        snakeX[i] = -i * CELL_SIZE;
        snakeY[i] = ((SIZE / 2) - 1) * CELL_SIZE;
      }

      timer.start();
      createFrog();
      inGame = true;
    }
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

  private void move() {
    for (int i = snakeCellCount; i > 0; i--) {
      snakeX[i] = snakeX[i - 1];
      snakeY[i] = snakeY[i - 1];
    }

    if (left) {
      snakeX[0] -= CELL_SIZE;
    }
    if (right) {
      snakeX[0] += CELL_SIZE;
    }
    if (up) {
      snakeY[0] -= CELL_SIZE;
    }
    if (down) {
      snakeY[0] += CELL_SIZE;
    }
  }

  private void checkFrog() {
    if (snakeX[0] == frogX && snakeY[0] == frogY) {
      snakeCellCount++;
      createFrog();
    }
  }

  private void checkCollisions() {
    for (int i = snakeCellCount; i > 0; i--) {
      if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
        inGame = false;
        break;
      }
    }

    if (snakeX[0] > (SIZE * CELL_SIZE) - CELL_SIZE) {
      inGame = false;
    }
    if (snakeX[0] < 0) {
      inGame = false;
    }
    if (snakeY[0] > (SIZE * CELL_SIZE) - CELL_SIZE) {
      inGame = false;
    }
    if (snakeY[0] < 0) {
      inGame = false;
    }
  }


  private class KeyListener extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
      super.keyPressed(e);
      int key = e.getKeyCode();

      if (key == KeyEvent.VK_RIGHT && !left) {
        right = true;
        up = false;
        down = false;
      }
      if (key == KeyEvent.VK_LEFT && !right) {
        left = true;
        up = false;
        down = false;
      }
      if (key == KeyEvent.VK_UP && !down) {
        up = true;
        right = false;
        left = false;
      }
      if (key == KeyEvent.VK_DOWN && !up) {
        down = true;
        right = false;
        left = false;
      }
    }
  }
}