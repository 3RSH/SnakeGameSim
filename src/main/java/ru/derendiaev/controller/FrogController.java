package ru.derendiaev.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import lombok.Setter;
import ru.derendiaev.model.Coords;
import ru.derendiaev.view.GameField;
import ru.derendiaev.view.ViewCoords;

/**
 * Created by DDerendiaev on 03-Mar-22.
 */
public class FrogController implements PropertyChangeListener {

  @Setter
  public GameField gameField;

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String eventName = evt.getPropertyName();

    //from model to view event
    if (eventName.equals("move") || eventName.equals("new")) {
      gameField.repaintFrog(
          coordsConvertion((Coords) evt.getOldValue()),
          coordsConvertion((Coords) evt.getNewValue()));
      gameField.paintPoints();
    }
  }

  private ViewCoords coordsConvertion(Coords coords) {
    return coords != null ? new ViewCoords(coords.getCoordX(), coords.getCoordY()) : null;
  }
}
