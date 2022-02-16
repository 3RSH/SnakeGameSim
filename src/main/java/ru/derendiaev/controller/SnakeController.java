package ru.derendiaev.controller;

import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.ImageIcon;
import ru.derendiaev.Config;
import ru.derendiaev.Main;
import ru.derendiaev.model.object.Coords;
import ru.derendiaev.model.object.Direction;
import ru.derendiaev.model.object.MovableObject;
import ru.derendiaev.model.thread.SnakeThread;
import ru.derendiaev.view.GameField;

/**
 * Created by DDerendiaev on 08-Feb-22.
 */
public class SnakeController implements PropertyChangeListener {

  private final Image headImage;
  private final Image bodyImage;
  private final Image tailImage;
  private GameField gameField;
  private SnakeThread snakeThread;
  private int stepCounter;

  /**
   * SnakeController constructor.
   */
  public SnakeController() {
    ClassLoader classLoader = getClass().getClassLoader();

    ImageIcon headIcon = new ImageIcon(
        Objects.requireNonNull(classLoader.getResource("images/head.png")));
    headImage = headIcon.getImage();

    ImageIcon bodyIcon = new ImageIcon(
        Objects.requireNonNull(classLoader.getResource("images/body.png")));
    bodyImage = bodyIcon.getImage();

    ImageIcon tailIcon = new ImageIcon(
        Objects.requireNonNull(classLoader.getResource("images/tail.png")));
    tailImage = tailIcon.getImage();
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String eventName = evt.getPropertyName();

    //MODEL -> VIEW
    if (eventName.equals("changeObjectCoords")) {
      stepCounter++;

      List<Coords> oldSnakeCoords = (List<Coords>) evt.getOldValue();
      int cellX = oldSnakeCoords.get(oldSnakeCoords.size() - 1).getCoordX();
      int cellY = oldSnakeCoords.get(oldSnakeCoords.size() - 1).getCoordY();
      gameField.clearCell(cellX, cellY);

      List<Coords> newSnakeCoords = (List<Coords>) evt.getNewValue();

      for (int i = 0; i < newSnakeCoords.size(); i++) {
        if (i == 0) {
          gameField.paintCell(cellX, cellY, headImage);
        } else if (i == newSnakeCoords.size() - 1) {
          gameField.paintCell(cellX, cellY, tailImage);
        } else {
          gameField.paintCell(cellX, cellY, bodyImage);
        }
      }

      //MODEL -> VIEW
    } else if (eventName.equals("threadIsDead")) {
      gameField.stopGame();

      //VIEW -> MODEL
    } else if (eventName.equals("changeDirection")) {
      boolean changeDirection = (boolean) evt.getNewValue();

      if (stepCounter > 0) {
        MovableObject snake = snakeThread.getSnake();
        int newDirectionIndex = changeDirection
            ? snake.getDirection().ordinal() + 1
            : snake.getDirection().ordinal() - 1;

        if (newDirectionIndex > Direction.values().length - 1) {
          newDirectionIndex = 0;
        } else if (newDirectionIndex < 0) {
          newDirectionIndex = Direction.values().length - 1;
        }

        snake.setDirection(Direction.values()[newDirectionIndex]);
        stepCounter = 0;
      }

      //VIEW -> MODEL
    } else if (eventName.equals("startGame")) {
      startSnake();

      //VIEW -> MODEL
    } else if (eventName.equals("stopGame")) {
      snakeThread.setLive(false);
      init(gameField);

      //VIEW -> MODEL
    } else if (eventName.equals("nextTenPoints")) {
      snakeThread.getSnake().setSpeed(snakeThread.getSnake().getSpeed() + (int) evt.getNewValue());
    }
  }

  /**
   * SnakeController initialization.
   */
  public void init(GameField gameField) {
    this.gameField = gameField;
    List<Coords> snakeAllCoords = new ArrayList<>();

    for (int i = Config.getSnakeStartSize(); i > 0; i--) {
      snakeAllCoords.add(new Coords(i - 1, 0));
    }

    MovableObject snake =
        new MovableObject(snakeAllCoords, Direction.RIGHT, Config.getSnakeStartSpeed());
    snakeThread = new SnakeThread(snake, Main.getFieldModel(), this);
  }

  private void startSnake() {
    Thread thread = new Thread(snakeThread);
    thread.start();
  }
}
