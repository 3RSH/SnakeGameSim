package ru.derendiaev.model.object;

import lombok.Getter;
import lombok.Setter;
import ru.derendiaev.model.Coords;

/**
 * Created by DDerendiaev on 05-Feb-22.
 */
@Getter
@Setter
public class Cell {

  private Coords coords;
  private Type type;
}
