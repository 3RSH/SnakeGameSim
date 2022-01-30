package ru.derendiaev.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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

  private final int cellSize;
  private final int fieldCellsX;
  private final int fieldCellsY;

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

    cellSize = snakeController.getFieldParams()[0];
    fieldCellsX = snakeController.getFieldParams()[1];
    fieldCellsY = snakeController.getFieldParams()[2];
    int sizeX = fieldCellsX * cellSize;
    int sizeY = fieldCellsY * cellSize;

    Dimension gameFieldDimension = new Dimension(sizeX, sizeY);
    this.setPreferredSize(gameFieldDimension);
    this.setMinimumSize(gameFieldDimension);
    this.setMaximumSize(gameFieldDimension);
    loadImages();
    addMouseListener(new MouseListener());

    snakeController.initSnake();
  }

  @Override
  public void paintComponent(Graphics g) {
    if (!isFocusOwner()) {
      requestFocus();
    }

    if (snakeController.isLive()) {
      paintGameField(g);
    } else {
      frogControllers.forEach(FrogController::kill);
      inGame = false;
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

    ScheduledThreadPoolExecutor frogExecutor =
        new ScheduledThreadPoolExecutor(frogControllers.size());

    for (int i = 0; i < frogControllers.size(); i++) {
      frogControllers.get(i).addPropertyChangeListener(this);
      frogControllers.get(i).initFrog();
      frogThread = new Thread(new FrogThread(frogControllers.get(i)));
      frogExecutor.schedule(
          frogThread,
          (i + 1) * 1000L / frogControllers.size(),
          TimeUnit.MILLISECONDS);
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

    for (int i = 1; i < snakeSize; i++) {
      if (i == (snakeSize - 1)) {
        g.drawImage(tail, snakeX[i], snakeY[i], this);
      } else {
        g.drawImage(body, snakeX[i], snakeY[i], this);
      }
    }

    g.drawImage(head, snakeX[0], snakeY[0], this);
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
    paintGameField(g);
    String message = "Game Over";
    Font font = new Font("Monospaced", Font.BOLD, 20);
    g.setColor(Color.white);
    g.setFont(font);
    g.drawString(message, (cellSize * fieldCellsX / 2) - 55, cellSize * fieldCellsY / 2);
  }

  private class MouseListener extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
      super.mouseClicked(e);
      int clickedButton = e.getButton();

      Direction direction = snakeController.getDirection();

      if (clickedButton == MouseEvent.BUTTON1) {
        snakeController.turnLeft();
      } else if (clickedButton == MouseEvent.BUTTON3) {
        snakeController.turnRight();
      }
    }
  }
}