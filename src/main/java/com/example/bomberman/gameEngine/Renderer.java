package com.example.bomberman.gameEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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

  public static void addEntity(List<? extends Entity> newEntities) {
    entities.addAll(newEntities);
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

  /**
   * render all entities.
   */
  public void render() {
    //TODO: clear, save, restore ?

    context.save();

    if (background != null) {
      context.drawImage(background, 32, 32, background.getWidth(), background.getHeight());
    }

    for (Entity entity : entities) {
      transformContext(entity);
      Point2D pos = entity.getPosition();
      Image entityTexture = entity.getTexture();
      if (entity.getActiveAnimation() != null) {
        entityTexture = entity.getActiveAnimation().getCurrentFrame();
      }
      context.drawImage(
              entityTexture,
              pos.getX(),
              pos.getY(),
              entity.getCollision().getWidth(),
              entity.getCollision().getHeight()
      );
    }
    context.restore();
  }

  /**
   * render image.
   *
   * @param image image.
   * @param x     x pos.
   * @param y     y pos.
   */
  public void render(Image image, double x, double y) {
    try {
      context.drawImage(image, x, y);
    } catch (Exception e) {
      System.out.println("Can not draw image!");
      e.printStackTrace();
    }
  }

  public void clear() {
//    context.setFill(new Color(0.68, 0.68, 0.68, 1.0));
//    context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }

  /**
   * Rotate entity by its rotational value.
   *
   * @param entity an entity.
   */
  private void transformContext(Entity entity) {
    //Set transform for GraphicsContext using entity's rotation value.
    Point2D centre = entity.getCenter();
    Rotate r = new Rotate(entity.getRotation(), centre.getX(), centre.getY());
    context.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
  }
}
