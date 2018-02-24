package com.krzysztofczereczon.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.krzysztofczereczon.spaceinvaders.objects.*;

import java.util.ArrayList;
import java.util.List;

public class GameObjectManager {

    private World world;
    private GameBorder gameBorder;

    private float asteroidDelay;
    private int currentLevel = 0;

    //Objects
    public Player player;

    public List<AsteroidBig> asteroidsBig;
    public List<AsteroidSmall> asteroidsSmall;
    public List<AsteroidMedium> asteroidsMedium;

    public com.badlogic.gdx.utils.Array<Body> bodies;

    public List<Bullet> bullets;

    private GameManager gameManager;

    // respawn positions
    private Vector2 left = new Vector2(-700 / GameInfo.PPM, 100/GameInfo.PPM);
    private Vector2 right = new Vector2(700 / GameInfo.PPM, 100/GameInfo.PPM);
    private Vector2 up = new Vector2(100/GameInfo.PPM, 400 / GameInfo.PPM);
    private Vector2 down = new Vector2(100/GameInfo.PPM, -400 / GameInfo.PPM);

    public GameObjectManager(World world){
        this.world = world;
        player = new Player(world);

        asteroidsSmall = new ArrayList<AsteroidSmall>();
        asteroidsMedium = new ArrayList<AsteroidMedium>();
        asteroidsBig = new ArrayList<AsteroidBig>();
        bodies = new com.badlogic.gdx.utils.Array<Body>();
        gameBorder = new GameBorder(world);

        asteroidsBig.add(new AsteroidBig(up,player.body.getTransform(),world));
        bullets = new ArrayList<Bullet>();

    }

    public void setGameManager(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void update(SpriteBatch batch){
        player.update(batch, bullets);
        draw(batch);

        //incresing delay
        asteroidDelay += Gdx.graphics.getDeltaTime();

        if(gameManager != null){
            if(currentLevel != gameManager.gameLevel){
                int i = gameManager.gameLevel;
                if(asteroidDelay >= 1) {
                    if (asteroidsBig.size() <= i) {
                        addAsteroids();
                    } else {
                        currentLevel = gameManager.gameLevel;
                    }
                }
            }
        }
    }

    public void addMediumAsteroid(Transform biggerAsteroidTransform, int dir){
        asteroidsMedium.add(new AsteroidMedium(dir, new Vector2(biggerAsteroidTransform.getPosition().x, biggerAsteroidTransform.getPosition().y + 64/GameInfo.PPM), player.body.getTransform(), world));
    }

    public void destroyBodies(){
        if(bodies.size > 0) {
            for (Body body : bodies
                    ) {
                if (!world.isLocked()) {
                    if(body.getUserData() == "big"){
                        addMediumAsteroid(body.getTransform(), 1);
                        addMediumAsteroid(body.getTransform(), -1);
                    }

                    Array<JointEdge> joints = body.getJointList();
                    for (JointEdge joint: joints
                         ) {
                        world.destroyJoint(joint.joint);
                    }


                    world.destroyBody(body);
                    bodies.removeValue(body,false);
                }
            }
        }
    }

    private void draw(SpriteBatch batch){
        //drawing
        for (AsteroidBig asteroid: asteroidsBig
                ) {
            asteroid.update(batch);
        }

        for (AsteroidMedium asteroid: asteroidsMedium
                ) {
            asteroid.update(batch);
        }

        for (AsteroidSmall asteroid: asteroidsSmall
                ) {
            asteroid.update(batch);
        }

        for (Bullet bullet: bullets
                ) {
            bullet.update(batch);
        }

    }

    public void addAsteroids(){
        switch ((int) Math.floor((Math.random() * 4))) {
            case 0:
                asteroidsBig.add(new AsteroidBig( left, player.body.getTransform(), world));
                asteroidDelay = 0;
                break;
            case 1:
                asteroidsBig.add(new AsteroidBig(right, player.body.getTransform(), world));
                asteroidDelay = 0;
                break;
            case 2:
                asteroidsBig.add(new AsteroidBig( up, player.body.getTransform(), world));
                asteroidDelay = 0;
                break;
            case 3:
                asteroidsBig.add(new AsteroidBig( down, player.body.getTransform(), world));
                asteroidDelay = 0;
                break;
        }
    }
}
