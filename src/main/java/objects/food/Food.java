package objects.food;

import java.awt.Color;

import block.Block;

public class Food extends Block implements IFood {

  static final int HEIGHT = 4;
  static final int WIDTH = 4;
  static final int PADDING = 14;

  static final Color COLOR = Color.WHITE;

  public Food(int x, int y) {
    super(x + PADDING, y + PADDING, HEIGHT, WIDTH, null, 0);
  }

  @Override
  public void move() {
    // Food does not move, so this method is intentionally left blank.
  }

  public Color getColor() {
    return COLOR;
  }
}
