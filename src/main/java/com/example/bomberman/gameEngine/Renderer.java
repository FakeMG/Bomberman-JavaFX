package com.example.bomberman.gameEngine;

import com.example.bomberman.game.Map;
import com.example.bomberman.game.entities.tile.LayeredTile;
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

  public Renderer(Canvas canvas) {
    this.canvas = canvas;
    this.context = canvas.getGraphicsContext2D();
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

    renderEntities(Map.tiles);
    renderEntities(Map.bombs);
    renderEntities(Map.flames);
    renderEntities(Map.players);
    renderEntities(Map.mobs);
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

  public <T extends Entity> void renderEntities(List<T> entities) {
    for (T entity : entities) {
      transformContext(entity);
      Point2D pos = entity.getPosition();

      Image entityTexture = entity.getTexture();
      if (entityTexture != null) {
        if (entity instanceof LayeredTile) {
          Image entityTexture2 = ((LayeredTile) entity).getBottom().getTexture();
          context.drawImage(
                  entityTexture2,
                  pos.getX(),
                  pos.getY(),
                  entity.getTexture().getWidth(),
                  entity.getTexture().getHeight()
          );
        }
        context.drawImage(
                entityTexture,
                pos.getX(),
                pos.getY(),
                entity.getTexture().getWidth(),
                entity.getTexture().getHeight()
        );
      }
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
