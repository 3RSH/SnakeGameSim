package ru.derendiaev.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import lombok.Setter;
import ru.derendiaev.model.object.Cell;
import ru.derendiaev.model.object.Direction;
import ru.derendiaev.model.object.MovableObject;
import ru.derendiaev.model.thread.SnakeThread;
import ru.derendiaev.view.GameField;

/**
 * Created by DDerendiaev on 08-Feb-22.
 */
public class SnakeController implements PropertyChangeListener {

  private int stepCounter;

  @Setter
  private GameField gameField;

  @Setter
  private SnakeThread snakeThread;

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String eventName = evt.getPropertyName();

    //from model to view event
    if (eventName.equals("changeCells")) {
      stepCounter++;
      gameField.setSnakeCoords((List<Cell>) evt.getNewValue());
      gameField.repaint();

      //from model to view event
    } else if (eventName.equals("dieThread")) {
      gameField.getObserver().removePropertyChangeListener(this);
      gameField.stopGame();

      //from view to model event
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

      //from view to model event
    } else if (eventName.equals("stopGame") && snakeThread.isLive()) {
      if (snakeThread.isLive()) {
        snakeThread.setLive(false);
      }
    }
  }
}
