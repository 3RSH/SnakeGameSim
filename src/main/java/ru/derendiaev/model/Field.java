package ru.derendiaev.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by DDerendiaev on 01-Feb-22.
 */
public class Field {

  @Getter
  private final List<Cell> cells = new ArrayList<>();

  private Snake snake;

  private List<Frog> frogs;

  @Getter
  @Setter
  private boolean inGame = true;

  public Field(int fieldSizeX, int fieldSizeY) {
    for (int i = 0; i < fieldSizeX; i++) {
      for (int j = 0; j < fieldSizeY; j++) {
        cells.add(new Cell(i, j));
      }
    }
  }

  public void init(Snake snake, List<Frog> frogs) {
    this.snake = snake;
    this.frogs = frogs;
  }

  public void respawnFrog(Cell cell) {
    for (Frog frog : frogs) {
      if (frog.getCell().equals(cell)) {
        snake.grow();
        frog.respawn(cells);
        break;
      }
    }
  }
}
