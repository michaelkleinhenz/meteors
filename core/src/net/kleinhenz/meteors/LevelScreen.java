package net.kleinhenz.meteors;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public class LevelScreen extends GameScreen {

	private static final float SCALEFACTOR_ALL = 0.25f;	
	private static final float FORCE = 150000f * SCALEFACTOR_ALL;
	
	public static final float TILESIZE = WIDTH/9;

	private World world = null;
	
	private List<HUDElement> lifes = new ArrayList<HUDElement>();
	private List<Meteor> meteors = new ArrayList<Meteor>();
	private Ship ship = null;
		
	public LevelScreen() {
		super();
		world = new World(new Vector2(0, 0), false);
	}
	
	@Override
	public void render(float delta) {
		world.step(delta, 6, 2);
		super.render(delta);
	}
	
	private void createWall(float x1, float y1, float x2, float y2) {
    	BodyDef bodyDef = new BodyDef();
    	bodyDef.type = BodyType.StaticBody;
    	bodyDef.position.set(x1, y1);
    	Body body = world.createBody(bodyDef);
    	EdgeShape edge = new EdgeShape();
    	edge.set(0, 0, x2, y2);    	
    	FixtureDef fixtureDef = new FixtureDef();
    	fixtureDef.shape = edge;
    	fixtureDef.density = 0.1f; 
    	fixtureDef.friction = 0.4f;
    	fixtureDef.restitution = 0.6f; // Make it bounce a little bit
    	body.createFixture(fixtureDef);
    	edge.dispose();
    }
	
	private boolean checkMeteors(Fixture fixture) {
		for (Meteor m : meteors)
			if (m.getBody().equals(fixture.getBody()))
				return true;
		return false;
	}

	private boolean checkShip(Fixture fixture) {
		return ship.getBody() != null && ship.getBody().equals(fixture.getBody());
	}

	private void removeLife() {
		if (!lifes.isEmpty()) {
			lifes.get(lifes.size()-1).remove();
			lifes.remove(lifes.size()-1);
		}
	}
	
	@Override
	public void show() {
		super.show();
		
        // add background
        addBackground(new Background("starfield.png", 30, 1f, true, null));
        addBackground(new Background("whirl.png", 40, 1f, true, null));
        addBackground(new Background("whirl2.png", 60, 1f, true, null));
        
        createWall(0f, 0f, 0f, HEIGHT);
        createWall(0f, 0f, WIDTH, 0f);
        createWall(0f, HEIGHT, WIDTH, 0f);
        createWall(WIDTH, HEIGHT, 0f, -HEIGHT);
        
		ship = new Ship(WIDTH / 2, HEIGHT / 2, 256f, 256f, world);
		getStage().addActor(ship);
		ship.reveal(0f, 1f);

		Meteor meteor1 = new Meteor(200, 200, new Texture(Gdx.files.internal("meteor1.png")), "Meteor1", world);
		getStage().addActor(meteor1);
		meteor1.reveal(0f, 1f);
		meteor1.boost(0, 0, 100000);
		meteors.add(meteor1);

		Meteor meteor2 = new Meteor(WIDTH - 200, HEIGHT - 200, new Texture(Gdx.files.internal("meteor2.png")), "Meteor2", world);
		getStage().addActor(meteor2);
		meteor2.reveal(0f, 1f);
		meteor2.boost(0, 0, 100000);
		meteors.add(meteor2);

		Meteor meteor3 = new Meteor(0, HEIGHT - 200, new Texture(Gdx.files.internal("meteor3.png")), "Meteor3", world);
		getStage().addActor(meteor3);
		meteor3.reveal(0f, 1f);
		meteor3.boost(0, 0, 100000);
		meteors.add(meteor3);
				
		// add HUD
		for (int i=1; i<6; i++) {
			HUDElement life = new HUDElement(i*50, 50, new Texture(Gdx.files.internal("heart.png")));
			lifes.add(life);
			getStage().addActor(life);			
		}
		
		// collision listener
		world.setContactListener(new ContactListener() {
			
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
			}
			
			@Override
			public void endContact(Contact contact) {
			}
			
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
			}

			@Override
			public void beginContact(Contact contact) {
				if ((checkMeteors(contact.getFixtureA()) && checkShip(contact.getFixtureB())) || 
						(checkMeteors(contact.getFixtureB()) && checkShip(contact.getFixtureA()))) {
					Gdx.app.log("meteors", "Collision");
					removeLife();					
				}
			}
		});
	}
}
