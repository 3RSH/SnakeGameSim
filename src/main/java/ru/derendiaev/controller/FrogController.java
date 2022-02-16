package ru.derendiaev.controller;

import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.ImageIcon;
import lombok.Setter;
import ru.derendiaev.Config;
import ru.derendiaev.Main;
import ru.derendiaev.NotEnoughSpaceExeption;
import ru.derendiaev.model.RandomDirectionGenerator;
import ru.derendiaev.model.object.Coords;
import ru.derendiaev.model.object.MovableObject;
import ru.derendiaev.model.thread.FrogThread;
import ru.derendiaev.model.thread.MovableThread;
import ru.derendiaev.view.GameField;

/**
 * Created by DDerendiaev on 10-Feb-22.
 */
public class FrogController implements PropertyChangeListener {

  private final Image frogImage;

  @Setter
  private GameField gameField;
  public List<FrogThread> frogThreads;

  /**
   * FrogController constructor.
   */
  public FrogController() {
    ClassLoader classLoader = getClass().getClassLoader();

    ImageIcon headIcon = new ImageIcon(
        Objects.requireNonNull(classLoader.getResource("images/head.png")));
    frogImage = headIcon.getImage();
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String eventName = evt.getPropertyName();

    //MODEL -> VIEW
    if (eventName.equals("changeObjectCoords")) {
      Coords oldFrogCoords = (Coords) evt.getOldValue();
      Coords newFrogCoords = (Coords) evt.getNewValue();

      int cellX = oldFrogCoords.getCoordX();
      int cellY = oldFrogCoords.getCoordY();
      gameField.clearCell(cellX, cellY);

      cellX = newFrogCoords.getCoordX();
      cellY = newFrogCoords.getCoordY();
      gameField.paintCell(cellX, cellY, frogImage);

      //MODEL -> VIEW
    } else if (eventName.equals("threadIsDead")) {
      try {
        gameField.incrementPoints();
        FrogThread newFrog = createFrog();
        frogThreads.add(newFrog);
        startFrog(newFrog);
      } catch (NotEnoughSpaceExeption ignored) {
        //Do nothing because need only interrupt of method.
      }

      //VIEW -> MODEL
    } else if (eventName.equals("startGame")) {
      init(gameField);
      frogThreads.forEach(this::startFrog);

      //VIEW -> MODEL
    } else if (eventName.equals("stopGame")) {
      frogThreads.stream().filter(MovableThread::isLive).forEach(frog -> frog.setLive(false));
    }
  }

  /**
   * FrogController initialization.
   */
  public void init(GameField gameField) {
    this.gameField = gameField;
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

  private void startFrog(FrogThread frogThread) {
    Thread thread = new Thread(frogThread);
    thread.start();
  }

  private FrogThread createFrog() throws NotEnoughSpaceExeption {
    int fieldSizeX = Main.getFieldModel().getFieldCoords().length;
    int fieldSizeY = Main.getFieldModel().getFieldCoords()[0].length;
    int fieldArea = fieldSizeX * fieldSizeY;
    int frogCount = (int) frogThreads.stream().filter(MovableThread::isLive).count();

    if (frogCount + Config.getSnakeStartSize() + gameField.getPoints() < fieldArea) {
      List<Coords> frogCoords = Main.getFieldModel().getNewFrogCoords();
      MovableObject frog = new MovableObject(
          frogCoords, RandomDirectionGenerator.getRandomObjectDirection(), 1);
      return new FrogThread(frog, Main.getFieldModel(), this);
    } else {
      throw new NotEnoughSpaceExeption();
    }
  }
}
