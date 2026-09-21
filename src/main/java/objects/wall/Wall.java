package objects.wall;

import java.awt.Image;

import javax.swing.ImageIcon;

import block.Block;

public class Wall extends Block implements IWall {

  static Image wallImage = new ImageIcon(Wall.class.getResource("/images/wall/wall.png")).getImage();

  public Wall(int x, int y, int height, int width) {
    super(x, y, height, width, wallImage, 0);
  }

  @Override
  public void move() {
    // Walls do not move, so this method is intentionally left blank.
  }
}
