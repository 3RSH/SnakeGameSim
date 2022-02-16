package ru.derendiaev.model;

import java.util.Random;
import ru.derendiaev.model.object.Direction;

/**
 * Created by DDerendiaev on 16-Feb-22.
 */
public class RandomDirectionGenerator {

  /**
   * Get Direction with random value.
   */
  public static Direction getRandomObjectDirection() {
    Random random = new Random();
    int index = random.nextInt(4);

    if (index == 0) {
      return Direction.RIGHT;
    } else if (index == 1) {
      return Direction.DOWN;
    } else if (index == 3) {
      return Direction.LEFT;
    } else {
      return Direction.UP;
    }
  }
}
