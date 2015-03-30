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
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public class GameObject extends Actor {
			
    protected TextureRegion textureRegion = null;    	
	protected float tileRotation = 0;

	protected Body body = null;

	/**
	 * Creates a counter at stage position x/y with the given size and texture.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param textureRegion
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
	 * Creates a counter at stage position x/y with the given size and texture.
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param texturePath
     */
    public GameObject(float x, float y, float width, float height, String texturePath) { 	
    	this(x, y, width, height, new TextureRegion(new Texture(Gdx.files.internal(texturePath))));
    }

    /**
     * Default constructor.
     */
    public GameObject() {
    	super();
	}

	/**
     * Adds a rotation function to the counter. The counter will rotate on tapping by the given degrees in
     * the given animation time and the given interpolation.
     * 
     * @param degrees
     * @param animationDuration
     * @param interpolation
     * @return Returns itself to allow stacking.
     */
    public GameObject addRotationOnTapAspect(final float degrees, final float animationDuration, final Interpolation interpolation) {
		setTouchable(Touchable.enabled);
		addListener(new ActorGestureListener() {
			@Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
				rotate(degrees, animationDuration, interpolation);
			}
		});
		return this;
    }

    /**
     * Fades in the counter after waiting the given delay with the
     * given fade in duration.
     * 
     * @param delay
     * @param fadeInDuration
     * @return Returns itself to allow stacking.
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
	 * Rotates the counter.
	 * 
	 * @param degrees
	 * @param animationDuration
	 * @param interpolation
     * @return Returns itself to allow stacking.
	 */
    public GameObject rotate(float degrees, float animationDuration, Interpolation interpolation) {
    	setOrigin(getWidth()/2, getHeight()/2);
		addAction(rotateBy(degrees, animationDuration, interpolation));
		return this;
    }

    /**
	 * Rotates the counter.
     * 
     * @param degrees
     * @return Returns itself to allow stacking.
     */
    public GameObject rotate(int degrees) {
    	rotate(degrees, 0.3f, Interpolation.exp10);
		return this;
    }

    /**
     * Returns true if the given fixture is part of this
     * body.
     * 
     * @param fixture
     * @return true if fixture is this body, false otherwise.
     */
    public boolean isBody(Fixture fixture) {
    	return (body!=null && body.getFixtureList()!=null && body.getFixtureList().contains(fixture));
    }

    /**
     * Returns the box2d body.
     * 
     * @return
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
		final Color c = getColor();
		batch.setColor(c.r, c.g, c.b, c.a * parentAlpha);
		batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
}
