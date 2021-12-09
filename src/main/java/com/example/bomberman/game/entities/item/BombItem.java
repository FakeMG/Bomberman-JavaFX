package com.example.bomberman.game.entities.item;

import com.example.bomberman.game.Map;
import com.example.bomberman.game.entities.Bomberman;
import com.example.bomberman.gameEngine.Physic;
import com.example.bomberman.gameEngine.Sound;
import com.example.bomberman.gameEngine.Sprite;
import javafx.geometry.Point2D;

public class BombItem extends Item {

  public BombItem(double x, double y, Sprite sprite) {
    super(x, y, sprite);
  }

  public BombItem(Point2D position, Sprite sprite) {
    this(position.getX(), position.getY(), sprite);
  }

  @Override
  public void affect() {
    for (Bomberman player : Map.players) {
      if (Physic.checkCollision(collision, player.getCollision())) {
        player.setMaxBombs(player.getMaxBombs() + 1);
        Sound.playOnce(Sound.item_collected);
        isDead = true;
      }
    }
  }

  @Override
  public void update(double deltaTime) {
    affect();
  }
}
