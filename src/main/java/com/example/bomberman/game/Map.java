package com.example.bomberman.game;

import com.example.bomberman.game.entities.Bomb;
import com.example.bomberman.game.entities.Bomberman;
import com.example.bomberman.game.entities.Camera;
import com.example.bomberman.game.entities.Flame;
import com.example.bomberman.game.entities.Portal;
import com.example.bomberman.game.entities.enemy.Ballom;
import com.example.bomberman.game.entities.enemy.Doll;
import com.example.bomberman.game.entities.enemy.Minvo;
import com.example.bomberman.game.entities.enemy.Oneal;
import com.example.bomberman.game.entities.item.BombItem;
import com.example.bomberman.game.entities.item.BombPass;
import com.example.bomberman.game.entities.item.FlameItem;
import com.example.bomberman.game.entities.item.SpeedItem;
import com.example.bomberman.game.entities.item.WallPass;
import com.example.bomberman.game.entities.tile.Brick;
import com.example.bomberman.game.entities.tile.LayeredTile;
import com.example.bomberman.game.entities.tile.Tile;
import com.example.bomberman.gameEngine.Animation;
import com.example.bomberman.gameEngine.Entity;
import com.example.bomberman.gameEngine.Sound;
import com.example.bomberman.gameEngine.Sprite;
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

  private String path;
  private int numOfMaps;
  private int numOfColumns;
  private int numOfRows;
  private int totalTiles;
  private boolean preValOfEmpty;

  private final int NUM_OF_MAPS = 4;
  private int currentMap = 1;

  public static boolean next = false;
  public static boolean reset = false;
  public static final List<Tile> tiles = new ArrayList<>();
  public static final List<Entity> mobs = new ArrayList<>();
  public static final List<Bomberman> players = new ArrayList<>();
  public static final List<Bomb> bombs = new ArrayList<>();
  public static final List<Flame> flames = new ArrayList<>();
  public static Camera camera = null;

  private FileInputStream fileInputStream = null;
  private BufferedReader bufferedReader = null;

  public Map(String path) {
    tiles.clear();
    mobs.clear();
    players.clear();
    bombs.clear();
    flames.clear();

    this.path = path;
    readMapSize();
    totalTiles = numOfColumns * numOfRows;

    readMap();
    camera = new Camera(0, 0, players.get(0));
    Sound.playLoop(Sound.theme);
  }

  public void update(double deltaTime) {
    if (reset) {
      if (currentMap > 0) currentMap--;
      resetMap();
    }
    if (next) {
      nextMap();
    }

    camera.update(deltaTime);
    if (mobs.isEmpty() && !preValOfEmpty) {
      Sound.playOnce(Sound.stage_cleared);
    }
    preValOfEmpty = mobs.isEmpty();
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
      for (int i = 1; i <= totalTiles + numOfRows * 2 - 2; ++i) {
        tileType = (char) data;

        //Nếu như đọc đc tileType và thỏa mãn
        if (tileType != '\n' && tileType != '\r') {
          Tile tile = null;

          switch (tileType) {
            case '#' -> {
              tile = new Tile(x, y, Sprite.wall, tileType);
            }
            case '*' -> {
              //bên dưới brick sẽ là grass
              tile = new LayeredTile(x, y,
                      new Tile(x, y, Sprite.grass, ' '),
                      new Brick(x, y, Sprite.brick, Animation.brick_broken));
            }
            case 'x' -> {
              //poral giấu sau brick và bên trên grass
              tile = new LayeredTile(x, y,
                      new Tile(x, y, Sprite.grass, ' '),
                      new Portal(x, y, Sprite.portal),
                      new Brick(x, y, Sprite.brick, Animation.brick_broken));
            }
            case 'p' -> {
              if (players.isEmpty()) {
                Bomberman player = new Bomberman(x, y, Animation.bomberDown);
                players.add(player);
              }
              tile = new Tile(x, y, Sprite.grass, ' ');
            }
            case '1' -> {
              Ballom ballom = new Ballom(x, y, Animation.ballom_left);
              mobs.add(ballom);
              tile = new Tile(x, y, Sprite.grass, ' ');
            }
            case '2' -> {
              Oneal oneal = new Oneal(x, y, Animation.oneal_right);
              mobs.add(oneal);
              tile = new Tile(x, y, Sprite.grass, ' ');
            }
            case '3' -> {
              Doll doll = new Doll(x, y, Animation.doll_right);
              mobs.add(doll);
              tile = new Tile(x, y, Sprite.grass, ' ');
            }
            case '4' -> {
              Minvo minvo = new Minvo(x, y, Animation.minvo_right);
              mobs.add(minvo);
              tile = new Tile(x, y, Sprite.grass, ' ');
            }
            case 'b' -> {
              tile = new LayeredTile(x, y,
                      new Tile(x, y, Sprite.grass, ' '),
                      new BombItem(x, y, Sprite.powerUp_bombs),
                      new Brick(x, y, Sprite.brick, Animation.brick_broken));
            }
            case 'f' -> {
              tile = new LayeredTile(x, y,
                      new Tile(x, y, Sprite.grass, ' '),
                      new FlameItem(x, y, Sprite.powerUp_flames),
                      new Brick(x, y, Sprite.brick, Animation.brick_broken));
            }
            case 's' -> {
              tile = new LayeredTile(x, y,
                      new Tile(x, y, Sprite.grass, ' '),
                      new SpeedItem(x, y, Sprite.powerUp_speed),
                      new Brick(x, y, Sprite.brick, Animation.brick_broken));
            }
            case 'm' -> {
              tile = new LayeredTile(x, y,
                      new Tile(x, y, Sprite.grass, ' '),
                      new BombPass(x, y, Sprite.powerUp_bomb_pass),
                      new Brick(x, y, Sprite.brick, Animation.brick_broken));
            }
            case 'w' -> {
              tile = new LayeredTile(x, y,
                      new Tile(x, y, Sprite.grass, ' '),
                      new WallPass(x, y, Sprite.powerUp_wall_pass),
                      new Brick(x, y, Sprite.brick, Animation.brick_broken));
            }
            case ' ' -> {
              tile = new Tile(x, y, Sprite.grass, ' ');
            }
          }
          if (tile != null) {
            tiles.add(tile);
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

  public void nextMap() {
    if (currentMap < NUM_OF_MAPS) {
      currentMap++;
      resetMap();
    }
  }

  public void resetMap() {
    tiles.clear();
    mobs.clear();
    bombs.clear();
    flames.clear();
    reset = false;
    next = false;

    this.path = "src/main/resources/com/example/bomberman/gameEngine/levels/Level" + currentMap
            + ".txt";
    readMapSize();
    totalTiles = numOfColumns * numOfRows;

    readMap();
    players.get(0).reset();
    camera = new Camera(0, 0, players.get(0));
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

  public int getTotalTiles() {
    return totalTiles;
  }
}
