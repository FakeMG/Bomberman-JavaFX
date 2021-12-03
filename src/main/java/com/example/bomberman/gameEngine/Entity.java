package com.example.bomberman.gameEngine;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public abstract class Entity {

  protected AnimationController animationController;
  protected Point2D position;
  protected Sprite sprite;
  protected Rectangle collision;
  protected float rotation;
  protected boolean isDead = false;
  protected float scale = 1;

  public Entity(double x, double y, Sprite sprite) {
    position = new Point2D(x, y);
    this.sprite = sprite;
    if (sprite != null) {
      this.collision = new Rectangle(x, y, sprite.getRealWidth(), sprite.getRealHeight());
    }
  }

  public Entity(Point2D position, Sprite sprite) {
    this(position.getX(), position.getY(), sprite);
  }

  public Entity(double x, double y, Animation animation) {
    position = new Point2D(x, y);
    if (animation != null) {
      sprite = animation.getSprites().get(0);
      animationController = new AnimationController(animation);
      this.collision = new Rectangle(x, y, animation.getSprites().get(0).getRealWidth(),
              animation.getSprites().get(0).getRealHeight());
    }
  }

  public Entity(Point2D position, Animation animation) {
    this(position.getX(), position.getY(), animation);
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

  public AnimationController getAnimationController() {
    return animationController;
  }

  public Sprite getSprite() {
    return sprite;
  }

  public boolean isDead() {
    return isDead;
  }

  public void setDead(boolean dead) {
    isDead = dead;
  }
}
