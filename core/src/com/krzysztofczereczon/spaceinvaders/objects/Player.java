package com.krzysztofczereczon.spaceinvaders.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.krzysztofczereczon.spaceinvaders.GameInfo;

import java.util.List;

public class Player extends Sprite {

    private World world;
    public Body body;


    private float reload = 0;

    public Player(World world){
        super(new Texture("player.png"));
        this.world = world;

        createBody();
    }

    public void move(Vector3 vector){
        float angle = (float)Math.atan2(vector.x, vector.y);
        body.setTransform(body.getPosition().x, body.getPosition().y, (float)(-angle * 180/Math.PI));

        float joyX = -(body.getPosition().x - vector.x);
        float joyY = -(body.getPosition().y - vector.y);

        body.applyForce(new Vector2(((body.getLinearVelocity().x >= 0 && joyX <= 0) || (body.getLinearVelocity().x <= 0 && joyX >= 0)) ? 5 * joyX : joyX, ((body.getLinearVelocity().y >= 0 && joyY <= 0) || (body.getLinearVelocity().y <= 0 && joyY >= 0)) ? 5 * joyY : joyY), body.getPosition(), true);
    }


    private void createBody(){
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);

        body = world.createBody(bodyDef);
        body.setUserData("player");

        CircleShape shape = new CircleShape();
        shape.setRadius(getHeight() / 2 / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.restitution = 1f;
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef).setUserData("player");
        shape.dispose();
    }


    public void update(SpriteBatch batch, List<Bullet> bullets){
        batch.draw(this,body.getPosition().x  - getWidth()/2 / GameInfo.PPM, body.getPosition().y  - getHeight() / 2 / GameInfo.PPM, getWidth() / 2 / GameInfo.PPM, getHeight()/2 / GameInfo.PPM, getWidth() / GameInfo.PPM, getHeight() / GameInfo.PPM,1,1,body.getAngle());

        if(reload >= 0.1f){
            bullets.add(new Bullet(world, body.getPosition(), body.getAngle()));
            reload = 0;
        }else{
            reload += Gdx.graphics.getDeltaTime();
        }

    }
}
