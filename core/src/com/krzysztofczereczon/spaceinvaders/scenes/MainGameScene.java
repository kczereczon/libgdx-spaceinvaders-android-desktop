package com.krzysztofczereczon.spaceinvaders.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.krzysztofczereczon.spaceinvaders.Game;
import com.krzysztofczereczon.spaceinvaders.GameInfo;
import com.krzysztofczereczon.spaceinvaders.objects.Player;

public class MainGameScene implements com.badlogic.gdx.Screen {

    Game game;
    Player player;
    Camera camera;
    World world;

    int mouseX, mouseY;

    Box2DDebugRenderer debugRenderer;

    public MainGameScene(Game game) {
        this.game = game;
        camera = new OrthographicCamera(GameInfo.WIDTH / GameInfo.PPM, GameInfo.HEIGHT / GameInfo.PPM);
        world = new World(new Vector2(0, 0), true);
        world.step(1/60f, 6, 6);
        debugRenderer = new Box2DDebugRenderer();

        //sprites objects
        player = new Player(world);

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
                mouseX = screenX;
                mouseY = screenY;
                System.out.println(screenX);
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                mouseX = screenX;
                mouseY = screenY;
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

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                if(contact.getFixtureA().getBody().getUserData() == "bullet" && contact.getFixtureB().getUserData() == "player"){
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
        Gdx.gl.glClearColor(0,0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getBatch().setProjectionMatrix(camera.combined);
        world.step(Gdx.graphics.getDeltaTime(), 6, 6);
        game.getBatch().begin();
        player.update(game.getBatch());
        game.getBatch().end();

        if(Gdx.input.isTouched(0)){
            player.move(new Vector3 (camera.unproject(new Vector3(mouseX, mouseY, 0))));
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
