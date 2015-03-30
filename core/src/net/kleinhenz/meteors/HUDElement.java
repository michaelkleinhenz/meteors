package net.kleinhenz.meteors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HUDElement extends GameObject {

	private static final float SCALEFACTOR = 0.1f;	
	
    public HUDElement(float x, float y, Texture texture) {    	
    	super(x, y, texture.getWidth()*SCALEFACTOR, texture.getHeight()*SCALEFACTOR, new TextureRegion(texture));    	
    }

    @Override
    public void act(float delta) {
    	super.act(delta);
    }

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}	
}
