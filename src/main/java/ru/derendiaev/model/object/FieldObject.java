package ru.derendiaev.model.object;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by DDerendiaev on 05-Feb-22.
 */
public class FieldObject {

  @Getter
  @Setter
  private List<Coords> allCoords;

  /**
   * Field fieldObject constructor.
   */
  public FieldObject(List<Coords> allObjectCoords) {
    allCoords = allObjectCoords;
  }
}
