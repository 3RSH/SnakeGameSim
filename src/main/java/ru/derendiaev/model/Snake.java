package ru.derendiaev.model;

/**
 * Created by DDerendiaev on 17-Jan-22.
 */
public class Snake {

  //Snake params.
  private final int[] snakeX;
  private final int[] snakeY;
  private final int startSize;
  private final int startSpeed;
  private int currentSize;
  private int currentSpeed;
  private boolean isLive = true;
  private int points;
  private Direction direction;
  private int stepCounter;

  //GameField params.
  private final int cellSize;
  private final int fieldCellsX;
  private final int fieldCellsY;

  /**
   * Snake constructor.
   */
  public Snake(int cellSize, int fieldCellsX, int fieldCellsY, int startSize, int startSpeed) {
    this.cellSize = cellSize;
    this.fieldCellsX = fieldCellsX;
    this.fieldCellsY = fieldCellsY;
    this.startSize = startSize;
    this.startSpeed = startSpeed;

    int fieldCellsArea = fieldCellsX * fieldCellsY;
    snakeX = new int[fieldCellsArea];
    snakeY = new int[fieldCellsArea];
  }

  /**
   * Move snake.
   */
  public void move() {
    for (int i = currentSize; i > 0; i--) {
      snakeX[i] = snakeX[i - 1];
      snakeY[i] = snakeY[i - 1];
    }

    if (direction == Direction.LEFT) {
      snakeX[0] -= cellSize;
    } else if (direction == Direction.RIGHT) {
      snakeX[0] += cellSize;
    } else if (direction == Direction.UP) {
      snakeY[0] -= cellSize;
    } else if (direction == Direction.DOWN) {
      snakeY[0] += cellSize;
    }

    stepCounter++;
    checkCollisions();
  }

  /**
   * Grow snake when is eating.
   */
  public void growSnake() {
    currentSize++;
    points++;

    if (points % 10 == 0) {
      currentSpeed++;
    }
  }

  /**
   * Snake turn right.
   */
  public void turnRight() {
    if (direction == Direction.RIGHT) {
      setDirection(Direction.DOWN);
    } else if (direction == Direction.DOWN) {
      setDirection(Direction.LEFT);
    } else if (direction == Direction.LEFT) {
      setDirection(Direction.UP);
    } else if (direction == Direction.UP) {
      setDirection(Direction.RIGHT);
    }
  }

  /**
   * Snake turn right.
   */
  public void turnLeft() {
    if (direction == Direction.RIGHT) {
      setDirection(Direction.UP);
    } else if (direction == Direction.UP) {
      setDirection(Direction.LEFT);
    } else if (direction == Direction.LEFT) {
      setDirection(Direction.DOWN);
    } else if (direction == Direction.DOWN) {
      setDirection(Direction.RIGHT);
    }
  }

  private void setDirection(Direction direction) {
    if (canChangeDirection(direction)) {
      this.direction = direction;
      stepCounter = 0;
    }
  }

  public boolean isLive() {
    return isLive;
  }

  public int getCurrentSize() {
    return currentSize;
  }

  public int[] getSnakeX() {
    return snakeX;
  }

  public int[] getSnakeY() {
    return snakeY;
  }

  public int getHeadX() {
    return snakeX[0];
  }

  public int getHeadY() {
    return snakeY[0];
  }

  public int getFieldCellsX() {
    return fieldCellsX;
  }

  public int getFieldCellsY() {
    return fieldCellsY;
  }

  public int getCellSize() {
    return cellSize;
  }

  public void setLive(boolean live) {
    isLive = live;
  }

  public int getPoints() {
    return points;
  }

  public int getSpeed() {
    return currentSpeed;
  }

  /**
   * Snake initialization method.
   */
  public void init() {
    currentSize = startSize;
    currentSpeed = startSpeed;
    points = 0;
    isLive = true;
    direction = Direction.RIGHT;

    for (int i = 0; i < currentSize; i++) {
      snakeX[i] = currentSize * cellSize - (i + 1) * cellSize;
      snakeY[i] = 0;
    }
  }

  private void checkCollisions() {
    for (int i = currentSize; i > 0; i--) {
      if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
        isLive = false;
        break;
      }
    }

    if (snakeX[0] > (fieldCellsX * cellSize) - cellSize) {
      isLive = false;
    } else if (snakeX[0] < 0) {
      isLive = false;
    } else if (snakeY[0] > (fieldCellsY * cellSize) - cellSize) {
      isLive = false;
    } else if (snakeY[0] < 0) {
      isLive = false;
    }
  }

  private boolean canChangeDirection(Direction direction) {
    boolean upCondition =
        direction == Direction.UP
            && this.direction != Direction.DOWN
            && stepCounter > 0;
    boolean downCondition =
        direction == Direction.DOWN
            && this.direction != Direction.UP
            && stepCounter > 0;
    boolean rightCondition =
        direction == Direction.RIGHT
            && this.direction != Direction.LEFT
            && stepCounter > 0;
    boolean leftCondition =
        direction == Direction.LEFT
            && this.direction != Direction.RIGHT
            && stepCounter > 0;

    return upCondition || downCondition || rightCondition || leftCondition;
  }

  /**
   * Direction emun.
   */
  public enum Direction {
    RIGHT, DOWN, LEFT, UP
  }
}