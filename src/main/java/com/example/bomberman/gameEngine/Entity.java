package com.example.bomberman.gameEngine;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public abstract class Entity {

  protected Animation activeAnimation = null;
  protected Point2D position;
  protected Sprite sprite;
  protected Rectangle collision;
  protected float rotation;
  protected float scale = 1;

  public Entity(double x, double y, Sprite sprite) {
    position = new Point2D(x, y);
    this.sprite = sprite;
    if (sprite != null) {
      this.collision = new Rectangle(x, y, sprite.getRealWidth(), sprite.getRealHeight());
    }
  }

  public Entity(Point2D position, Sprite sprite) {
    this.position = position;
    this.sprite = sprite;
    if (sprite != null) {
      this.collision = new Rectangle(position.getX(), position.getY(), sprite.getRealWidth(),
              sprite.getRealHeight());
    }
  }

  public abstract void update(double deltaTime);

  public Point2D getCenter() {
    Point2D pos = getPosition();
    return new Point2D(pos.getX() + sprite.getRealWidth() / 2,
            pos.getY() + sprite.getRealHeight() / 2);
  }

  public Point2D getPosition() {
    return position;
  }

  public void setPosition(Point2D position) {
    this.position = position;
  }

  public Rectangle getCollision() {
    return collision;
  }

  public void setCollision(Rectangle collision) {
    this.collision = collision;
  }

  public float getRotation() {
    return rotation;
  }

  public void setRotation(float rotation) {
    this.rotation = rotation;
  }

  public float getScale() {
    return scale;
  }

  public void setScale(float scale) {
    this.scale = scale;
  }

  public Animation getActiveAnimation() {
    return activeAnimation;
  }

  public void setActiveAnimation(Animation activeAnimation) {
    this.activeAnimation = activeAnimation;
  }

  public Sprite getSprite() {
    return sprite;
  }
}
