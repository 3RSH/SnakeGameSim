package ru.derendiaev.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by DDerendiaev on 05-Feb-22.
 */
public class Entity {

  private final PropertyChangeSupport observer = new PropertyChangeSupport(this);

  @Getter
  private List<Cell> cells;

  @Getter
  @Setter
  private boolean isLive;

  @Getter
  @Setter
  private Direction direction;

  @Getter
  @Setter
  private int speed;

  @Getter
  @Setter
  private int points;

  public void setCells(List<Cell> cells) {
    observer.firePropertyChange("cells", this.cells, cells);
    this.cells = cells;
  }

  public void kill() {
    observer.firePropertyChange("isLive", this.isLive, false);
    this.isLive = false;
  }

  public void addListener(PropertyChangeListener listener) {
    observer.addPropertyChangeListener(listener);
  }
}
