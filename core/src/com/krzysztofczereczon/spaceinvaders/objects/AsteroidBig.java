package com.krzysztofczereczon.spaceinvaders.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.krzysztofczereczon.spaceinvaders.GameInfo;

public class AsteroidBig extends Sprite {

    public Body body;

    public AsteroidBig( Vector2 respawnPosition, Transform playerPos, World world) {
        super(new Texture("asteroidbig.png"));
        createBody(world);

        body.setTransform(respawnPosition.x, respawnPosition.y,0);

        float velocityX =  playerPos.getPosition().x - respawnPosition.x;
        float velocityY = -1 * playerPos.getPosition().y - respawnPosition.y;

        body.applyLinearImpulse(new Vector2(2*velocityX / Math.abs(velocityX), 2*velocityY / Math.abs(velocityY)), body.getWorldCenter(), true);

    }

    private void createBody(World world) {
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);

        body = world.createBody(bodyDef);
        body.setUserData("big");

        CircleShape shape = new CircleShape();
        shape.setRadius(getHeight() / 2 / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.restitution = 1;
        fixtureDef.density = 0;
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
    }

    public void update(SpriteBatch batch) {
        batch.draw(this, body.getPosition().x - getWidth() / 2 / GameInfo.PPM, body.getPosition().y - getHeight() / 2 / GameInfo.PPM, getWidth() / 2 / GameInfo.PPM, getHeight() / 2 / GameInfo.PPM, getWidth() / GameInfo.PPM, getHeight() / GameInfo.PPM, 1, 1, body.getAngle());
    }
}
