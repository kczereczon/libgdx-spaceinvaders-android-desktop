package com.krzysztofczereczon.spaceinvaders.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.krzysztofczereczon.spaceinvaders.Game;
import com.krzysztofczereczon.spaceinvaders.GameInfo;
import com.krzysztofczereczon.spaceinvaders.GameManager;
import com.krzysztofczereczon.spaceinvaders.GameObjectManager;
import com.krzysztofczereczon.spaceinvaders.analog.ScreenJoystick;
import com.krzysztofczereczon.spaceinvaders.objects.BodyDataObject;

public class MainGameScene implements com.badlogic.gdx.Screen {

    private Game game;
    private Camera camera;
    private World world;

    private GameObjectManager gameObjectManager;
    private GameManager gameManager;
    private ScreenJoystick screenJoystick;

    private float startMouseX, startMouseY;

    private Box2DDebugRenderer debugRenderer;

    public MainGameScene(final Game game) {
        this.game = game;
        camera = new OrthographicCamera(GameInfo.WIDTH / GameInfo.PPM, GameInfo.HEIGHT / GameInfo.PPM);
        world = new World(new Vector2(0, 0), true);
        world.step(1/60f, 6, 6);
        debugRenderer = new Box2DDebugRenderer();


        gameObjectManager = new GameObjectManager(world);
        gameManager = new GameManager(gameObjectManager);
        gameObjectManager.setGameManager(gameManager);
        screenJoystick = new ScreenJoystick();

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                startMouseX = screenX;
                startMouseY = screenY;
                gameManager.gameHaveSarted = true;
                System.out.println(screenX);
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        });
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                //destroing big asteroids
                if((contact.getFixtureB().getUserData() == "bullet" && contact.getFixtureA().getUserData() == "big") || (contact.getFixtureA().getUserData() == "bullet" && contact.getFixtureB().getUserData() == "big")){
                    BodyDataObject objectB = (BodyDataObject) contact.getFixtureB().getBody().getUserData();
                    contact.getFixtureB().getBody().setUserData(new BodyDataObject(objectB.object, objectB.type,true));

                    BodyDataObject objectA = (BodyDataObject) contact.getFixtureA().getBody().getUserData();
                    contact.getFixtureA().getBody().setUserData(new BodyDataObject(objectA.object, objectA.type,true));

                }
                //destroing medium
                if((contact.getFixtureB().getUserData() == "bullet" && contact.getFixtureA().getUserData() == "medium") || (contact.getFixtureA().getUserData() == "bullet" && contact.getFixtureB().getUserData() == "medium")){
                    BodyDataObject objectB = (BodyDataObject) contact.getFixtureB().getBody().getUserData();
                    contact.getFixtureB().getBody().setUserData(new BodyDataObject(objectB.object, objectB.type,true));

                    BodyDataObject objectA = (BodyDataObject) contact.getFixtureA().getBody().getUserData();
                    contact.getFixtureA().getBody().setUserData(new BodyDataObject(objectA.object, objectA.type,true));

                }
                //destroing small
                if((contact.getFixtureB().getUserData() == "bullet" && contact.getFixtureA().getUserData() == "small") || (contact.getFixtureA().getUserData() == "bullet" && contact.getFixtureB().getUserData() == "small")){
                    BodyDataObject objectB = (BodyDataObject) contact.getFixtureB().getBody().getUserData();
                    contact.getFixtureB().getBody().setUserData(new BodyDataObject(objectB.object, objectB.type,true));

                    BodyDataObject objectA = (BodyDataObject) contact.getFixtureA().getBody().getUserData();
                    contact.getFixtureA().getBody().setUserData(new BodyDataObject(objectA.object, objectA.type,true));

                }

                if(contact.getFixtureB().getUserData() == "bullet" && (contact.getFixtureA().getUserData() == "top" || contact.getFixtureA().getUserData() == "bottom" || contact.getFixtureA().getUserData() == "left" || contact.getFixtureA().getUserData() == "right")){
                    BodyDataObject dataObject = (BodyDataObject) contact.getFixtureB().getBody().getUserData();
                    contact.getFixtureB().getBody().setUserData(new BodyDataObject(dataObject.object, dataObject.type,true));
                }
            }

            @Override
            public void endContact(Contact contact) {

            }
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                if(contact.getFixtureB().getUserData() == "bullet" && contact.getFixtureA().getUserData() == "player"){
                    contact.setEnabled(false);
                }

                if((contact.getFixtureB().getUserData() == "big" || contact.getFixtureB().getUserData() == "medium" || contact.getFixtureB().getUserData() == "small") && contact.getFixtureA().getUserData() == "top" && contact.getFixtureB().getBody().getLinearVelocity().y < 0){
                    contact.setEnabled(false);
                }

                if((contact.getFixtureB().getUserData() == "big" || contact.getFixtureB().getUserData() == "medium" || contact.getFixtureB().getUserData() == "small") && contact.getFixtureA().getUserData() == "bottom" && contact.getFixtureB().getBody().getLinearVelocity().y > 0){
                    contact.setEnabled(false);
                }
                if((contact.getFixtureB().getUserData() == "big" || contact.getFixtureB().getUserData() == "medium" || contact.getFixtureB().getUserData() == "small") && contact.getFixtureA().getUserData() == "right" && contact.getFixtureB().getBody().getLinearVelocity().x > 0){
                    contact.setEnabled(false);
                }

                if((contact.getFixtureB().getUserData() == "big" || contact.getFixtureB().getUserData() == "medium" || contact.getFixtureB().getUserData() == "small") && contact.getFixtureA().getUserData() == "left" && contact.getFixtureB().getBody().getLinearVelocity().x < 0){
                    contact.setEnabled(false);
                }
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.34f,0.24f, 0.42f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(Gdx.graphics.getDeltaTime(), 6, 6);

        gameObjectManager.destroyBodies();

        gameManager.update();
        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();
        screenJoystick.update(camera.unproject(new Vector3(startMouseX,startMouseY,0)), camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0)), game.getBatch());
        gameObjectManager.update(game.getBatch());
        game.getBatch().end();

        if(Gdx.input.isTouched(0)){
            gameObjectManager.player.move(screenJoystick.getAxis());
        }


        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
