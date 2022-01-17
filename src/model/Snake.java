package model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

/**
 * Created by DDerendiaev on 17-Jan-22.
 */
public class Snake {

  private static final int CELL_SIZE = 16;

  //Snake params.
  private final int[] snakeX;
  private final int[] snakeY;
  private boolean isLive = true;
  private int size;
  private Direction direction;
  private Image body;

  //Gamefield params.
  private final int fieldSizeX;
  private final int fieldSizeY;

  /**
   * Snake constructor.
   */
  public Snake(Direction direction, int size, int fieldSizeX, int fieldSizeY) {
    this.direction = direction;
    this.size = size;
    this.fieldSizeX = fieldSizeX;
    this.fieldSizeY = fieldSizeY;

    int fieldArea = fieldSizeX * fieldSizeY;
    snakeX = new int[fieldArea];
    snakeY = new int[fieldArea];

    init();
  }

  /**
   * Direction emun.
   */
  public enum Direction {
    RIGHT, DOWN, LEFT, UP
  }

  /**
   * Draw snake.
   */
  public void draw(Graphics g, ImageObserver observer) {
    for (int i = 0; i < size; i++) {
      g.drawImage(body, snakeX[i], snakeY[i], observer);
    }

  }

  /**
   * Move snake.
   */
  public void move() {
    for (int i = size; i > 0; i--) {
      snakeX[i] = snakeX[i - 1];
      snakeY[i] = snakeY[i - 1];
    }

    if (direction.equals(Direction.LEFT)) {
      snakeX[0] -= CELL_SIZE;
    } else if (direction.equals(Direction.RIGHT)) {
      snakeX[0] += CELL_SIZE;
    } else if (direction.equals(Direction.UP)) {
      snakeY[0] -= CELL_SIZE;
    } else if (direction.equals(Direction.DOWN)) {
      snakeY[0] += CELL_SIZE;
    }
  }

  /**
   * Grow snake when is eating.
   */
  public void growSnake() {
    size++;
  }

  /**
   * Check snake collisions.
   */
  public void checkCollisions() {
    for (int i = size; i > 0; i--) {
      if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
        isLive = false;
        break;
      }
    }

    if (snakeX[0] > (fieldSizeX * CELL_SIZE) - CELL_SIZE) {
      isLive = false;
    } else if (snakeX[0] < 0) {
      isLive = false;
    } else if (snakeY[0] > (fieldSizeY * CELL_SIZE) - CELL_SIZE) {
      isLive = false;
    } else if (snakeY[0] < 0) {
      isLive = false;
    }
  }

  public int getHeadX() {
    return snakeX[0];
  }

  public int getHeadY() {
    return snakeY[0];
  }

  public boolean isLive() {
    return isLive;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  private void init() {
    for (int i = 0; i < size; i++) {
      snakeX[i] = -i * CELL_SIZE;
      snakeY[i] = ((fieldSizeX / 2) - 1) * CELL_SIZE;
    }

    body = new ImageIcon("snake.png").getImage();
  }
}