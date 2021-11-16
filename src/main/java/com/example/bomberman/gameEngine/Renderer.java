package com.example.bomberman.gameEngine;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Renderer {

  Canvas canvas;
  GraphicsContext context;
  Image background;
  static List<Entity> entities = new ArrayList<>();

  public Renderer(Canvas canvas) {
    this.canvas = canvas;
    this.context = canvas.getGraphicsContext2D();
  }

  public static void addEntity(Entity entity) {
    entities.add(entity);
  }

  public void removeEntity(Entity entity) {
    entities.remove(entity);
  }

  public void clearEntities() {
    entities.clear();
  }

  public void setBackground(Image background) {
    this.background = background;
  }

  public void render() {
    //TODO: clear, save, restore ?

    context.save();

    if (background != null) {
      context.drawImage(background, 0, 0);
    }

    for (Entity entity : entities) {
//      transformContext(entity);
      Point2D pos = entity.getPosition();
      context.drawImage(
              entity.getTexture(),
              pos.getX(),
              pos.getY(),
              entity.getHitBox().getWidth(),
              entity.getHitBox().getHeight()
      );
    }
    context.restore();
  }

  public void prepare() {
    context.setFill(new Color(0.68, 0.68, 0.68, 1.0));
    context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }

//  private void transformContext(Entity entity) {
//    Point2D centre = entity.getCenter();
//    Rotate r = new Rotate(entity.getRotation(), centre.getX(), centre.getY());
//    context.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
//  }
}
