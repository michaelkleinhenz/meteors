package net.kleinhenz.meteors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Ship extends GameObject {

	private static final float SCALEFACTOR_ALL = 0.25f;	
	private static final float FORCE = 150000f * SCALEFACTOR_ALL;
	private static final float SCALEFACTOR = 256f * SCALEFACTOR_ALL;
	
	ParticleEffect effect = null;
	SpriteBatch spriteBatch = null;
	Array<ParticleEmitter> emitters = null;

    public Ship(float x, float y, float width, float height, World world) {
    	super(x, y, width * SCALEFACTOR_ALL, height * SCALEFACTOR_ALL, new TextureRegion(new Texture(Gdx.files.internal("ship.png"))));
    	
    	// First we create a body definition
    	BodyDef bodyDef = new BodyDef();
    	// We set our body to dynamic, for something like ground which doesnt move we would set it to StaticBody
    	bodyDef.type = BodyType.DynamicBody;
    	// Set our body's starting position in the world
    	bodyDef.position.set(x, y);
    	// Create our body in the world using our body definition
    	body = world.createBody(bodyDef);
    	// Create a fixture definition to apply our shape to
    	FixtureDef fixtureDef = new FixtureDef();
    	fixtureDef.density = 0.1f; 
    	fixtureDef.friction = 0.4f;
    	fixtureDef.restitution = 0.6f; // Make it bounce a little bit
    	// Load shapes from file
    	BodyEditorLoader bodyShapes = new BodyEditorLoader(Gdx.files.internal("shapes.json"));
		bodyShapes.attachFixture(body, "Ship", fixtureDef, SCALEFACTOR);
		
		// Particles
		spriteBatch = new SpriteBatch();
		effect = new ParticleEffect();
		effect.load(Gdx.files.internal("particle.p"), Gdx.files.internal(""));

		/*
		// Use box2d particles
		ParticleEmitterBox2D boxPE = new ParticleEmitterBox2D(world, effect.getEmitters().get(0));
		effect.getEmitters().clear();
		effect.getEmitters().add(boxPE);
		*/
		
		effect.setDuration(0);
	}

    public void boost(float x, float y, float forceInit, boolean impulse, boolean particles) {
    	float angel = body.getAngle();
		float forceX = (float) Math.sin(angel) * forceInit;
		float forceY = (float) Math.cos(angel) * forceInit;
		float localX = x * SCALEFACTOR;
		float localY = y * SCALEFACTOR;
		Vector2 force = new Vector2(forceX, -forceY);
		Vector2 localPoint = new Vector2(localX, localY);
		if (impulse) {
			body.applyLinearImpulse(force, body.getWorldPoint(localPoint), true);
		} else {
			body.applyForce(force, body.getWorldPoint(localPoint), true);
		}
		if (particles) {
			effect.start();
			effect.setDuration(500);
		}
    }		
    
    @Override
    public void act(float delta) {
    	super.act(delta);
    	effect.update(delta);

		Vector2 effectPosition = body.getWorldPoint(new Vector2(getWidth() / 2, getHeight()));
		effect.setPosition(effectPosition.x, effectPosition.y);
		
    	for (ParticleEmitter particleEmitter : effect.getEmitters()) { //get the list of emitters - things that emit particles
    		particleEmitter.getAngle().setLow((float)Math.toDegrees(body.getAngle())+90); //low is the minimum rotation
    		particleEmitter.getAngle().setHigh((float)Math.toDegrees(body.getAngle())+90); //high is the max rotation
        }

    	setPosition(body.getPosition().x, body.getPosition().y);
    	setRotation((float)Math.toDegrees(body.getAngle()));
    	if(Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) {
    		boost(1f, 0f, -FORCE/300, true, false);
    		boost(0f, 1f, FORCE/300, true, false);
    	}
    	if(Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) {
    		boost(0f, 0f, -FORCE/300, true, false);
    		boost(1f, 1f, FORCE/300, true, false);	
    	}
    	if(Gdx.input.isKeyPressed(Keys.DPAD_UP)) {
    		boost(0.5f, 1f, FORCE, false, true);
    	}
    	if(Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) {
    		boost(0.5f, 0f, -FORCE, false, false);
    	}
    }
    
	@Override
	public void draw(Batch spriteBatch, float parentAlpha) {
		super.draw(spriteBatch, parentAlpha);

		float delta = Gdx.graphics.getDeltaTime();
		effect.draw(spriteBatch, delta);
	}	
}
