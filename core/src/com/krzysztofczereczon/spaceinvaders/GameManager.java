package com.krzysztofczereczon.spaceinvaders;

import com.badlogic.gdx.Gdx;

public class GameManager {

    public int gameLevel = 0;

    public boolean gameHaveSarted = false;
    public int requiredToLevelUp = gameLevel * 3;
    private int neededAsteroids = gameLevel;
    public int playerHp = 100;
    public int score = 0;

    private float timer;
    private float gameSpeed = 1.25f;
    public float requredTimeToRespawn;

    public GameObjectManager gameObjectManager;

    public GameManager(GameObjectManager gameObjectManager){
        this.gameObjectManager = gameObjectManager;
    }

    public void update(){
        if(gameHaveSarted) {
            timer += Gdx.graphics.getDeltaTime();
            if(timer >= requredTimeToRespawn && neededAsteroids != 0){
                gameObjectManager.addBigAsteroid();
                neededAsteroids--;
                timer = 0;
                requredTimeToRespawn = gameSpeed;
            }
        }

        if(requiredToLevelUp == 0){
            gameLevel ++;
            requiredToLevelUp = gameLevel * 3;
            neededAsteroids = gameLevel;
            timer = 0;
            requredTimeToRespawn = 5f;
        }
    }

}
