package ru.derendiaev.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Setter;
import ru.derendiaev.model.Coords;
import ru.derendiaev.model.ModelManager;
import ru.derendiaev.view.GameField;
import ru.derendiaev.view.ViewCoords;

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
      List<Coords> modelPresent = (List<Coords>) evt.getNewValue();

      gameField.repaintSnake(
          coordsConvertion((Coords) evt.getOldValue()),
          coordsConvertion(modelPresent));
      gameField.paintPoints();

      //from model to view event
    } else if (eventName.equals("grow")) {
      stepCounter++;
      List<Coords> modelPresent = (List<Coords>) evt.getNewValue();

      gameField.repaintSnake(
          coordsConvertion((Coords) evt.getOldValue()),
          coordsConvertion(modelPresent));
      gameField.clearPoints();
      gameField.incrementPoints();
      gameField.paintPoints();

      //from model to view event
    } else if (eventName.equals("new")) {
      List<Coords> modelPresent = (List<Coords>) evt.getNewValue();

      gameField.repaintSnake(
          coordsConvertion((Coords) evt.getOldValue()),
          coordsConvertion(modelPresent));
      gameField.paintPoints();

      //from model to view event
    } else if (eventName.equals("over")) {
      gameField.paintGameOver();
      gameField.modelIsRunning = false;

      //from view to model event
    } else if (eventName.equals("startGame")) {
      modelManager.initModel();
      modelManager.startModel();
      gameField.modelIsRunning = true;

      //from view to model event
    } else if (eventName.equals("stopGame")) {
      modelManager.stopModel();
      gameField.modelIsRunning = false;

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

  private ViewCoords coordsConvertion(Coords coords) {
    return coords != null ? new ViewCoords(coords.getCoordX(), coords.getCoordY()) : null;
  }

  private List<ViewCoords> coordsConvertion(List<Coords> coords) {
    return coords.stream()
        .map(this::coordsConvertion)
        .collect(Collectors.toList());
  }
}
