package com.krzysztofczereczon.spaceinvaders.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.krzysztofczereczon.spaceinvaders.Game;
import com.krzysztofczereczon.spaceinvaders.GameInfo;

public class Menu implements Screen {
    private Skin skin;
    private Skin labelSkin;
    private Game game;

    private Stage stage;
    private Texture background;

    public Menu (final Game game){
        this.game = game;
        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);
        background = new Texture("starBackground.png");

        createBasicSkin();

        TextButton newGameButton = new TextButton("Start game.", skin);
        newGameButton.setPosition(Gdx.graphics.getWidth()/2 , Gdx.graphics.getHeight()/5, 0);

        newGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainGameScene(game));
            }
        });

        TextButton exitGameButton = new TextButton("Credits", skin);
        exitGameButton.setPosition(Gdx.graphics.getWidth()/2 , Gdx.graphics.getHeight()/5 - newGameButton.getHeight() - 2, 0);

        exitGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        createLabelSkin();

        Label title = new Label("Asteroids", labelSkin);
        title.setPosition(Gdx.graphics.getWidth()/2 - title.getWidth()/2 , Gdx.graphics.getHeight() - 5 * title.getHeight());

        stage.addActor(newGameButton);
        stage.addActor(exitGameButton);
        stage.addActor(title);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().begin();
        stage.getBatch().draw(background,0,0, background.getWidth() * GameInfo.WIDTH  / GameInfo.PPM, background.getHeight() * GameInfo.HEIGHT / 1.5f /GameInfo.PPM);
        stage.getBatch().end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        //stage.getViewport().update(GameInfo.WIDTH/GameInfo.PPM, GameInfo.HEIGHT/GameInfo.PPM);
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

    private void createBasicSkin(){
        //Create a font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("pixelart.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetY = 2;
        parameter.shadowOffsetX = 2;
        BitmapFont font32 = generator.generateFont(parameter); // font size 12 pixels

        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        skin = new Skin();
        skin.add("default", font32);


        //Create a texture
        Pixmap pixmap = new Pixmap((int) Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/15, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        skin.add("background",new Texture(pixmap));

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

    }

    private void createLabelSkin(){
        //Create a font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("pixelart.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 64;
        parameter.borderColor = Color.BLACK;
        parameter.color = Color.BROWN;
        parameter.borderWidth = 4;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = 5;
        parameter.shadowOffsetY = 5;
        BitmapFont title = generator.generateFont(parameter); // font size 12 pixels

        labelSkin = new Skin();
        labelSkin.add("default", title);

        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = labelSkin.getFont("default");

        labelSkin.add("default", labelStyle);
    }
}
