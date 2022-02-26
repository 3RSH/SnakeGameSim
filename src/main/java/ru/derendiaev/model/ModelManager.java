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
import ru.derendiaev.model.object.CellObject;
import ru.derendiaev.model.object.CellObjectType;
import ru.derendiaev.model.object.Direction;
import ru.derendiaev.model.object.FrogObject;
import ru.derendiaev.model.object.SnakeObject;
import ru.derendiaev.model.thread.FrogThread;
import ru.derendiaev.model.thread.MovableThread;
import ru.derendiaev.model.thread.SnakeThread;

/**
 * Created by DDerendiaev on 16-Feb-22.
 */
public class ModelManager {

  private Field field;
  private Map<CellObject, MovableThread<?>> threadsByCellObjects;
  private List<MovableThread<?>> startModelThreads;

  @Getter
  @Setter
  private boolean modelIsRunning;

  /**
   * Model initialization.
   */
  public void initModel() {
    threadsByCellObjects = new HashMap<>();
    startModelThreads = new ArrayList<>();
    initField();
    initSnake();
    initFrogs();
  }

  /**
   * Run model's threads.
   */
  public void startModel() {
    modelIsRunning = true;
    ExecutorService service = Executors.newFixedThreadPool(startModelThreads.size());
    startModelThreads.forEach(service::execute);
    service.shutdown();
  }


  /**
   * Stop model's threads.
   */
  public void stopModel() {
    modelIsRunning = false;
    threadsByCellObjects.entrySet().stream()
        .filter(entry -> entry.getValue().isLive())
        .forEach(entry -> entry.getValue().setLive(false));
  }

  /**
   * Stop frogThread and set snakeThread on CellObject.
   */
  public void snakeEatFrog(CellObject frogObject, MovableThread<SnakeObject> snakeThread) {
    threadsByCellObjects.get(frogObject).setLive(false);
    threadsByCellObjects.put(frogObject, snakeThread);
    respawnFrog();
  }

  private void initField() {
    field = new Field(Config.getFieldSizeX(), Config.getFieldSizeY());
  }

  private void initSnake() {
    int snakeSize = Config.getSnakeStartSize();
    List<CellObject> snakeCellObjects = new ArrayList<>();

    for (int i = 0; i < snakeSize; i++) {
      CellObject cellObject = new CellObject();
      Coords coords = new Coords(i, 0);
      cellObject.setCoords(coords);
      field.setCellObjectByCoords(cellObject, coords);
      snakeCellObjects.add(cellObject);
    }

    SnakeObject snakeObject =
        new SnakeObject(snakeCellObjects, Direction.RIGHT, Config.getSnakeStartSpeed());
    SnakeThread snakeThread = new SnakeThread(snakeObject, field, this);

    snakeObject.updateCellObjectsTypes();
    snakeCellObjects.forEach(cellObject -> threadsByCellObjects.put(cellObject, snakeThread));
    startModelThreads.add(snakeThread);
  }

  private void initFrogs() {
    int frogAmount = Config.getFrogsAmount();
    List<FrogObject> frogObjects = new ArrayList<>();

    for (int i = 0; i < frogAmount; i++) {
      FrogObject object = createFrog();

      if (object == null) {
        break;
      }

      frogObjects.add(object);
    }

    frogObjects.forEach(frogObject -> {
      FrogThread thread = new FrogThread(frogObject, field, this);
      threadsByCellObjects.put(frogObject.getHeadCellObject(), thread);
      startModelThreads.add(thread);
    });
  }

  private FrogObject createFrog() {
    int fieldArea = Config.getFieldSizeX() * Config.getFieldSizeY();

    if (threadsByCellObjects.size() < fieldArea) {
      Random random = new Random();
      int coordX;
      int coordY;
      Coords coords;

      do {
        coordX = random.nextInt(Config.getFieldSizeX());
        coordY = random.nextInt(Config.getFieldSizeY());
        coords = new Coords(coordX, coordY);
      } while (field.getCellObjectByCoords(coords) != null);

      CellObject cellObject = new CellObject();
      cellObject.setType(CellObjectType.FROG);
      cellObject.setCoords(coords);
      field.setCellObjectByCoords(cellObject, coords);

      Direction direction = RandomDirectionGenerator.getRandomObjectDirection();

      return new FrogObject(cellObject, direction, 1);
    }

    return null;
  }

  private void respawnFrog() {
    FrogObject frogObject = createFrog();

    if (frogObject != null) {
      CellObject frogCellObject = frogObject.getHeadCellObject();

      field.setCellObjectByCoords(frogCellObject, frogCellObject.getCoords());
      FrogThread frogThread = new FrogThread(frogObject, field, this);
      threadsByCellObjects.put(frogCellObject, frogThread);
      Thread thread = new Thread(frogThread);
      thread.start();
    }
  }
}
