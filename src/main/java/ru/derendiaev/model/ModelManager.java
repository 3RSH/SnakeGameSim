package ru.derendiaev.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.Getter;
import lombok.Setter;
import ru.derendiaev.Config;
import ru.derendiaev.model.object.Coords;
import ru.derendiaev.model.object.Direction;
import ru.derendiaev.model.object.MovableObject;
import ru.derendiaev.model.thread.FrogThread;
import ru.derendiaev.model.thread.MovableThread;
import ru.derendiaev.model.thread.SnakeThread;

/**
 * Created by DDerendiaev on 16-Feb-22.
 */
public class ModelManager {

  private Field field;
  private SnakeThread snakeThread;
  private List<FrogThread> frogThreads;

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
    frogThreads.forEach(service::execute);
  }


  /**
   * Stop model's threads.
   */
  public void stopModel() {
    modelIsRunning = false;
    snakeThread.setLive(false);
    frogThreads.forEach(frog -> frog.setLive(false));
  }

  /**
   * Stop frog's thread by index and create new one.
   */
  public synchronized void respawnFrog(int frogIndex) {
    killFrogByIndex(frogIndex);

    if (modelIsRunning) {
      FrogThread frogThread = createFrog();

      if (frogThread != null) {
        frogThread.setIndex(frogIndex);
        frogThreads.set(frogIndex, frogThread);
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

    for (int i = 0; i < snakeAllCoords.size(); i++) {
      if (i == 0) {
        field.setCoordsCellType(snakeAllCoords.get(i), CellType.HEAD);
      } else if (i == snakeAllCoords.size() - 1) {
        field.setCoordsCellType(snakeAllCoords.get(i), CellType.TAIL);
      } else {
        field.setCoordsCellType(snakeAllCoords.get(i), CellType.BODY);
      }
    }

    MovableObject snake =
        new MovableObject(snakeAllCoords, Direction.RIGHT, Config.getSnakeStartSpeed());
    snakeThread = new SnakeThread(snake, field, this);
  }

  private void initFrogs() {
    frogThreads = new ArrayList<>();
    int frogAmount = Config.getFrogsAmount();

    for (int i = 0; i < frogAmount; i++) {
      FrogThread frogThread = createFrog();

      if (frogThread != null) {
        frogThread.setIndex(i);
        frogThreads.add(frogThread);
      }
    }
  }

  private FrogThread createFrog() {
    int fieldArea = field.getFieldCoords().length * field.getFieldCoords()[0].length;
    int frogCount = (int) frogThreads.stream().filter(MovableThread::isLive).count();

    if (frogCount + snakeThread.getSnake().getAllCoords().size() < fieldArea) {
      List<Coords> frogCoords = field.getNewFrogCoords();

      field.setCoordsCellType(frogCoords.get(0), CellType.FROG);

      MovableObject frog = new MovableObject(
          frogCoords, RandomDirectionGenerator.getRandomObjectDirection(), 1);
      return new FrogThread(frog, field, this);
    }

    return null;
  }

  private void killFrogByIndex(int frogIndex) {
    frogThreads.get(frogIndex).setLive(false);
  }
}
