package com.example.bomberman.game.entities.tile;

import com.example.bomberman.gameEngine.Entity;
import java.util.List;
import java.util.Stack;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class LayeredTile extends Tile {
  private Stack<Entity> entityList = new Stack<>();

  public LayeredTile(double x, double y, Entity ... entities) {
    super(x, y, null, '&');
    entityList.addAll(List.of(entities));
    sprite = entityList.peek().getSprite();
  }

  public LayeredTile(Point2D position, Entity ... entities) {
    this(position.getX(), position.getY(), entities);
  }

  @Override
  public void update(double deltaTime) {
    entityList.peek().update(deltaTime);
    if (entityList.peek().isDead()) {
      entityList.pop();
    }
  }

  @Override
  public Image getTexture() {
    return entityList.peek().getTexture();
  }

  @Override
  public boolean canBePassedThrough() {
    return getTop().canBePassedThrough();
  }

  @Override
  public Rectangle getCollision() {
    return entityList.peek().getCollision();
  }

  public Entity getTop() {
    return entityList.peek();
  }

  public Entity getBottom() {
    return entityList.firstElement();
  }
}
