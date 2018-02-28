package com.krzysztofczereczon.spaceinvaders.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.krzysztofczereczon.spaceinvaders.GameInfo;


public class Player extends Sprite {

    private World world;
    public Body body;
    private float speed = 10;
    private float maxSpeed = 3;

    private float reload = 0;

    public Player(World world){
        super(new Texture("player.png"));
        this.world = world;

        createBody();
    }

    public void move(Vector2 vector){
        float angle = (float)Math.atan2(vector.x, vector.y);
        body.setTransform(body.getPosition().x, body.getPosition().y, (float)(-angle * 180/Math.PI));

        float impulseX = ((body.getLinearVelocity().x <= maxSpeed && vector.x > 0) || (body.getLinearVelocity().x >= -maxSpeed && vector.x < 0)) ? vector.x * speed : 0;
        float impulseY = ((body.getLinearVelocity().y <= maxSpeed && vector.y > 0) || (body.getLinearVelocity().y >= -maxSpeed && vector.y < 0)) ? vector.y * speed : 0;

        body.applyForceToCenter(new Vector2(impulseX, impulseY), true);
    }

    private void drag(){
        Vector2 v = body.getLinearVelocity();
        float vSqrd = v.dst2(new Vector2(0,0));

        float fMag = 0.5f * vSqrd;
        Vector2 fd = new Vector2(-v.nor().x  * fMag, -v.nor().y  * fMag);
        body.applyForceToCenter(fd, true);
    }


    private void createBody(){
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);

        body = world.createBody(bodyDef);
        body.setUserData(new BodyDataObject(this, "player", false));

        CircleShape shape = new CircleShape();
        shape.setRadius(getHeight() / 2 / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.restitution = 1f;
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef).setUserData("player");
        shape.dispose();
    }


    public void update(SpriteBatch batch, Array<Bullet> bullets){
        batch.draw(this,body.getPosition().x  - getWidth()/2 / GameInfo.PPM, body.getPosition().y  - getHeight() / 2 / GameInfo.PPM, getWidth() / 2 / GameInfo.PPM, getHeight()/2 / GameInfo.PPM, getWidth() / GameInfo.PPM, getHeight() / GameInfo.PPM,1,1,body.getAngle());
        drag();
        if(reload >= 0.25f){
            bullets.add(new Bullet(world, body.getPosition(), body.getLinearVelocity(), body.getAngle()));
            reload = 0;
        }else{
            reload += Gdx.graphics.getDeltaTime();
        }

    }
}
