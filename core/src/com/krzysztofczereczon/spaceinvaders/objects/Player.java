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

        float screenX = vector.x - body.getPosition().x - getWidth()/2 / GameInfo.PPM;
        float screenY = vector.y - body.getPosition().y - getHeight()/2 / GameInfo.PPM;
        float angle = (float)Math.atan2(screenX, screenY);

        Vector2 impulse = new Vector2(2 * -(float)Math.sin(body.getAngle() * Math.PI/180) / GameInfo.PPM, 2 * (float)Math.cos(body.getAngle() * Math.PI/180) / GameInfo.PPM);
        Vector2 impulseFaster = new Vector2(12 * -(float)Math.sin(body.getAngle() * Math.PI/180) / GameInfo.PPM, 12 * (float)Math.cos(body.getAngle() * Math.PI/180) / GameInfo.PPM);

        body.setTransform(body.getPosition().x, body.getPosition().y, (float)(-angle * 180/Math.PI));

        if(body.getLinearVelocity().x / Math.abs(body.getLinearVelocity().x) != impulse.x / Math.abs(impulse.x) || body.getLinearVelocity().y / Math.abs(body.getLinearVelocity().y) != impulse.y / Math.abs(impulse.y)) {
            body.applyLinearImpulse(impulseFaster, body.getWorldCenter(), true);
        }else {
            body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
        }
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

        if(reload >= 1){
            bullets.add(new Bullet(world, body.getPosition(), body.getAngle()));
            reload = 0;
        }else{
            reload += Gdx.graphics.getDeltaTime();

        }

        for (Bullet bullet:bullets
             ) {
            bullet.update(batch);
        }
    }
}
