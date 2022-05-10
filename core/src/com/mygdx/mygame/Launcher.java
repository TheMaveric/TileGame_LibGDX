package com.mygdx.mygame;
import com.badlogic.gdx.Game;

public class Launcher extends Game {
	GameScreen gameScreen;
	MenuScreen menuScreen;
	@Override
	public void create() {
		gameScreen = new GameScreen();
		//menuScreen = new MenuScreen();
		setScreen(gameScreen);
		//setScreen(menuScreen);
	}

	@Override
	public void dispose() {
		gameScreen.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		gameScreen.resize(width, height);
	}
}
