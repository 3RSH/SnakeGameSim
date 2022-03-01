package ru.derendiaev.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.Getter;
import lombok.Setter;
import ru.derendiaev.Config;
import ru.derendiaev.model.object.Cell;
import ru.derendiaev.model.object.Type;
import ru.derendiaev.model.object.Direction;
import ru.derendiaev.model.object.FrogObject;
import ru.derendiaev.model.object.MovableObject;
import ru.derendiaev.model.object.SnakeObject;
import ru.derendiaev.model.thread.FrogThread;
import ru.derendiaev.model.thread.MovableThread;
import ru.derendiaev.model.thread.SnakeThread;

/**
 * Created by DDerendiaev on 16-Feb-22.
 */
public class ModelManager {

  private Field field;
  private Map<MovableObject, MovableThread<?>> threadByObject;
  private Map<Cell, MovableObject> objectByCellObject;

  @Getter
  @Setter
  private boolean modelIsRunning;

  /**
   * Model initialization.
   */
  public void initModel() {
    threadByObject = new HashMap<>();
    objectByCellObject = new HashMap<>();
    initField();
    initSnake();
    initFrogs();
  }

  /**
   * Run model's threads.
   */
  public void startModel() {
    modelIsRunning = true;
    ExecutorService service = Executors.newFixedThreadPool(threadByObject.size());
    threadByObject.forEach((key, value) -> service.execute(value));
    service.shutdown();
  }

  /**
   * Stop model's threads.
   */
  public void stopModel() {
    modelIsRunning = false;
    threadByObject.entrySet().stream()
        .filter(entry -> entry.getValue().isLive())
        .forEach(entry -> entry.getValue().setLive(false));
  }

  public void killFrog(Cell frogCell, MovableObject snake) {
    MovableObject frog = objectByCellObject.get(frogCell);
    threadByObject.get(frog).setLive(false);
    threadByObject.remove(frog);
    objectByCellObject.put(frogCell, snake);
  }

  public void respawnFrog() {
    FrogObject frogObject = createFrog();

    if (frogObject != null) {
      FrogThread frogThread = new FrogThread(frogObject, field, this);
      threadByObject.put(frogObject, frogThread);
      Thread thread = new Thread(frogThread);
      thread.start();
    }
  }

  private void initField() {
    field = new Field(Config.getFieldSizeX(), Config.getFieldSizeY());
  }

  private void initSnake() {
    int snakeSize = Config.getSnakeStartSize();
    List<Cell> snakeCells = new ArrayList<>();

    for (int i = 0; i < snakeSize; i++) {
      Cell cell = new Cell();
      Coords coords = new Coords(i, 0);
      cell.setCoords(coords);
      field.setCellObjectByCoords(cell, coords);
      snakeCells.add(cell);
    }

    SnakeObject snakeObject =
        new SnakeObject(snakeCells, Direction.RIGHT, Config.getSnakeStartSpeed());
    SnakeThread snakeThread = new SnakeThread(snakeObject, field, this);

    snakeCells.forEach(cellObject -> objectByCellObject.put(cellObject, snakeObject));
    threadByObject.put(snakeObject, snakeThread);
  }

  private void initFrogs() {
    int frogAmount = Config.getFrogsAmount();
    List<FrogObject> frogs = new ArrayList<>();

    for (int i = 0; i < frogAmount; i++) {
      FrogObject object = createFrog();

      if (object == null) {
        break;
      }

      frogs.add(object);
    }

    frogs.forEach(frog -> {
      FrogThread thread = new FrogThread(frog, field, this);
      threadByObject.put(frog, thread);
    });
  }

  private FrogObject createFrog() {
    int fieldArea = Config.getFieldSizeX() * Config.getFieldSizeY();

    if (objectByCellObject.size() < fieldArea) {
      Random random = new Random();
      int coordX;
      int coordY;
      Coords coords;

      do {
        coordX = random.nextInt(Config.getFieldSizeX());
        coordY = random.nextInt(Config.getFieldSizeY());
        coords = new Coords(coordX, coordY);
      } while (field.getCellObjectByCoords(coords) != null);

      Cell cell = new Cell();
      cell.setType(Type.FROG);
      cell.setCoords(coords);
      field.setCellObjectByCoords(cell, coords);

      Direction direction = RandomDirectionGenerator.getRandomObjectDirection();
      FrogObject frog = new FrogObject(cell, direction, 1);
      objectByCellObject.put(cell, frog);

      return frog;
    }

    return null;
  }
}
