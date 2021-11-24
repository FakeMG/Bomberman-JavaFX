package com.example.bomberman.gameEngine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Tất cả sprite (hình ảnh game) được lưu trữ vào một ảnh duy nhất. Class này lưu dữ liệu của
 * SpriteSheet vào trong "_pixels".
 */
public class SpriteSheet {

  private String _path;
  public final int SIZE;
  public int[] _pixels;
  public BufferedImage image;

  public static SpriteSheet tiles = new SpriteSheet(
          "src/main/resources/com/example/bomberman/gameEngine/textures/classic.png", 256);

  public SpriteSheet(String path, int size) {
    _path = path;
    SIZE = size;
    _pixels = new int[SIZE * SIZE];
    load();
  }

  private void load() {
    try {
      File f = new File(_path);
      image = ImageIO.read(f);
      int w = image.getWidth();
      int h = image.getHeight();
      image.getRGB(0, 0, w, h, _pixels, 0, w);
    } catch (Exception e) {
      Logger.getLogger(SpriteSheet.class.getName()).log(Level.SEVERE, "FILE NOT FOUND!", e);
      System.exit(0);
    }
  }
}
