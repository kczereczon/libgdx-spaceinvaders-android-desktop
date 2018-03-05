package com.krzysztofczereczon.spaceinvaders;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.krzysztofczereczon.spaceinvaders.objects.*;


public class GameObjectManager {

    private World world;

    //Objects
    public Player player;

    private Array<AsteroidBig> asteroidsBig;
    private Array<AsteroidSmall> asteroidsSmall;
    private Array<AsteroidMedium> asteroidsMedium;
    private Array<Bullet> bullets;

    private GameManager gameManager;

    // respawn positions
    private Vector2 left = new Vector2(-700 / GameInfo.PPM, 100/GameInfo.PPM);
    private Vector2 right = new Vector2(700 / GameInfo.PPM, 100/GameInfo.PPM);
    private Vector2 up = new Vector2(100/GameInfo.PPM, 400 / GameInfo.PPM);
    private Vector2 down = new Vector2(100/GameInfo.PPM, -400 / GameInfo.PPM);

    public GameObjectManager(World world){
        this.world = world;


        asteroidsSmall = new Array<AsteroidSmall>();
        asteroidsMedium = new Array<AsteroidMedium>();
        asteroidsBig = new Array<AsteroidBig>();
        bullets = new Array<Bullet>();
    }

    public void setGameManager(GameManager gameManager){
        this.gameManager = gameManager;
        player = new Player(world, gameManager);
    }

    public void update(SpriteBatch batch){
        player.update(batch, bullets);
        border();
        draw(batch);
    }

    private void addMediumAsteroid(Transform biggerAsteroidTransform, Vector2 veocity){
        asteroidsMedium.add(new AsteroidMedium(new Vector2(biggerAsteroidTransform.getPosition().x, biggerAsteroidTransform.getPosition().y), veocity, world));
    }

    private void addSmallAsteroid(Transform biggerAsteroidTransform, Vector2 velocity){
        asteroidsSmall.add(new AsteroidSmall(new Vector2(biggerAsteroidTransform.getPosition().x, biggerAsteroidTransform.getPosition().y), velocity, world));
    }


    public void addBigAsteroid(){
        switch ((int) Math.floor((Math.random() * 4))) {
            case 0:
                asteroidsBig.add(new AsteroidBig( left, player.body.getTransform(), world));
                break;
            case 1:
                asteroidsBig.add(new AsteroidBig(right, player.body.getTransform(), world));
                break;
            case 2:
                asteroidsBig.add(new AsteroidBig( up, player.body.getTransform(), world));
                break;
            case 3:
                asteroidsBig.add(new AsteroidBig( down, player.body.getTransform(), world));
                break;
        }
    }

    private void border(){
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for(Body body: bodies){
            BodyDataObject bodyDataObject = (BodyDataObject) body.getUserData();
            if(!bodyDataObject.type.equals("bullet")){
                if(body.getPosition().x < -7f && body.getLinearVelocity().x < 0){
                    body.setTransform(7f, body.getPosition().y, body.getAngle());
                }
                if(body.getPosition().x > 7f && body.getLinearVelocity().x > 0){
                    body.setTransform(-7f, body.getPosition().y, body.getAngle());
                }
                if(body.getPosition().y < -4f && body.getLinearVelocity().y < 0){
                    body.setTransform(body.getPosition().x, 4f, body.getAngle());
                }
                if(body.getPosition().y > 4f && body.getLinearVelocity().y > 0){
                    body.setTransform(body.getPosition().x, -4f, body.getAngle());
                }
            }else{
                if(body.getPosition().x < -7f
                        || body.getPosition().x >7f
                        || body.getPosition().y < -4f
                        || body.getPosition().y > 4f){
                    body.setUserData(new BodyDataObject(bodyDataObject.object, "bullet", true));
                }
            }
        }
    }

    public void destroyBodies(){
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (Body body: bodies
             ) {
            BodyDataObject data = (BodyDataObject) body.getUserData();

            if(data!=null &&  data.isFlaggedForDelete){
                if(data.type.equals("big")){
                    addMediumAsteroid(body.getTransform(), body.getLinearVelocity());
                    gameManager.requiredToLevelUp--;
                    gameManager.score += 50;
                    asteroidsBig.removeValue((AsteroidBig) data.object, true);
                }
                if(data.type.equals("medium")){
                    addSmallAsteroid(body.getTransform(), body.getLinearVelocity());
                    gameManager.requiredToLevelUp--;
                    gameManager.score += 75;
                    asteroidsMedium.removeValue((AsteroidMedium) data.object, true);
                }
                if(data.type.equals("small")){
                    gameManager.requiredToLevelUp--;
                    gameManager.score += 100;
                    asteroidsSmall.removeValue((AsteroidSmall) data.object, true);
                }
                if(data.type.equals("bullet")){
                    bullets.removeValue((Bullet) data.object, true);
                }
                world.destroyBody(body);
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
}
