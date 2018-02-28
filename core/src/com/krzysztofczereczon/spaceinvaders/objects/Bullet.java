package com.krzysztofczereczon.spaceinvaders.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.krzysztofczereczon.spaceinvaders.GameInfo;

public class Bullet extends Sprite {

    private Body body;
    private World world;

    Bullet(World world, Vector2 position, Vector2 velocity, float angle){
        super(new Texture("laserRed.png"));
        this.world = world;
        createBody();
        body.setTransform(position.x, position.y, angle);

        Vector2 impulse = new Vector2(500 * -(float)Math.sin(body.getAngle() * Math.PI/180) / GameInfo.PPM + velocity.x, 500 * (float)Math.cos(body.getAngle() * Math.PI/180) / GameInfo.PPM + velocity.y);
        body.applyLinearImpulse(impulse, body.getWorldCenter(), true);

    }

    private void createBody(){
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX(), getY());

        body = world.createBody(bodyDef);
        body.setUserData(new BodyDataObject(this, "bullet", false));

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth()/2/ GameInfo.PPM, getHeight() / 2 / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef).setUserData("bullet");
        shape.dispose();
    }

    public void update(SpriteBatch batch){
        batch.draw(this,body.getPosition().x  - getWidth()/2 / GameInfo.PPM, body.getPosition().y  - getHeight() / 2 / GameInfo.PPM, getWidth() / 2 / GameInfo.PPM, getHeight()/2 / GameInfo.PPM, getWidth() / GameInfo.PPM, getHeight() / GameInfo.PPM,1,1,body.getAngle());
    }

}
