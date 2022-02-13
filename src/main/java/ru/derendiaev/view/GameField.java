package ru.derendiaev.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import lombok.Getter;
import lombok.Setter;
import ru.derendiaev.Config;
import ru.derendiaev.controller.FrogController;
import ru.derendiaev.controller.SnakeController;
import ru.derendiaev.model.CellType;
import ru.derendiaev.model.Field;
import ru.derendiaev.model.object.Cell;
import ru.derendiaev.model.object.Direction;
import ru.derendiaev.model.object.MovableObject;
import ru.derendiaev.model.thread.FrogThread;
import ru.derendiaev.model.thread.SnakeThread;

/**
 * Created by DDerendiaev on 13-Jan-22.
 */
public class GameField extends JPanel {

  @Getter
  private final PropertyChangeSupport observer = new PropertyChangeSupport(this);
  private final Random random = new Random();

  private Image head;
  private Image body;
  private Image tail;
  private Image frog;
  private Field field;

  @Getter
  @Setter
  private int frogCount;

  @Getter
  @Setter
  private int snakeSize;

  @Getter
  @Setter
  private int points;

  @Getter
  private boolean inGame;

  @Setter
  private List<Cell> snakeCoords;

  @Getter
  @Setter
  private List<Cell> frogsCoords;

  private List<Thread> gameThreads;

  /**
   * Game field constructor.
   */
  public GameField(PropertyChangeListener listener) {
    observer.addPropertyChangeListener(listener);

    int sizeX = Config.getFieldSizeX() * Config.CELL_SIZE;
    int sizeY = Config.getFieldSizeY() * Config.CELL_SIZE;

    Dimension gameFieldDimension = new Dimension(sizeX, sizeY);
    setPreferredSize(gameFieldDimension);
    addMouseListener(new MouseListener());
    loadImages();
    init();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (inGame) {
      paintGameField(g);

      if (!gameThreads.get(0).isAlive()) {
        paintTitle(g);
      }
    } else {
      paintGameOver(g);
    }
  }

  /**
   * GameField initialization method.
   */
  public void init() {
    points = 0;
    inGame = true;
    frogsCoords = new ArrayList<>();
    gameThreads = new ArrayList<>();
    field = new Field(Config.getFieldSizeX(), Config.getFieldSizeY());
    snakeInit();
    frogsInit();
    repaint();
  }

  /**
   * Start game's threads.
   */
  public void startThreads() {
    for (Thread thread : gameThreads) {
      thread.start();
    }
  }

  /**
   * Stop game.
   */
  public void stopGame() {
    observer.firePropertyChange("stopGame", inGame, false);
    inGame = false;
    this.repaint();

  }

  /**
   * Respawn new frog.
   */
  public void respawnFrog(int frogIndex) {
    int fieldArea = field.getCoords().length * field.getCoords()[0].length;

    if (frogCount + snakeSize < fieldArea) {
      List<Cell> frogCells = getNewFrogCells();
      frogsCoords.set(frogIndex, frogCells.get(0));
      frogCount++;

      getNewFrogThread(frogCells, frogIndex).start();
    } else {
      frogsCoords.set(frogIndex, null);
    }
  }

  /**
   * Increment points.
   */
  public void incrementPoints() {
    points++;

    if (points % 10 == 0) {
      observer.firePropertyChange("nextTenPoints", 0, 1);
    }
  }

  private void snakeInit() {
    snakeCoords = new ArrayList<>();

    //Set snake coordinates.
    for (int i = 0; i < Config.getSnakeStartSize(); i++) {
      int cellX = (Config.getSnakeStartSize() - 1) - i;

      snakeCoords.add(new Cell(cellX, 0));
    }

    //Indicate snake on the model.Field.
    for (int i = 0; i < snakeCoords.size(); i++) {
      int cellX = snakeCoords.get(i).getCellX();
      int cellY = snakeCoords.get(i).getCellY();

      if (i == 0) {
        field.getCoords()[cellX][cellY] = CellType.HEAD;
      } else if (i == snakeCoords.size() - 1) {
        field.getCoords()[cellX][cellY] = CellType.TAIL;
      } else {
        field.getCoords()[cellX][cellY] = CellType.BODY;
      }
    }

    //Create snake model and snake controller.
    MovableObject snake =
        new MovableObject(snakeCoords, Direction.RIGHT, Config.getSnakeStartSpeed());
    SnakeController snakeController = new SnakeController();
    SnakeThread snakeThread = new SnakeThread(snake, field, snakeController);

    //Snake controller initialization.
    snakeController.setSnakeThread(snakeThread);
    snakeController.setGameField(this);
    observer.addPropertyChangeListener(snakeController);

    //Add the snake model in to the threads' collection.
    gameThreads.add(new Thread(snakeThread));
  }

  private void frogsInit() {
    frogCount = (Config.getFieldSizeX() + Config.getFieldSizeY()) / 10;

    for (int i = 0; i < frogCount; i++) {
      List<Cell> frogCells = getNewFrogCells();
      frogsCoords.add(frogCells.get(0));

      int frogIndex = frogsCoords.size() - 1;
      gameThreads.add(getNewFrogThread(frogCells, frogIndex));
    }
  }

  private List<Cell> getNewFrogCells() {
    int frogX;
    int frogY;

    do {
      frogX = random.nextInt(field.getCoords().length);
      frogY = random.nextInt(field.getCoords()[0].length);
    } while (field.getCoords()[frogX][frogY] != CellType.FREE);

    field.getCoords()[frogX][frogY] = CellType.FROG;

    List<Cell> frogCells = new ArrayList<>(1);
    frogCells.add(new Cell(frogX, frogY));

    return frogCells;
  }

  private Thread getNewFrogThread(List<Cell> frogCells, int frogIndex) {
    MovableObject frog = new MovableObject(frogCells, Direction.RIGHT, 1);
    FrogController frogController = new FrogController();
    frogController.setFrogIndex(frogIndex);
    FrogThread frogThread = new FrogThread(frog, field, frogController);

    frogController.frogThread = frogThread;
    frogController.gameField = this;
    observer.addPropertyChangeListener(frogController);

    return new Thread(frogThread);
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

  private void paintGameField(Graphics g) {
    paintFrogs(g);
    paintSnake(g);
    paintPoints(g);
  }

  private void paintSnake(Graphics g) {
    int cellX;
    int cellY;

    for (int i = 0; i < snakeCoords.size(); i++) {
      cellX = snakeCoords.get(i).getCellX() * Config.CELL_SIZE;
      cellY = snakeCoords.get(i).getCellY() * Config.CELL_SIZE;

      if (i == 0) {
        g.drawImage(head, cellX, cellY, this);
      } else if (i == snakeCoords.size() - 1) {
        g.drawImage(tail, cellX, cellY, this);
      } else {
        g.drawImage(body, cellX, cellY, this);
      }
    }
  }

  private void paintFrogs(Graphics g) {
    int cellX;
    int cellY;

    for (Cell frogsCoord : frogsCoords) {
      if (frogsCoord != null) {
        cellX = frogsCoord.getCellX() * Config.CELL_SIZE;
        cellY = frogsCoord.getCellY() * Config.CELL_SIZE;
        g.drawImage(frog, cellX, cellY, this);
      }
    }
  }

  private void paintGameOver(Graphics g) {
    paintGameField(g);
    String message = "Game Over";
    Font font = new Font("Monospaced", Font.BOLD, 20);
    g.setColor(Color.white);
    g.setFont(font);
    g.drawString(message,
        (Config.CELL_SIZE * Config.getFieldSizeX() / 2) - 55,
        Config.CELL_SIZE * Config.getFieldSizeY() / 2);
  }

  private void paintTitle(Graphics g) {
    String message = "SNAKE";
    Font font = new Font("Monospaced", Font.BOLD, 40);
    g.setColor(Color.white);
    g.setFont(font);
    g.drawString(message,
        (Config.CELL_SIZE * Config.getFieldSizeX() / 2) - 65,
        Config.CELL_SIZE * Config.getFieldSizeY() / 2);

    message = "the Game";
    font = new Font("Monospaced", Font.BOLD, 15);
    g.setColor(Color.white);
    g.setFont(font);
    g.drawString(message,
        Config.CELL_SIZE * Config.getFieldSizeX() / 2,
        (Config.CELL_SIZE * Config.getFieldSizeY() / 2) + 20);
  }

  private void paintPoints(Graphics g) {
    String message = "POINTS " + points;
    Font font = new Font("Monospaced", Font.BOLD, Config.CELL_SIZE);
    g.setColor(Color.white);
    g.setFont(font);
    g.drawString(message, (Config.CELL_SIZE * (field.getCoords().length - 7)),
        Config.CELL_SIZE * 2);
  }

  private class MouseListener extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
      super.mouseClicked(e);
      int clickedButton = e.getButton();

      if (clickedButton == MouseEvent.BUTTON1) {
        observer.firePropertyChange("changeDirection", true, false);
      } else if (clickedButton == MouseEvent.BUTTON3) {
        observer.firePropertyChange("changeDirection", false, true);
      }
    }
  }
}