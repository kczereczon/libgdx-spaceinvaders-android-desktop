package com.krzysztofczereczon.spaceinvaders.objects;

import com.badlogic.gdx.physics.box2d.*;
import com.krzysztofczereczon.spaceinvaders.Game;
import com.krzysztofczereczon.spaceinvaders.GameInfo;

public class GameBorder {

    private Body body;

    public GameBorder(World world){
        createTopBody(world);
        createBottomBody(world);
        createLeftBody(world);
        createRightBody(world);
    }

    private void createTopBody(World world) {
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, (float)GameInfo.HEIGHT / 2 / GameInfo.PPM);
        System.out.println(GameInfo.HEIGHT / 2 / GameInfo.PPM);

        body = world.createBody(bodyDef);
        body.setUserData("top");

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(GameInfo.WIDTH/GameInfo.PPM, 1/GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;


        body.createFixture(fixtureDef);
        shape.dispose();
    }

    private void createBottomBody(World world) {
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, -(float)GameInfo.HEIGHT / 2 / GameInfo.PPM );

        body = world.createBody(bodyDef);
        body.setUserData("bottom");

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(GameInfo.WIDTH/GameInfo.PPM, 1/GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.restitution = 1;
        fixtureDef.shape = shape;


        body.createFixture(fixtureDef);
        shape.dispose();
    }

    private void createLeftBody(World world) {
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((float)GameInfo.WIDTH / 2 / GameInfo.PPM, 0);

        body = world.createBody(bodyDef);
        body.setUserData("left");

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1/GameInfo.PPM, GameInfo.HEIGHT/GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.restitution = 1;
        fixtureDef.shape = shape;


        body.createFixture(fixtureDef);
        shape.dispose();
    }

    private void createRightBody(World world) {
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(-(float)GameInfo.WIDTH / 2 / GameInfo.PPM, 0);

        body = world.createBody(bodyDef);
        body.setUserData("right");

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1/GameInfo.PPM, GameInfo.HEIGHT/GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.restitution = 1;
        fixtureDef.shape = shape;


        body.createFixture(fixtureDef);
        shape.dispose();
    }
}
