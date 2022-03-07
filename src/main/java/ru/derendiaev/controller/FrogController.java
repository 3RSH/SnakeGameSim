package ru.derendiaev.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import lombok.Setter;
import ru.derendiaev.model.Coords;
import ru.derendiaev.view.GameField;

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
    if (eventName.equals("move")) {
      gameField.repaintFrog((Coords) evt.getOldValue(), (Coords) evt.getNewValue());

      //from model to view event
    } else if (eventName.equals("new")) {
      gameField.repaintFrog((Coords) evt.getOldValue(), (Coords) evt.getNewValue());
    }
  }
}
