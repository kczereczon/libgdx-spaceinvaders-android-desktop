package com.krzysztofczereczon.spaceinvaders.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.krzysztofczereczon.spaceinvaders.Game;
import com.krzysztofczereczon.spaceinvaders.GameInfo;

public class AsteroidMedium extends Sprite{

    private Body body;

    public AsteroidMedium(int dir, Vector2 respawnPosition, Transform playerPos, World world) {
        super(new Texture("asteroidmedium.png"));
        createBody(world);
        body.setTransform(respawnPosition.x + (dir * 128/ GameInfo.PPM), respawnPosition.y,0);
        float random = (float)(Math.random()*2) -1;

        float velocityX = dir * (playerPos.getPosition().x - respawnPosition.x);
        float velocityY =  playerPos.getPosition().y - respawnPosition.y;

        body.setLinearVelocity(new Vector2(2.5f * velocityX / Math.abs(velocityX), 2.5f * velocityY / Math.abs(velocityY)));
    }

    private void createBody(World world) {
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);

        body = world.createBody(bodyDef);
        body.setUserData("medium");

        CircleShape shape = new CircleShape();
        shape.setRadius(getHeight() / 2 / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.restitution = 1;
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
    }

    public void update(SpriteBatch batch) {
        if(body != null)
        batch.draw(this, body.getPosition().x - getWidth() / 2 / GameInfo.PPM, body.getPosition().y - getHeight() / 2 / GameInfo.PPM, getWidth() / 2 / GameInfo.PPM, getHeight() / 2 / GameInfo.PPM, getWidth() / GameInfo.PPM, getHeight() / GameInfo.PPM, 1, 1, body.getAngle());
    }
}
