package ru.derendiaev.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import ru.derendiaev.controller.FrogController;
import ru.derendiaev.controller.SnakeController;
import ru.derendiaev.model.Snake.Direction;
import ru.derendiaev.thread.FrogThread;
import ru.derendiaev.thread.SnakeThread;

/**
 * Created by DDerendiaev on 13-Jan-22.
 */
public class GameField extends JPanel implements PropertyChangeListener {

  private static final int CELL_SIZE = 16;
  private static final int SIZE = 20;

  private boolean inGame;
  private Image head;
  private Image body;
  private Image tail;
  private Image frog;

  SnakeController snakeController;
  List<FrogController> frogControllers;
  Thread snakeThread;
  Thread frogThread;

  /**
   * Game field constructor.
   */
  public GameField(SnakeController snakeController, List<FrogController> frogControllers) {
    this.snakeController = snakeController;
    this.frogControllers = frogControllers;
    loadImages();
    init();
    addKeyListener(new KeyListener());
  }

  @Override
  public void paintComponent(Graphics g) {
    if (!isFocusOwner()) {
      requestFocus();
    }

    inGame = snakeController.isLive();

    if (inGame) {
      paintGameField(g);
    } else {
      paintGameOver(g);
    }
  }

  /**
   * GameField initialization method.
   */
  public void init() {
    inGame = true;
    snakeController.addPropertyChangeListener(this);
    snakeController.initSnake();
    snakeThread = new Thread(new SnakeThread(snakeController));
    snakeThread.start();

    for (FrogController frogController : frogControllers) {
      frogController.addPropertyChangeListener(this);
      frogController.initFrog();
      frogThread = new Thread(new FrogThread(frogController));
      frogThread.start();
    }
  }

  public boolean isInGame() {
    return inGame;
  }

  private void loadImages() {
    ClassLoader classLoader = getClass().getClassLoader();

    ImageIcon headIcon = new ImageIcon(
        Objects.requireNonNull(classLoader.getResource("images/head.png")));
    head = headIcon.getImage();

    ImageIcon bodyIcon = new ImageIcon(
        Objects.requireNonNull(classLoader.getResource("images/body.png")));
    body = bodyIcon.getImage();

    ImageIcon tailIcon = new ImageIcon(
        Objects.requireNonNull(classLoader.getResource("images/tail.png")));
    tail = tailIcon.getImage();

    ImageIcon frogIcon = new ImageIcon(
        Objects.requireNonNull(classLoader.getResource("images/frog.png")));
    frog = frogIcon.getImage();
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    repaint();
  }

  private void paintSnake(Graphics g, int[] snakeX, int[] snakeY) {
    int snakeSize = snakeController.getSize();

    for (int i = 0; i < snakeSize; i++) {
      if (i == 0) {
        g.drawImage(head, snakeX[i], snakeY[i], this);
      } else if (i == (snakeSize - 1)) {
        g.drawImage(tail, snakeX[i], snakeY[i], this);
      } else {
        g.drawImage(body, snakeX[i], snakeY[i], this);
      }
    }
  }

  private void paintGameField(Graphics g) {
    super.paintComponent(g);
    int[] snakeX = snakeController.getX();
    int[] snakeY = snakeController.getY();

    for (FrogController frogController : frogControllers) {
      int frogX = frogController.getX();
      int frogY = frogController.getY();
      g.drawImage(frog, frogX, frogY, this);

      if (frogX == snakeX[0] && frogY == snakeY[0]) {
        frogController.respawnFrog();
        snakeController.grow();
      }
    }

    paintSnake(g, snakeX, snakeY);
  }

  private void paintGameOver(Graphics g) {
    frogControllers.forEach(FrogController::kill);
    String message = "Game Over";
    Font font = new Font("Monospaced", Font.BOLD, 20);
    g.setColor(Color.white);
    g.setFont(font);
    g.drawString(message, 110, CELL_SIZE * SIZE / 2);
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