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
import java.util.List;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import lombok.Getter;
import lombok.Setter;
import ru.derendiaev.Config;
import ru.derendiaev.model.Coords;

/**
 * Created by DDerendiaev on 13-Jan-22.
 */
public class GameField extends JPanel {

  private final PropertyChangeSupport observer = new PropertyChangeSupport(this);
  private final int cellSize = Config.CELL_SIZE;

  private Image head;
  private Image body;
  private Image tail;
  private Image frog;

  @Getter
  @Setter
  private int points;

  @Getter
  private boolean inGame;


  /**
   * Game field constructor.
   */
  public GameField(PropertyChangeListener listener) {
    observer.addPropertyChangeListener(listener);

    int sizeX = Config.getFieldSizeX() * cellSize;
    int sizeY = Config.getFieldSizeY() * cellSize;

    Dimension gameFieldDimension = new Dimension(sizeX, sizeY);
    setPreferredSize(gameFieldDimension);
    addMouseListener(new MouseListener());
    loadImages();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (!inGame) {
      paintTitle(g);
    }
  }

  public void addListener(PropertyChangeListener listener) {
    observer.addPropertyChangeListener(listener);
  }

  /**
   * Start game.
   */
  public void startGame() {
    points = 0;
    inGame = true;
    repaint();
    observer.firePropertyChange("startGame", false, true);
  }

  /**
   * Stop game.
   */
  public void stopGame() {
    inGame = false;
    observer.firePropertyChange("stopGame", true, false);
    repaint();
  }

  /**
   * Frog repaint.
   */
  public void repaintFrog(Coords past, Coords present) {
    Graphics g = this.getGraphics();
    clearPoints(g);

    int coordX;
    int coordY;

    if (past != null) {
      coordX = past.getCoordX() * cellSize;
      coordY = past.getCoordY() * cellSize;
      g.setColor(Color.BLACK);
      g.fillRect(coordX, coordY, cellSize, cellSize);
    }

    coordX = present.getCoordX() * cellSize;
    coordY = present.getCoordY() * cellSize;
    g.drawImage(frog, coordX, coordY, this);
    paintPoints(g);
  }

  /**
   * Snake repaint.
   */
  public void repaintSnake(Coords past, List<Coords> present) {
    Graphics g = this.getGraphics();
    clearPoints(g);

    if (past != null) {
      int coordX = past.getCoordX() * cellSize;
      int coordY = past.getCoordY() * cellSize;
      g.setColor(Color.BLACK);
      g.fillRect(coordX, coordY, cellSize, cellSize);
    }

    paintSnake(present, g);
    paintPoints(g);
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

  /**
   * Game over paint.
   */
  public void paintGameOver() {
    Graphics g = this.getGraphics();
    String message = "Game Over";
    Font font = new Font("Monospaced", Font.BOLD, 20);
    g.setColor(Color.white);
    g.setFont(font);
    g.drawString(message,
        (cellSize * Config.getFieldSizeX() / 2) - 55,
        cellSize * Config.getFieldSizeY() / 2);
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

  private void paintSnake(List<Coords> coords, Graphics g) {
    int cellX;
    int cellY;

    for (int i = 0; i < coords.size(); i++) {
      cellX = coords.get(i).getCoordX() * cellSize;
      cellY = coords.get(i).getCoordY() * cellSize;

      if (i == 0) {
        g.drawImage(tail, cellX, cellY, this);
      } else if (i == coords.size() - 1) {
        g.drawImage(head, cellX, cellY, this);
      } else {
        g.drawImage(body, cellX, cellY, this);
      }
    }
  }

  private void paintTitle(Graphics g) {
    String message = "SNAKE";
    Font font = new Font("Monospaced", Font.BOLD, 40);
    g.setColor(Color.white);
    g.setFont(font);
    g.drawString(message,
        (cellSize * Config.getFieldSizeX() / 2) - 65,
        cellSize * Config.getFieldSizeY() / 2);

    message = "the Game";
    font = new Font("Monospaced", Font.BOLD, 15);
    g.setColor(Color.white);
    g.setFont(font);
    g.drawString(message,
        cellSize * Config.getFieldSizeX() / 2,
        (cellSize * Config.getFieldSizeY() / 2) + 20);
  }

  private void paintPoints(Graphics g) {
    String message = "POINTS " + points;
    Font font = new Font("Monospaced", Font.BOLD, cellSize);
    g.setColor(Color.white);
    g.setFont(font);
    g.drawString(message, (cellSize * (Config.getFieldSizeX() - 7)),
        cellSize * 2);
  }

  private void clearPoints(Graphics g) {
    g.setColor(Color.BLACK);
    g.fillRect(cellSize * (Config.getFieldSizeX() - 3), cellSize,
        cellSize * 3, cellSize);
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