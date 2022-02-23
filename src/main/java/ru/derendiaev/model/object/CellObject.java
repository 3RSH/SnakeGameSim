package ru.derendiaev.model.object;

import lombok.Getter;
import lombok.Setter;
import ru.derendiaev.model.Coords;

/**
 * Created by DDerendiaev on 05-Feb-22.
 */
public class CellObject {

  @Getter
  @Setter
  private Coords coords;

  @Getter
  @Setter
  private ObjectType type;


  /**
   * CellObject constructor.
   */
  public CellObject(ObjectType type) {
    this.type = type;
  }
}
