package com.example.bomberman.game;

import com.example.bomberman.gameEngine.Renderer;
import com.example.bomberman.gameEngine.Sprite;
import com.example.bomberman.gameEngine.Tile;
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
  private List<Tile> tiles = new ArrayList<>();
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
      //Cần - 2 vì kí tự thứ nhất đã đc đọc bên trên; chỉ cần đọc đến kí tự \r ở dòng cuối của map
      for (int i = 1; i <= TOTAL_TILES + numOfRows * 2 - 2; ++i) {
        tileType = (char) data;

        //Nếu như đọc đc tileType và thỏa mãn
        if (tileType != '\n' && tileType != '\r') {
          Tile tile = null;

          if (tileType == '#') {
            tile = new Tile(x, y, Sprite.wall.getTexture(), tileType);
            tiles.add(tile);
          } else if (tileType == '*') {
            //bên dưới brick sẽ là grass
            Brick brick = new Brick(x, y, Sprite.brick.getTexture());
            tiles.add(brick);
          } else if (tileType == 'x') {
            //poral giấu sau brick và bên trên grass
            Brick brick = new Brick(x, y, Sprite.brick.getTexture(), 'x');
            tiles.add(brick);
          } else {
            tile = new Tile(x, y, Sprite.grass.getTexture(), tileType);
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
        Renderer.addEntity(tiles);
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

  public List<Tile> getTiles() {
    return tiles;
  }
}
