package com.example.bomberman.game.entities;

import com.example.bomberman.game.Map;
import com.example.bomberman.game.entities.tile.Brick;
import com.example.bomberman.game.entities.tile.LayeredTile;
import com.example.bomberman.game.entities.tile.Tile;
import com.example.bomberman.gameEngine.Animation;
import com.example.bomberman.gameEngine.Animator;
import com.example.bomberman.gameEngine.Entity;
import com.example.bomberman.gameEngine.Physic;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Flame extends Entity {

  private Animator animator;

  public Flame(double x, double y, Animation animation) {
    super(x, y, animation.getSprites().get(0));
    animator = new Animator(animation);
    animator.setPaused(false);
  }

  public Flame(Point2D position, Animation animation) {
    this(position.getX(), position.getY(), animation);
  }

  @Override
  public void update(double deltaTime) {
    try {

      if (animator.isEnded()) {
        isDead = true;
      }

      animatorController(deltaTime);
      updateCollision();
    } catch (NullPointerException ex) {
      Logger.getLogger(Flame.class.getName()).log(Level.SEVERE, "ANIMATOR IS NULL", ex);
      System.exit(-1);
    } catch (Exception ex) {
      Logger.getLogger(Flame.class.getName()).log(Level.SEVERE, "SOMETHING WENT WRONG!", ex);
      System.exit(-1);
    }
  }

  private void animatorController(double deltaTime) {
    animator.update(deltaTime);
  }

  @Override
  public Image getTexture() {
    return animator.getCurrentFrame();
  }

  private void updateCollision() {
    //TODO: possible lag SEVERE
    for (Tile tile : Map.tiles) {
      if (Physic.checkCollision(tile.getCollision(), collision) && !tile.canBePassedThrough()) {
        isDead = true;

        if (tile.getTileType() == '&') {
          Entity entity = ((LayeredTile) tile).getTop();
          if (entity instanceof Brick) {
            entity.setDying(true);
          }
          //TODO: destroy item
        }
      }
    }

    for (Entity entity : Map.mobs) {
      if (Physic.checkCollision(entity.getCollision(), collision)) {
        entity.setDead(true);
      }
    }

    for (Entity player : Map.players) {
      if (Physic.checkCollision(player.getCollision(), collision)) {
        player.setDead(true);
      }
    }

    for (Bomb bomb : Map.bombs) {
      if (Physic.checkCollision(bomb.getCollision(), collision)) {
        bomb.setDead(true);
      }
    }
  }
}
