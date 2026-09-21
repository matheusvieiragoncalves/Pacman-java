package block;

import java.awt.Image;

import enums.DirectionEnum;

public interface IBlock {

  boolean checkCollisionWithAnotherBlock(IBlock block);

  DirectionEnum getDirection();

  Image getImage();

  int getX();

  int getY();

  int getWidth();

  int getHeight();

  void move();

  void resetPosition();

  void setImage(Image image);

  void updateDirection(DirectionEnum direction);

  void updateVelocity();
}
