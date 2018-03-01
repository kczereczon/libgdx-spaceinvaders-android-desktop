package com.krzysztofczereczon.spaceinvaders;

import com.badlogic.gdx.Gdx;

public class GameManager {

    public int gameLevel = 1;
    public boolean gameHaveSarted = false;
    public int requiredToLevelUp = gameLevel * 3;
    private int neededAsteroids = gameLevel;

    private float timer;
    private float gameSpeed = 1.25f;

    private GameObjectManager gameObjectManager;

    public GameManager(GameObjectManager gameObjectManager){
        this.gameObjectManager = gameObjectManager;
    }

    public void update(){
        if(gameHaveSarted) {
            timer += Gdx.graphics.getDeltaTime();
            if(timer >= gameSpeed && neededAsteroids != 0){
                gameObjectManager.addBigAsteroid();
                neededAsteroids--;
                timer = 0;
            }
        }

        if(requiredToLevelUp == 0){
            gameLevel ++;
            requiredToLevelUp = gameLevel * 3;
            neededAsteroids = gameLevel;
        }
    }

}
