package com.example.bomberman.gameEngine;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public abstract class Entity {

  private Point2D position;
  private Image texture;
  private Rectangle2D hitBox;

  //TODO: constructor

  public Entity(double x, double y, Image texture) {
    position = new Point2D(x, y);
    this.texture = texture;
    this.hitBox = new Rectangle2D(x, y, texture.getWidth(), texture.getHeight());
  }

  public abstract void update();

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

  public Rectangle2D getHitBox() {
    return hitBox;
  }

  public void setHitBox(Rectangle2D hitBox) {
    this.hitBox = hitBox;
  }
}
