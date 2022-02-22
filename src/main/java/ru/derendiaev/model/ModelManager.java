package ru.derendiaev.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.Getter;
import lombok.Setter;
import ru.derendiaev.Config;
import ru.derendiaev.model.object.Coords;
import ru.derendiaev.model.object.Direction;
import ru.derendiaev.model.object.MovableObject;
import ru.derendiaev.model.thread.FrogThread;
import ru.derendiaev.model.thread.SnakeThread;

/**
 * Created by DDerendiaev on 16-Feb-22.
 */
public class ModelManager {

  private Field field;
  private SnakeThread snakeThread;
  private Map<String, FrogThread> frogThreads;

  @Getter
  @Setter
  private boolean modelIsRunning;

  /**
   * Model initialization.
   */
  public void initModel() {
    initField();
    initSnake();
    initFrogs();
  }

  /**
   * Run model's threads.
   */
  public void startModel() {
    modelIsRunning = true;
    ExecutorService service = Executors.newFixedThreadPool(frogThreads.size() + 1);
    service.execute(snakeThread);

    for (Entry<String, FrogThread> entry : frogThreads.entrySet()) {
      service.execute(entry.getValue());
    }
    service.shutdown();
  }


  /**
   * Stop model's threads.
   */
  public void stopModel() {
    modelIsRunning = false;
    snakeThread.setLive(false);
    for (Entry<String, FrogThread> entry : frogThreads.entrySet()) {
      entry.getValue().setLive(false);
    }
  }

  /**
   * Stop frog's thread by index and create new one.
   */
  public void respawnFrog(String frogName) {
    frogThreads.get(frogName).setLive(false);

    if (modelIsRunning) {
      FrogThread frogThread = createFrog(frogName);

      if (frogThread != null) {
        frogThread.setName(frogName);
        frogThreads.put(frogName, frogThread);
        Thread frog = new Thread(frogThread);
        frog.start();
      }
    }
  }

  private void initField() {
    field = new Field(Config.getFieldSizeX(), Config.getFieldSizeY());
  }

  private void initSnake() {
    List<Coords> snakeAllCoords = new ArrayList<>();

    for (int i = Config.getSnakeStartSize(); i > 0; i--) {
      snakeAllCoords.add(new Coords(i - 1, 0));
    }

    CellType head = new CellType();
    CellType tail = new CellType();
    CellType body = new CellType();
    head.setName("head");
    tail.setName("tail");
    body.setName("body");

    for (int i = 0; i < snakeAllCoords.size(); i++) {
      if (i == 0) {
        field.setCoordsCellType(snakeAllCoords.get(i), head);
      } else if (i == snakeAllCoords.size() - 1) {
        field.setCoordsCellType(snakeAllCoords.get(i), tail);
      } else {
        field.setCoordsCellType(snakeAllCoords.get(i), body);
      }
    }

    MovableObject snake =
        new MovableObject(snakeAllCoords, Direction.RIGHT, Config.getSnakeStartSpeed());
    snakeThread = new SnakeThread(snake, field, this);
  }

  private void initFrogs() {
    frogThreads = new HashMap<>();
    int frogAmount = Config.getFrogsAmount();

    for (int i = 0; i < frogAmount; i++) {
      String frogName = "frog" + i;
      FrogThread frogThread = createFrog(frogName);

      if (frogThread != null) {
        frogThread.setName(frogName);
        frogThreads.put(frogThread.getName(), frogThread);
      }
    }
  }

  private FrogThread createFrog(String cellTypeName) {
    int fieldArea = Config.getFieldSizeX() * Config.getFieldSizeY();

    if (frogThreads.size() + snakeThread.getSnake().getAllCoords().size() < fieldArea) {
      List<Coords> frogCoords = new ArrayList<>();
      frogCoords.add(field.getAnyFreeCoords());

      CellType frog = new CellType();
      frog.setName(cellTypeName);

      field.setCoordsCellType(frogCoords.get(0), frog);

      MovableObject frogObject = new MovableObject(
          frogCoords, RandomDirectionGenerator.getRandomObjectDirection(), 1);
      return new FrogThread(frogObject, field, this);
    }

    return null;
  }
}
