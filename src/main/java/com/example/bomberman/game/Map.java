package com.example.bomberman.game;

import com.example.bomberman.game.entities.Bomb;
import com.example.bomberman.game.entities.Bomberman;
import com.example.bomberman.game.entities.enemy.Ballom;
import com.example.bomberman.game.entities.enemy.Portal;
import com.example.bomberman.game.entities.item.BombItem;
import com.example.bomberman.game.entities.item.FlameItem;
import com.example.bomberman.game.entities.item.SpeedItem;
import com.example.bomberman.game.entities.tile.Brick;
import com.example.bomberman.game.entities.Flame;
import com.example.bomberman.game.entities.tile.LayeredTile;
import com.example.bomberman.gameEngine.Animation;
import com.example.bomberman.gameEngine.Entity;
import com.example.bomberman.gameEngine.Sprite;
import com.example.bomberman.game.entities.tile.Tile;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Map {

  private final String path;
  private int numOfMaps;
  private int numOfColumns;
  private int numOfRows;
  private final int TOTAL_TILES;
  public static final List<Tile> tiles = new ArrayList<>();
  public static final List<Entity> mobs = new ArrayList<>();
  public static final List<Bomberman> players = new ArrayList<>();
  public static final List<Bomb> bombs = new ArrayList<>();
  public static final List<Flame> flames = new ArrayList<>();
  private FileInputStream fileInputStream = null;
  private BufferedReader bufferedReader = null;

  public Map(String path) {
    this.path = path;
    readMapSize();
    TOTAL_TILES = numOfColumns * numOfRows;
  }

  public void readMap() {
    try {
      fileInputStream = new FileInputStream(path);
      bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
      int data;
      char tileType;

      //Bỏ dòng kích thước của map
      bufferedReader.readLine();

      data = bufferedReader.read();
      int x = 0, y = 0;

      //Cần cộng thêm numOfRows * 2 vì cuối mỗi dòng sẽ có 2 kí tự \r và \n
      //Cần - 2 vì kí tự thứ nhất đã đc đọc bên trên
      //Chỉ cần đọc đến kí tự \r ở dòng cuối của map
      for (int i = 1; i <= TOTAL_TILES + numOfRows * 2 - 2; ++i) {
        tileType = (char) data;

        //Nếu như đọc đc tileType và thỏa mãn
        if (tileType != '\n' && tileType != '\r') {
          Tile tile = null;

          switch (tileType) {
            case '#' -> {
              tile = new Tile(x, y, Sprite.wall, tileType);
              tiles.add(tile);
            }
            case '*' -> {
              //bên dưới brick sẽ là grass
              LayeredTile layeredTile = new LayeredTile(x, y,
                      new Tile(x, y, Sprite.grass, ' '),
                      new Brick(x, y, Sprite.brick, Animation.brick_broken));
              tiles.add(layeredTile);
            }
            case 'x' -> {
              //poral giấu sau brick và bên trên grass
              //TODO: create portal
              LayeredTile layeredTile = new LayeredTile(x, y,
                      new Tile(x, y, Sprite.grass, ' '),
                      new Portal(x, y, Sprite.portal),
                      new Brick(x, y, Sprite.brick, Animation.brick_broken));
              tiles.add(layeredTile);
            }
            case 'p' -> {
              Bomberman player = new Bomberman(x, y, Animation.bomberDown);
              players.add(player);
              tile = new Tile(x, y, Sprite.grass, ' ');
              tiles.add(tile);
            }
            case '1' -> {
              Ballom ballom = new Ballom(x, y, Animation.ballom_left);
              mobs.add(ballom);
              tile = new Tile(x, y, Sprite.grass, ' ');
              tiles.add(tile);
            }
            case '2' -> {
              //TODO:
              System.out.println("create oneal " + x + " " + y);
              tile = new Tile(x, y, Sprite.grass, ' ');
              tiles.add(tile);
            }
            case 'b' -> {
              LayeredTile layeredTile = new LayeredTile(x, y,
                      new Tile(x, y, Sprite.grass, ' '),
                      new BombItem(x, y, Sprite.powerUp_bombs),
                      new Brick(x, y, Sprite.brick, Animation.brick_broken));
              tiles.add(layeredTile);
            }
            case 'f' -> {
              LayeredTile layeredTile = new LayeredTile(x, y,
                      new Tile(x, y, Sprite.grass, ' '),
                      new FlameItem(x, y, Sprite.powerUp_flames),
                      new Brick(x, y, Sprite.brick, Animation.brick_broken));
              tiles.add(layeredTile);
            }
            case 's' -> {
              LayeredTile layeredTile = new LayeredTile(x, y,
                      new Tile(x, y, Sprite.grass, ' '),
                      new SpeedItem(x, y, Sprite.powerUp_speed),
                      new Brick(x, y, Sprite.brick, Animation.brick_broken));
              tiles.add(layeredTile);
            }
            case ' ' -> {
              tile = new Tile(x, y, Sprite.grass, ' ');
              tiles.add(tile);
            }
          }

          //Dịch sang x của tile tiếp theo
          x += Sprite.DEFAULT_SIZE * Sprite.SCALED;

          //Nếu như đến giới hạn level
          if (x >= numOfColumns * Sprite.DEFAULT_SIZE * Sprite.SCALED) {
            //Xuống dòng mới và làm lại
            x = 0;
            y += Sprite.DEFAULT_SIZE * Sprite.SCALED;
          }
        }

        data = bufferedReader.read();
      }
    } catch (FileNotFoundException ex) {
      Logger.getLogger(Map.class.getName()).log(Level.SEVERE, "FILE NOT FOUND!", ex);
    } catch (IOException ex) {
      Logger.getLogger(Map.class.getName()).log(Level.SEVERE, "FILE NOT COMPATIBLE!", ex);
    } catch (Exception ex) {
      Logger.getLogger(Map.class.getName()).log(Level.SEVERE, "ERROR WHILE READING MAP!", ex);
    } finally {
      try {
        System.out.println("mobs: " + mobs.size());
        System.out.println("tiles: " + tiles.size());
        bufferedReader.close();
        fileInputStream.close();
      } catch (NullPointerException | IOException ex) {
        Logger.getLogger(Map.class.getName()).log(Level.SEVERE, "CAN NOT READ FILE!", ex);
      }
    }
  }

  public void readMapSize() {
    try {
      File file = new File(path);
      Scanner sc = new Scanner(file);

      numOfMaps = sc.nextInt();
      numOfRows = sc.nextInt();
      numOfColumns = sc.nextInt();

    } catch (FileNotFoundException ex) {
      Logger.getLogger(Map.class.getName()).log(Level.SEVERE, "FILE NOT FOUND!", ex);
    } catch (InputMismatchException ex) {
      Logger.getLogger(Map.class.getName()).log(Level.SEVERE, "FILE NOT COMPATIBLE!", ex);
    }
  }

  public int getNumOfMaps() {
    return numOfMaps;
  }

  public int getNumOfColumns() {
    return numOfColumns;
  }

  public int getNumOfRows() {
    return numOfRows;
  }

  public int getTOTAL_TILES() {
    return TOTAL_TILES;
  }
}
