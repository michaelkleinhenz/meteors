package net.kleinhenz.meteors;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Meteor extends GameObject {

	private static final float SCALEFACTOR = 0.5f;	
	
    public Meteor(float x, float y, Texture texture, String shapeName, World world) {    	
    	super(x, y, texture.getWidth()*SCALEFACTOR, texture.getHeight()*SCALEFACTOR, new TextureRegion(texture));
    	
    	BodyDef bodyDef = new BodyDef();
    	bodyDef.type = BodyType.DynamicBody;
    	bodyDef.position.set(x, y);
    	body = world.createBody(bodyDef);
    	FixtureDef fixtureDef = new FixtureDef();
    	fixtureDef.density = 0.1f; 
    	fixtureDef.friction = 0.4f;
    	fixtureDef.restitution = 0.6f;

    	BodyEditorLoader bodyShapes = new BodyEditorLoader(Gdx.files.internal("data/shapes.json"));
		bodyShapes.attachFixture(body, shapeName, fixtureDef, texture.getWidth()*SCALEFACTOR);
    }
    
    public void boost(float x, float y, float forceInit) {
    	float angel = body.getAngle();
		float forceX = (float) Math.sin(angel) * forceInit;
		float forceY = (float) Math.cos(angel) * forceInit;
		float localX = x * SCALEFACTOR;
		float localY = y * SCALEFACTOR;
		Vector2 force = new Vector2(forceX, -forceY);
		Vector2 localPoint = new Vector2(localX, localY);
		body.applyLinearImpulse(force, body.getWorldPoint(localPoint));
    }		

    @Override
    public void act(float delta) {
    	super.act(delta);
    	setPosition(body.getPosition().x, body.getPosition().y);
    	setRotation((float)Math.toDegrees(body.getAngle()));
    }

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}	
}
