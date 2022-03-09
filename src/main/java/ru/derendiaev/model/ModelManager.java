package ru.derendiaev.model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import ru.derendiaev.Config;
import ru.derendiaev.controller.FrogController;
import ru.derendiaev.controller.SnakeController;
import ru.derendiaev.model.object.Cell;
import ru.derendiaev.model.object.Direction;
import ru.derendiaev.model.object.FrogObject;
import ru.derendiaev.model.object.MovableObject;
import ru.derendiaev.model.object.SnakeObject;
import ru.derendiaev.model.object.Type;
import ru.derendiaev.model.thread.FrogThread;
import ru.derendiaev.model.thread.MovableThread;
import ru.derendiaev.model.thread.SnakeThread;

/**
 * Created by DDerendiaev on 16-Feb-22.
 */
public class ModelManager {

  private final PropertyChangeSupport snakeObserver = new PropertyChangeSupport(this);
  private final PropertyChangeSupport frogObserver = new PropertyChangeSupport(this);
  private final SnakeController snakeController;
  private final FrogController frogController;

  private Field field;
  private SnakeObject snakeObject;
  private Map<MovableObject, MovableThread<?>> threadByObject;
  private Map<Cell, MovableObject> objectByCellObject;

  @Getter
  @Setter
  private boolean modelIsRunning;

  /**
   * Model manager constructor.
   */
  public ModelManager(SnakeController snakeController, FrogController frogController) {
    this.snakeController = snakeController;
    this.frogController = frogController;

    snakeObserver.addPropertyChangeListener(snakeController);
    frogObserver.addPropertyChangeListener(frogController);
  }

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

    snakeObserver.firePropertyChange("over", true, false);
  }

  /**
   * Kill frog-thread.
   */
  public void killFrog(Cell frogCell, MovableObject snake) {
    MovableObject frog = objectByCellObject.get(frogCell);
    threadByObject.get(frog).setLive(false);
    threadByObject.remove(frog);
    objectByCellObject.put(frogCell, snake);
  }

  /**
   * Create new frog-thread.
   */
  public void respawnFrog() {
    FrogObject frogObject = createFrog();

    if (frogObject != null) {
      FrogThread frogThread = new FrogThread(frogObject, field, this, frogController);
      threadByObject.put(frogObject, frogThread);
      Thread thread = new Thread(frogThread);
      thread.start();
    }
  }

  /**
   * Increment snake-object speed.
   */
  public void incrementSnakeSpeed() {
    snakeObject.setSpeed(snakeObject.getSpeed() + 5);
  }

  /**
   * Change snake-object direction.
   */
  public void changeSnakeDirection(Boolean change) {
    Direction direction = snakeObject.getDirection();

    switch (direction) {
      case UP:
        direction = change ? Direction.RIGHT : Direction.LEFT;
        break;
      case DOWN:
        direction = change ? Direction.LEFT : Direction.RIGHT;
        break;
      case RIGHT:
        direction = change ? Direction.DOWN : Direction.UP;
        break;
      default:
        direction = change ? Direction.UP : Direction.DOWN;
    }

    snakeObject.setDirection(direction);
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
      field.setCellObjectByCoords(cell, coords);
      snakeCells.add(cell);
    }

    snakeObject =
        new SnakeObject(snakeCells, Direction.RIGHT, Config.getSnakeStartSpeed());
    SnakeThread snakeThread = new SnakeThread(snakeObject, field, this, snakeController);

    snakeCells.forEach(cellObject -> objectByCellObject.put(cellObject, snakeObject));
    threadByObject.put(snakeObject, snakeThread);

    List<Coords> snakeCoords =
        snakeCells.stream().map(Cell::getCoords).collect(Collectors.toList());
    snakeObserver.firePropertyChange("new", null, snakeCoords);
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
      FrogThread thread = new FrogThread(frog, field, this, frogController);
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
      field.setCellObjectByCoords(cell, coords);

      Direction direction = RandomDirectionGenerator.getRandomObjectDirection();
      FrogObject frog = new FrogObject(cell, direction, 1);
      objectByCellObject.put(cell, frog);
      frogObserver.firePropertyChange("new", null, coords);

      return frog;
    }

    return null;
  }
}
