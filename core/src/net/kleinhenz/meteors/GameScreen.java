/*
 * Board Game Engine
 * Copyright (c) 2013 Michael Kleinhenz (michael@kleinhenz.net)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the 
 * Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included 
 * in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS 
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
 * DEALINGS IN THE SOFTWARE.
 */
package net.kleinhenz.meteors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * This class provides a basic framework for board game screens.
 * It creates a scene, adds a camera and sets reasonable defaults
 * for resolution and clipping.
 */
public abstract class GameScreen implements Screen {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;	
	
	protected Stage stage = null;

	public GameScreen() {
		super();

		OrthographicCamera cam = new OrthographicCamera(WIDTH, HEIGHT);
		stage = new Stage(new StretchViewport(WIDTH, HEIGHT, cam));
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
	}

	/**
	 * Adds a background to the screen.
	 * 
	 * @param background
	 */
	public void addBackground(Background background) {
		stage.addActor(background);
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
    }

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void hide() {
		// NOP
	}

	@Override
	public void pause() {
		// NOP
	}

	@Override
	public void resume() {
		// NOP
	}

	@Override
	public void dispose() {
        stage.dispose();
	}

	public Stage getStage() {
		return stage;
	}
}
