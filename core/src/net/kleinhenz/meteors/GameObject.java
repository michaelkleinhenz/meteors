package net.kleinhenz.meteors;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public class GameObject extends Actor {
			
    protected TextureRegion textureRegion = null;
	protected Body body = null;

	/**
	 * Creates a counter at stage position x/y with the given size and texture.
	 */
    public GameObject(float x, float y, float width, float height, TextureRegion textureRegion) {    	
    	setWidth(width);
    	setHeight(height);
		this.textureRegion = textureRegion;
		setX(x);
		setY(y);
		setTouchable(Touchable.disabled);
    }

    /**
     * Fades in the counter after waiting the given delay with the
     * given fade in duration.
     */
	public GameObject reveal(float delay, float fadeInDuration) {
		addAction(sequence(
				alpha(0),
				delay(delay),
				fadeIn(fadeInDuration)
		));
		return this;
	}

    /**
     * Returns the box2d body.
     */
    public Body getBody() {
    	return body;
    }
    
	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
}
