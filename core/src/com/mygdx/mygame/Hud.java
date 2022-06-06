package com.mygdx.mygame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.mygame.entity.Player;
import com.mygdx.mygame.terrain.Terrain;
import com.mygdx.mygame.textures.Tile;
import com.mygdx.mygame.textures.TileTexture;

import java.util.ArrayList;

import static com.mygdx.mygame.GameScreen.WORLD_WIDTH;

public class Hud implements Disposable {
  // player
  Player player;
  World world;
  // Scene2D.ui Stage and its own Viewport for HUD
  public Stage stage;
  private Viewport viewport;
  // texture
  TileTexture texture = new TileTexture();
  // Mario score/time Tracking Variables
  private Integer worldTimer;
  private boolean timeUp; // true when the world timer reaches 0
  private float timeCount;
  private static Integer score;
  private String boundingBox;

  // Scene2D widgets
  private Label countdownLabel;
  private static Label scoreLabel;
  private Label timeLabel;
  private Label levelLabel;
  private Label worldLabel;
  private Label marioLabel;

  public Hud(SpriteBatch sb, Player player) {
    this.player = player;
    // define our tracking variables
    worldTimer = 0;
    timeCount = 0;
    score = 0;
    boundingBox = player.boundingBox.toString();
    // setup the HUD viewport using a new camera seperate from our gamecam
    // define our stage using that viewport and our games spritebatch
    viewport = new FitViewport(WORLD_WIDTH, GameScreen.WORLD_HEIGHT, new OrthographicCamera());
    stage = new Stage(viewport, sb);

    // define a table used to organize our hud's labels
    Table table = new Table();
    // Top-Align table
    table.top();
    // make the table fill the entire stage
    table.setFillParent(true);

    // define our labels using the String, and a Label style consisting of a font and color
    countdownLabel =
        new Label(
            String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    scoreLabel = new Label(boundingBox, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    worldLabel = new Label(World.tileNameCurr, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    marioLabel = new Label("PLAYER", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    Image image = new Image(texture.button);
    // add our labels to our table, padding the top, and giving them all equal width with expandX
    table.add(marioLabel).expandX().padTop(WORLD_WIDTH - 100);
    table.add(worldLabel).expandX().padTop(WORLD_WIDTH - 100);
    table.add(timeLabel).expandX().padTop(WORLD_WIDTH - 100);
    // add a second row to our table
    table.row();
    table.add(scoreLabel).expandX();
    table.add(image).expandX();
    table.add(countdownLabel).expandX();

    // add our table to the stage
    stage.addActor(table);
  }

  public void update(float dt) {
    scoreLabel.setText(player.boundingBox.toString());
    timeCount += dt;
    if (timeCount >= 1) {
      if (worldTimer > -1) {
        worldTimer++;
      } else {
        timeUp = true;
      }
      countdownLabel.setText(String.format("%03d", worldTimer));
      timeCount = 0;
    }
  }

  public static void addScore(int value) {
    score += value;
    scoreLabel.setText(String.format("%06d", score));
  }

  @Override
  public void dispose() {
    stage.dispose();
  }

  public boolean isTimeUp() {
    return timeUp;
  }
}
