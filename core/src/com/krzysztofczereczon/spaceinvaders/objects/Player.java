package com.krzysztofczereczon.spaceinvaders.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.krzysztofczereczon.spaceinvaders.Game;
import com.krzysztofczereczon.spaceinvaders.GameInfo;

import java.awt.geom.RectangularShape;

public class Player extends Sprite {

    World world;
    Body body;

    Vector3 touch;

    public Player(World world){
        super(new Texture("player.png"));
        this.world = world;
        createBody();
    }

    public void move(Vector3 vector){

        float screenX = vector.x - body.getPosition().x - getWidth()/2 / GameInfo.PPM;
        float screenY = vector.y - body.getPosition().y - getHeight()/2 / GameInfo.PPM;
        float angle = (float)Math.atan2(screenX, screenY);

        Vector2 impulse = new Vector2(2 * -(float)Math.sin(body.getAngle() * Math.PI/180) / GameInfo.PPM, 2 * (float)Math.cos(body.getAngle() * Math.PI/180) / GameInfo.PPM);

        body.setTransform(body.getPosition().x, body.getPosition().y, (float)(-angle * 180/Math.PI));
        body.applyLinearImpulse( impulse, body.getWorldCenter(), true);
    }


    public void createBody(){
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);

        body = world.createBody(bodyDef);
        body.setUserData("player");

        CircleShape shape = new CircleShape();
        shape.setRadius(getHeight() / 2 / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef);

        shape.dispose();
    }


    public void update(SpriteBatch batch){
        batch.draw(this,body.getPosition().x  - getWidth()/2 / GameInfo.PPM, body.getPosition().y  - getHeight() / 2 / GameInfo.PPM, getWidth() / 2 / GameInfo.PPM, getHeight()/2 / GameInfo.PPM, getWidth() / GameInfo.PPM, getHeight() / GameInfo.PPM,1,1,body.getAngle());
    }
}
