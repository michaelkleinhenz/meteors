package net.kleinhenz.meteors;

import com.badlogic.gdx.Game;

public class MeteorsGame extends Game {

	@Override
	public void create() {
		// create view for level
		LevelScreen levelScreen = new LevelScreen();
		setScreen(levelScreen);
	}
}
