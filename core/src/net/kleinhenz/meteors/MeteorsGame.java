package net.kleinhenz.meteors;

import com.badlogic.gdx.Game;

public class MeteorsGame extends Game {

	private LevelScreen levelScreen = null;

	@Override
	public void create() {
		// create view for level
		levelScreen = new LevelScreen();
		setScreen(levelScreen);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	public void updatePlayerInput(int player, float lS_X, float lS_Y,
								  float rS_X, float rS_Y, float l2, float r2) {
		if (levelScreen!=null)
			levelScreen.updatePlayerInput(player, lS_X, lS_Y, rS_X, rS_Y, l2, r2);
	}

	/*
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	*/
}
