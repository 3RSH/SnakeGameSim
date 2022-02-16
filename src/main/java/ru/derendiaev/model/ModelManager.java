package ru.derendiaev.model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import ru.derendiaev.Config;
import ru.derendiaev.NotEnoughSpaceExeption;
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

  public void initModel() {
    initField();
    initSnake();
    initFrogs();
  }

  public FrogThread createFrog() throws NotEnoughSpaceExeption {
    int fieldArea = Config.getFieldSizeX() * Config.getFieldSizeY();
    int frogCount = (int) frogThreads.stream().filter(MovableThread::isLive).count();

    if (frogCount + snakeThread.getSnake().getAllCoords().size() < fieldArea) {
      List<Coords> frogCoords = field.getNewFrogCoords();

      field.setCoordsCellType(frogCoords.get(0), CellType.FROG);

      MovableObject frog = new MovableObject(
          frogCoords, RandomDirectionGenerator.getRandomObjectDirection(), 1);
      return new FrogThread(frog, field);
    } else {
      throw new NotEnoughSpaceExeption();
    }
  }

  public void setSnakeListener(PropertyChangeListener listener) {
    snakeThread.getObserver().addPropertyChangeListener(listener);
  }

  public void setFrogsListener(PropertyChangeListener listener) {
    for (FrogThread frog : frogThreads) {
      frog.getObserver().addPropertyChangeListener(listener);
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
    snakeThread = new SnakeThread(snake, field);
  }

  private void initFrogs() {
    frogThreads = new ArrayList<>();
    int frogsCount = (Config.getFieldSizeX() + Config.getFieldSizeY()) / 10;

    for (int i = 0; i < frogsCount; i++) {
      try {
        FrogThread frogThread = createFrog();
        frogThreads.add(frogThread);
      } catch (NotEnoughSpaceExeption ignored) {
        //Do nothing because need only interrupt of method.
      }
    }
  }
}
