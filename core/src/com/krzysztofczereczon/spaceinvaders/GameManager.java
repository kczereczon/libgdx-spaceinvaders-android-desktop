package com.krzysztofczereczon.spaceinvaders;

public class GameManager {

    public int gameLevel = 1;
    public int lastLevel = -1;
    private GameObjectManager gameObjectManager;

    public GameManager(GameObjectManager gameObjectManager){
        this.gameObjectManager = gameObjectManager;
    }

    public void update(){
        if(gameObjectManager.asteroidsBig.size == 0 && gameObjectManager.asteroidsSmall.size == 0 && gameObjectManager.asteroidsMedium.size == 0){
            gameLevel++;
            System.out.println(gameLevel);
        }

    }

}
