package model;

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

  //GameField params.
  private final int fieldCellsX;
  private final int fieldCellsY;

  /**
   * Snake constructor.
   */
  public Snake(Direction direction, int size, int fieldCellsX, int fieldCellsY) {
    this.direction = direction;
    this.size = size;
    this.fieldCellsX = fieldCellsX;
    this.fieldCellsY = fieldCellsY;

    int fieldCellsArea = fieldCellsX * fieldCellsY;
    snakeX = new int[fieldCellsArea];
    snakeY = new int[fieldCellsArea];

    init();
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

    if (snakeX[0] > (fieldCellsX * CELL_SIZE) - CELL_SIZE) {
      isLive = false;
    } else if (snakeX[0] < 0) {
      isLive = false;
    } else if (snakeY[0] > (fieldCellsY * CELL_SIZE) - CELL_SIZE) {
      isLive = false;
    } else if (snakeY[0] < 0) {
      isLive = false;
    }
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

  public int getSize() {
    return size;
  }

  public int[] getSnakeX() {
    return snakeX;
  }

  public int[] getSnakeY() {
    return snakeY;
  }

  private void init() {
    for (int i = 0; i < size; i++) {
      snakeX[i] = 32 - i * CELL_SIZE;
      snakeY[i] = 0;
    }
  }

  /**
   * Direction emun.
   */
  public enum Direction {
    RIGHT, DOWN, LEFT, UP
  }
}