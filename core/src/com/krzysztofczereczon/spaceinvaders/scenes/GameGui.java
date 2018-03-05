package com.krzysztofczereczon.spaceinvaders.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.krzysztofczereczon.spaceinvaders.GameInfo;
import com.krzysztofczereczon.spaceinvaders.GameManager;


public class GameGui extends com.badlogic.gdx.scenes.scene2d.Stage {

    private Skin labelSkin;
    private Skin hpSkin;
    private GameManager gameManager;
    private Label label;
    private Label score;
    private Label currentWave;
    private Label playerHp;

    GameGui(GameManager gameManager, Camera camera){
        super();
        this.gameManager = gameManager;

        createLabelSkin();
        createHpSkin();

        label = new Label("TAP TO START!", labelSkin);
        score = new Label("SCORE: ", labelSkin);
        currentWave = new Label("WAVE: ", labelSkin);
        playerHp = new Label("HP: ", hpSkin);

        getViewport().update((int) camera.viewportWidth, (int) camera.viewportHeight);

        label.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
        score.setPosition(Gdx.graphics.getWidth()/GameInfo.PPM,Gdx.graphics.getHeight() - Gdx.graphics.getWidth()/GameInfo.PPM * 2);
        currentWave.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight() - Gdx.graphics.getWidth()/GameInfo.PPM * 2, 0);
        playerHp.setPosition(Gdx.graphics.getWidth()/ GameInfo.PPM * 2,Gdx.graphics.getWidth()/ GameInfo.PPM * 2);

        addActor(label);
        addActor(score);
        addActor(currentWave);
        addActor(playerHp);

    }

    public void update(){
        if(gameManager.gameHaveSarted){
            if(gameManager.requredTimeToRespawn == 5){
                label.setText("WAVE " + gameManager.gameLevel);
                label.setAlignment(0);
            }else{
                label.setText("");
            }
        }
        score.setText("SCORE: "+ gameManager.score);
        currentWave.setText("WAVE: " + gameManager.gameLevel);
        playerHp.setPosition(  ( gameManager.gameObjectManager.player.body.getPosition().x) * 100, Gdx.graphics.getHeight()/2 + gameManager.gameObjectManager.player.body.getPosition().y * GameInfo.PPM + 64, 0);
        playerHp.setText("HP: " + gameManager.playerHp);
    }

    private void createLabelSkin(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("pixelart.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Gdx.graphics.getWidth()/ GameInfo.PPM * 2;
        parameter.color = Color.WHITE;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = 2;
        parameter.shadowOffsetY = 2;
        BitmapFont title = generator.generateFont(parameter);

        labelSkin = new Skin();
        labelSkin.add("default", title);

        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = labelSkin.getFont("default");

        labelSkin.add("default", labelStyle);
    }

    private void createHpSkin(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("pixelart.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Gdx.graphics.getWidth()/ GameInfo.PPM * 2;
        parameter.color = Color.WHITE;
        parameter.shadowColor = Color.RED;
        parameter.shadowOffsetX = 2;
        parameter.shadowOffsetY = 2;
        BitmapFont title = generator.generateFont(parameter);

        hpSkin = new Skin();
        hpSkin.add("default", title);

        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = hpSkin.getFont("default");

        hpSkin.add("default", labelStyle);
    }


}
