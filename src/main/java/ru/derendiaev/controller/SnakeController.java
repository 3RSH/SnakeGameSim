package ru.derendiaev.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import ru.derendiaev.model.object.Cell;
import ru.derendiaev.model.object.Direction;
import ru.derendiaev.model.object.MovableObject;
import ru.derendiaev.model.thread.SnakeThread;
import ru.derendiaev.view.GameField;

/**
 * Created by DDerendiaev on 08-Feb-22.
 */
public class SnakeController implements PropertyChangeListener {

  GameField gameField;
  SnakeThread snakeThread;

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String eventName = evt.getPropertyName();

    //from model to view event
    if (eventName.equals("changeCells")) {
      List<Cell> cells = new ArrayList<>();

      cells.addAll((List<Cell>) evt.getNewValue());
      cells.addAll((List<Cell>) evt.getOldValue());

      gameField.repaintCells(cells);

      //from model to view event
    } else if (eventName.equals("dieThread")) {
      gameField.stopGame;

      //from view to model event
    } else if (eventName.equals("changeDirection")) {
      int newDirection = (int) evt.getNewValue();

      if (snakeThread.getStepCounter() > 0) {
        MovableObject snake = snakeThread.getSnake();

        if (newDirection == 0 && snake.getDirection() != Direction.LEFT) {
          snake.setDirection(Direction.RIGHT);
        } else if (newDirection == 1 && snake.getDirection() != Direction.UP) {
          snake.setDirection(Direction.DOWN);
        } else if (newDirection == 2 && snake.getDirection() != Direction.RIGHT) {
          snake.setDirection(Direction.LEFT);
        } else if (newDirection == 3 && snake.getDirection() != Direction.DOWN) {
          snake.setDirection(Direction.UP);
        }
      }

      //from view to model event
    } else if (eventName.equals("stopGame") && snakeThread.isLive()) {
      snakeThread.setLive(false);
    }
  }
}
