package com.example.bomberman.game.entities.enemy;

import com.example.bomberman.game.Map;
import java.util.LinkedList;
import java.util.Stack;
import javafx.geometry.Point2D;

public class AIHigh extends AI {

  Enemy enemy;
  AI aiLow;

  int[] path = new int[Map.getTotalTiles()];
  boolean[] hasFlame = new boolean[Map.getTotalTiles()];
  Stack<Integer> direction = new Stack<>();

  public AIHigh(Enemy enemy) {
    this.enemy = enemy;
    aiLow = new AILow();
  }

  public void update() {
    updateFlame();
    bfs();
  }

  @Override
  public int calculateDirection() {
    Point2D pos = enemy.getCenter();
    int currentPos = Map.convertToTileUnit(pos.getX(), pos.getY());

    if (!direction.isEmpty() && hasFlame[currentPos]) {
      return direction.pop();
    }

    int newDirection = aiLow.calculateDirection();
    if (newDirection == Enemy.LEFT && !hasFlame[currentPos - 1]) {
      return newDirection;
    }
    if (newDirection == Enemy.RIGHT && !hasFlame[currentPos + 1]) {
      return newDirection;
    }
    if (newDirection == Enemy.UP && !hasFlame[currentPos - Map.getNumOfColumns()]) {
      return newDirection;
    }
    if (newDirection == Enemy.DOWN && !hasFlame[currentPos + Map.getNumOfColumns()]) {
      return newDirection;
    }
    return calculateDirection();
  }

  public void bfs() {
    Point2D enemyCenter = enemy.getCenter();
    int currentPos = Map.convertToTileUnit(enemyCenter.getX(), enemyCenter.getY());
    int target = -1;
    boolean[] hasChecked = new boolean[Map.getTotalTiles()]; //những vị trí đã check
    LinkedList<Integer> position = new LinkedList<>(); //những vị trí check tiếp theo

    for (int i = 0; i < Map.getTotalTiles(); ++i) {
      hasChecked[i] = false;
    }

    hasChecked[currentPos] = true;
    position.add(currentPos);

    while (!position.isEmpty()) {
      int pos = position.pollFirst();
      int newPos;

      newPos = pos + 1;
      if (!hasChecked[newPos] && Map.tiles.get(newPos).canBePassedThrough()) {
        hasChecked[newPos] = true;
        path[newPos] = Enemy.RIGHT;
        position.add(newPos);

        //Nếu ô này ko có flame thì break -> lưu lại vị trí
        if (!hasFlame[newPos]) {
          target = newPos;
          break;
        }
      }

      newPos = pos - 1;
      if (!hasChecked[newPos] && Map.tiles.get(newPos).canBePassedThrough()) {
        hasChecked[newPos] = true;
        path[newPos] = Enemy.LEFT;
        position.add(newPos);

        //Nếu ô này ko có flame thì break -> lưu lại vị trí
        if (!hasFlame[newPos]) {
          target = newPos;
          break;
        }
      }

      newPos = pos + Map.getNumOfColumns();
      if (!hasChecked[newPos] && Map.tiles.get(newPos).canBePassedThrough()) {
        hasChecked[newPos] = true;
        path[newPos] = Enemy.DOWN;
        position.add(newPos);

        //Nếu ô này ko có flame thì break -> lưu lại vị trí
        if (!hasFlame[newPos]) {
          target = newPos;
          break;
        }
      }

      newPos = pos - Map.getNumOfColumns();
      if (!hasChecked[newPos] && Map.tiles.get(newPos).canBePassedThrough()) {
        hasChecked[newPos] = true;
        path[newPos] = Enemy.UP;
        position.add(newPos);

        //Nếu ô này ko có flame thì break -> lưu lại vị trí
        if (!hasFlame[newPos]) {
          target = newPos;
          break;
        }
      }
    }

    //Track lùi lại từ vị trí target đến vị trí hiện tại của quái
    direction.clear();
    if (target != -1) {
      int tracker = target;

      while (tracker != currentPos) {
        int type = path[tracker];
        direction.add(type);
        if (type == Enemy.RIGHT) {
          tracker--;
        } else if (type == Enemy.LEFT) {
          tracker++;
        } else if (type == Enemy.UP) {
          tracker += Map.getNumOfColumns();
        } else if (type == Enemy.DOWN) {
          tracker -= Map.getNumOfColumns();
        }
      }
      //end
    }
  }

  public void updateFlame() {

    for (int i = 0; i < Map.bombs.size(); i++) {
      Point2D pointPos = Map.bombs.get(i).getPosition();
      int pos = Map.convertToTileUnit((int) pointPos.getX(), (int) pointPos.getY());

      hasFlame[pos] = true;
      boolean stopUp = false;
      boolean stopDown = false;
      boolean stopLeft = false;
      boolean stopRight = false;

      for (int j = 1; j <= Map.bombs.get(i).getFlameSize(); j++) {
        if (!stopRight) {
          if (pos + j < Map.getTotalTiles() && Map.tiles.get(pos + j).canBePassedThrough()) {
            hasFlame[pos + j] = true;
          }
          stopRight = !Map.tiles.get(pos + j).canBePassedThrough();
        }
        if (!stopLeft) {
          if (pos - j > 0 && Map.tiles.get(pos - j).canBePassedThrough()) {
            hasFlame[pos - j] = true;
          }
          stopLeft = !Map.tiles.get(pos - j).canBePassedThrough();
        }
        if (!stopUp) {
          if (pos - j * Map.getNumOfColumns() > 0
                  && Map.tiles.get(pos - j * Map.getNumOfColumns()).canBePassedThrough()) {
            hasFlame[pos - j * Map.getNumOfColumns()] = true;
          }
          stopUp = !Map.tiles.get(pos - j * Map.getNumOfColumns()).canBePassedThrough();
        }
        if (!stopDown) {
          if (pos + j * Map.getNumOfColumns() < Map.getTotalTiles()
                  && Map.tiles.get(pos + j * Map.getNumOfColumns()).canBePassedThrough()) {
            hasFlame[pos + j * Map.getNumOfColumns()] = true;
          }
          stopDown = !Map.tiles.get(pos + j * Map.getNumOfColumns()).canBePassedThrough();
        }
      }
    }

    for (int i = 0; i < Map.flames.size(); i++) {
      if (Map.flames.get(i).isDead()) {
        Point2D pointPos = Map.flames.get(i).getPosition();
        int pos = Map.convertToTileUnit((int) pointPos.getX(), (int) pointPos.getY());
        hasFlame[pos] = false;
      }
    }
  }
}
