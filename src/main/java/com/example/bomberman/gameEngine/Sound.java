package com.example.bomberman.gameEngine;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {

  public static Media theme = new Media(new File(
          "src/main/resources/com/example/bomberman/gameEngine/sound/03_StageTheme.mp3").toURI()
          .toString());
  public static Media bomb_placed = new Media(new File(
          "src/main/resources/com/example/bomberman/gameEngine/sound/bomb_placed.wav").toURI()
          .toString());
  public static Media bomb_exploded = new Media(new File(
          "src/main/resources/com/example/bomberman/gameEngine/sound/bomb_exploded.wav").toURI()
          .toString());
  public static Media walk_horizontal = new Media(new File(
          "src/main/resources/com/example/bomberman/gameEngine/sound/walk_horizontal.wav").toURI()
          .toString());
  public static Media walk_vertical = new Media(new File(
          "src/main/resources/com/example/bomberman/gameEngine/sound/walk_vertical.wav").toURI()
          .toString());
  public static Media die = new Media(new File(
          "src/main/resources/com/example/bomberman/gameEngine/sound/die.wav").toURI().toString());
  public static Media stage_cleared = new Media(new File(
          "src/main/resources/com/example/bomberman/gameEngine/sound/stage_cleared.wav").toURI()
          .toString());
  public static Media item_collected = new Media(new File(
          "src/main/resources/com/example/bomberman/gameEngine/sound/item_collected.wav").toURI()
          .toString());

  public static MediaPlayer mediaPlayer;

  public static void playLoop(Media media) {
    mediaPlayer = new MediaPlayer(media);
    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    mediaPlayer.play();
  }

  public static void playOnce(Media media) {
    mediaPlayer = new MediaPlayer(media);
    mediaPlayer.play();
  }

  public static void stop() {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
    }
  }

//  public static void play(String sound) {
//    new Thread(new Runnable() {
//      public void run() {
//        try {
//          File file = new File(
//                  "src/main/resources/com/example/bomberman/gameEngine/sound/" + sound + ".wav");
//          AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
//          Clip clip = AudioSystem.getClip();
//          clip.open(inputStream);
//          clip.start();
//        } catch (Exception e) {
//          System.err.println(e.getMessage());
//        }
//      }
//    }).start();
//  }
//
//  public static void stop(String sound) {
//    new Thread(new Runnable() {
//      public void run() {
//        try {
//          File file = new File(
//                  "src/main/resources/com/example/bomberman/gameEngine/sound/" + sound + ".wav");
//          AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
//          Clip clip = AudioSystem.getClip();
//          clip.open(inputStream);
//          clip.stop();
//        } catch (Exception e) {
//          System.err.println(e.getMessage());
//        }
//      }
//    }).start();
//  }
}
