package com.example.bomberman.gameEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class Renderer {

  Canvas canvas;
  GraphicsContext context;
  Image background = new Image(
          Objects.requireNonNull(Renderer.class.getResourceAsStream("space.png")));
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
    context.clearRect(0,0, canvas.getWidth(), canvas.getHeight());

    if (background != null) {
      context.drawImage(background, 0, 0, background.getWidth(), background.getHeight());
    }

    for (Entity entity : entities) {
      transformContext(entity);
      Point2D pos = entity.getPosition();
      context.drawImage(
              entity.getTexture(),
              pos.getX(),
              pos.getY(),
              entity.getCollision().getWidth(),
              entity.getCollision().getHeight()
      );
    }
    context.restore();
  }

  public void prepare() {
    context.setFill(new Color(0.68, 0.68, 0.68, 1.0));
    context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }

  /**
   * Rotate entity by its rotation value.
   * @param entity an entity.
   */
  private void transformContext(Entity entity) {
    //Set transform for GraphicsContext using entity's rotation value.
    Point2D centre = entity.getCenter();
    Rotate r = new Rotate(entity.getRotation(), centre.getX(), centre.getY());
    context.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
  }
}
