package block;

import java.awt.Image;
import java.util.Collection;

import enums.DirectionEnum;

public abstract class Block implements IBlock {
  protected int x;
  protected int y;

  protected int height;
  protected int width;

  protected Image image;

  protected int startX;
  protected int startY;

  protected int velocityX = 0;
  protected int velocityY = 0;
  protected int availableVelocity = 0;

  protected DirectionEnum direction = DirectionEnum.UP;

  protected Block(int x, int y, int height, int width, Image image, int availableVelocity) {
    this.x = x;
    this.y = y;

    this.height = height;
    this.width = width;

    this.image = image;

    this.startX = x;
    this.startY = y;

    this.availableVelocity = availableVelocity;
  }

  public boolean checkCollisionWithAnotherBlock(IBlock block) {
    return this.x < block.getX() + block.getWidth() &&
        this.x + this.width > block.getX() &&
        this.y < block.getY() + block.getHeight() &&
        this.y + this.height > block.getY();
  }

  public void updateDirection(DirectionEnum direction) {
    this.direction = direction;
    updateVelocity();
  }

  public void updateVelocity() {

    if (this.direction == DirectionEnum.UP) {
      this.velocityX = 0;
      this.velocityY = -this.availableVelocity;
      return;
    }

    if (this.direction == DirectionEnum.DOWN) {
      this.velocityX = 0;
      this.velocityY = this.availableVelocity;
      return;
    }

    if (this.direction == DirectionEnum.LEFT) {
      this.velocityX = -this.availableVelocity;
      this.velocityY = 0;
      return;
    }

    if (this.direction == DirectionEnum.RIGHT) {
      this.velocityX = this.availableVelocity;
      this.velocityY = 0;
      return;
    }
  }

  public abstract void move();

  public Image getImage() {
    return this.image;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public DirectionEnum getDirection() {
    return this.direction;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  public void resetPosition() {
    this.x = this.startX;
    this.y = this.startY;
  }

  protected void applyVelocity() {
    this.x += this.velocityX;
    this.y += this.velocityY;
  }

  protected void undoMovement() {
    this.x -= this.velocityX;
    this.y -= this.velocityY;
    this.velocityX = 0;
    this.velocityY = 0;
  }

  protected boolean isCollidingWithAny(Collection<IBlock> blocks) {
    for (IBlock block : blocks) {
      if (this.checkCollisionWithAnotherBlock(block))
        return true;
    }
    return false;
  }
}
