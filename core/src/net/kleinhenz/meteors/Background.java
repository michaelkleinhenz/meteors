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

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

/**
 * A full size background of a screen with an image stretched to fill it
 * with the option of scaling up and rotating the background image
 * seamlessly.
 */
public class Background extends Actor {

    private Texture texture = null;
    private TextureRegion tRegion = null;

    /**
     * Creates a background actor that takes an optional rotation duration and
     * an initial alpha.
     * 
     * @param imgPath Path to the image texture.
     * @param duration Duration of rotating 90 degrees. The rotation will be endless. Set to 0 to disable rotation.
     * @param alpha The initial alpha. The texture will be blended to that value on display.
     * @param scaleToFit true if the image should be scaled to fit the screen.
     */
    public Background(String imgPath, float duration, float alpha, boolean scaleToFit, ActorGestureListener listener) {
		texture = new Texture(Gdx.files.internal(imgPath));
		tRegion = new TextureRegion(texture);
		if (duration>0) {
			// scale and rotate
	    	float diagonal = (float)Math.sqrt(LevelScreen.WIDTH*LevelScreen.WIDTH+LevelScreen.WIDTH*LevelScreen.WIDTH);
	    	setSize(diagonal, diagonal);
	    	setPosition((LevelScreen.WIDTH-diagonal)/2, (LevelScreen.WIDTH-diagonal)/2);
	    	setOrigin(getWidth()/2, getHeight()/2);
			addAction(forever(rotateBy(90f, duration)));
		} else {
			// just display
			if (scaleToFit) {
				setSize(LevelScreen.WIDTH, LevelScreen.HEIGHT);
				setPosition(0, 0);
			} else {
				setSize(texture.getWidth(), texture.getHeight());
				setPosition((LevelScreen.WIDTH-texture.getWidth())/2, (LevelScreen.HEIGHT-texture.getHeight())/2);				
			}
		}
		addAction(alpha(alpha));
		
		// touch listener
		setTouchable(Touchable.enabled);
		if (listener!=null)
			addListener(listener);
    }
    
    /**
     * Creates a simple background from the given texture path, fullscreen,
     * non-transparent and centered.
     * 
     * @param imgPath Path to the image texture.
     */
    public Background(String imgPath) {
    	this(imgPath, 0, 1f, true, null);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		final Color c = getColor();
		batch.setColor(c.r, c.g, c.b, c.a * parentAlpha);
		batch.draw(tRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), 1f, 1f, getRotation());
	}
}
