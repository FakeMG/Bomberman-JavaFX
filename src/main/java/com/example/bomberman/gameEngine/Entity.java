package com.example.bomberman.gameEngine;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public abstract class Entity {

  protected Animation activeAnimation = null;
  protected Point2D position;
  protected Image texture;
  protected Rectangle collision;
  protected float rotation;
  protected float scale = 1;

  public Entity(double x, double y, Image texture) {
    position = new Point2D(x, y);
    this.texture = texture;
    if (texture != null) {
      this.collision = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }
  }

  public Entity(Point2D position, Image texture) {
    this.position = position;
    this.texture = texture;
    if (texture != null) {
      this.collision = new Rectangle(position.getX(), position.getY(), texture.getWidth(),
              texture.getHeight());
    }
  }

  public abstract void update();

  public Point2D getCenter() {
    Point2D pos = getPosition();
    return new Point2D(pos.getX() + collision.getWidth() / 2,
            pos.getY() + collision.getHeight() / 2);
  }

  public Point2D getPosition() {
    return position;
  }

  public void setPosition(Point2D position) {
    this.position = position;
  }

  public Image getTexture() {
    return texture;
  }

  public void setTexture(Image texture) {
    this.texture = texture;
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
}
