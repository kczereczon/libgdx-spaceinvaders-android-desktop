package com.krzysztofczereczon.spaceinvaders.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.krzysztofczereczon.spaceinvaders.GameManager;


public class GameGui extends com.badlogic.gdx.scenes.scene2d.Stage {

    private Skin labelSkin;
    private GameManager gameManager;
    public Label label;

    public GameGui(GameManager gameManager, Camera camera){
        super();
        this.gameManager = gameManager;
        createLabelSkin();
        label = new Label("TAP TO START!", labelSkin);
        getViewport().update((int) camera.viewportWidth, (int) camera.viewportHeight);
        label.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
        addActor(label);

    }

    public void update(){
        if(gameManager.gameHaveSarted){
            label.setText("");
        }
    }

    private void createLabelSkin(){
        //Create a font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("pixelart.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        parameter.color = Color.WHITE;
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
