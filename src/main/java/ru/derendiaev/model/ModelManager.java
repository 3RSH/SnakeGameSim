package ru.derendiaev.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.Getter;
import lombok.Setter;
import ru.derendiaev.Config;
import ru.derendiaev.model.object.CellObject;
import ru.derendiaev.model.object.Direction;
import ru.derendiaev.model.object.MovableCellObject;
import ru.derendiaev.model.object.ObjectType;
import ru.derendiaev.model.thread.FrogThread;
import ru.derendiaev.model.thread.MovableThread;
import ru.derendiaev.model.thread.SnakeThread;

/**
 * Created by DDerendiaev on 16-Feb-22.
 */
public class ModelManager {

  private Field field;
  private Map<CellObject, MovableThread> threadsByObjects;
  private List<MovableThread> startModelThreads;

  @Getter
  @Setter
  private boolean modelIsRunning;

  /**
   * Model initialization.
   */
  public void initModel() {
    threadsByObjects = new HashMap<>();
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
    threadsByObjects.entrySet().stream()
        .filter(entry -> entry.getValue().isLive())
        .forEach(entry -> entry.getValue().setLive(false));
  }

  /**
   * Stop frogThread and set snakeThread on CellObject.
   */
  public void snakeEatFrog(CellObject frogObject, MovableThread snakeThread) {
    threadsByObjects.get(frogObject).setLive(false);
    threadsByObjects.put(frogObject, snakeThread);
  }

  private void initField() {
    field = new Field(Config.getFieldSizeX(), Config.getFieldSizeY());
  }

  private void initSnake() {
    int snakeSize = Config.getSnakeStartSize();
    List<MovableCellObject> objects = new ArrayList<>();

    for (int i = 0; i < snakeSize; i++) {
      MovableCellObject object;

      if (i == 0) {
        object =
            new MovableCellObject(ObjectType.TAIL, Direction.RIGHT, Config.getSnakeStartSpeed());
      } else if (i == objects.size() - 1) {
        object =
            new MovableCellObject(ObjectType.HEAD, Direction.RIGHT, Config.getSnakeStartSpeed());
      } else {
        object =
            new MovableCellObject(ObjectType.BODY, Direction.RIGHT, Config.getSnakeStartSpeed());
      }

      Coords coords = new Coords(i, 0);
      object.setCoords(coords);
      field.setObjectByCoords(object, coords);
      objects.add(object);
    }

    SnakeThread snakeThread = new SnakeThread(objects, field, this);
    objects.forEach(object -> threadsByObjects.put(object, snakeThread));
    startModelThreads.add(snakeThread);
  }

  private void initFrogs() {
    int frogAmount = Config.getFrogsAmount();
    List<MovableCellObject> objects = new ArrayList<>();

    for (int i = 0; i < frogAmount; i++) {
      MovableCellObject object = createFrog();

      if (object == null) {
        break;
      }

      objects.add(object);
    }

    objects.forEach(object -> {
      List<MovableCellObject> objectInList = new ArrayList<>(1);
      objectInList.add(object);
      FrogThread thread = new FrogThread(objectInList, field, this);
      threadsByObjects.put(object, thread);
      startModelThreads.add(thread);
    });
  }

  private MovableCellObject createFrog() {
    int fieldArea = Config.getFieldSizeX() * Config.getFieldSizeY();

    if (threadsByObjects.size() < fieldArea) {
      Random random = new Random();
      int coordX;
      int coordY;
      Coords coords;

      do {
        coordX = random.nextInt(Config.getFieldSizeX());
        coordY = random.nextInt(Config.getFieldSizeY());
        coords = new Coords(coordX, coordY);
      } while (field.getObjectByCoords(coords) != null);

      Direction direction = RandomDirectionGenerator.getRandomObjectDirection();
      MovableCellObject frogObject = new MovableCellObject(ObjectType.FROG, direction, 1);
      frogObject.setCoords(coords);
      field.setObjectByCoords(frogObject, coords);
      return frogObject;
    }

    return null;
  }
}
