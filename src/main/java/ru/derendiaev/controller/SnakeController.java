package ru.derendiaev.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import lombok.Setter;
import ru.derendiaev.model.Coords;
import ru.derendiaev.model.ModelManager;
import ru.derendiaev.view.GameField;

/**
 * Created by DDerendiaev on 03-Mar-22.
 */
public class SnakeController implements PropertyChangeListener {

  private int stepCounter = 0;

  @Setter
  private GameField gameField;

  @Setter
  private ModelManager modelManager;

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String eventName = evt.getPropertyName();

    //from model to view event
    if (eventName.equals("move")) {
      stepCounter++;
      gameField.repaintSnake((Coords) evt.getOldValue(), (List<Coords>) evt.getNewValue());

      //from model to view event
    } else if (eventName.equals("grow")) {
      stepCounter++;
      gameField.incrementPoints();
      gameField.repaintSnake((Coords) evt.getOldValue(), (List<Coords>) evt.getNewValue());

      //from model to view event
    } else if (eventName.equals("new")) {
      gameField.repaintSnake((Coords) evt.getOldValue(), (List<Coords>) evt.getNewValue());

      //from model to view event
    } else if (eventName.equals("over")) {
      gameField.paintGameOver();

      //from view to model event
    } else if (eventName.equals("startGame")) {
      modelManager.initModel();
      modelManager.startModel();

      //from view to model event
    } else if (eventName.equals("stopGame")) {
      modelManager.stopModel();

      //from view to model event
    } else if (eventName.equals("nextTenPoints")) {
      modelManager.incrementSnakeSpeed();

      //from view to model event
    } else if (eventName.equals("changeDirection")) {
      if (stepCounter > 0) {
        modelManager.changeSnakeDirection((boolean) evt.getNewValue());
        stepCounter = 0;
      }
    }
  }
}
