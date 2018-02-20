package com.krzysztofczereczon.spaceinvaders.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Player extends Sprite {


    public Player(){
        super(new Texture("player.png"));
        setPosition(100,100);
        setSize(64, 64);
    }

    public void move(Vector3 vector){
        float screenX = vector.x - getX() - getWidth();
        float screenY = vector.y - getY() - getHeight();
        float angle = (float)Math.atan2(screenX, screenY);

        setRotation((float)(-angle * 180/Math.PI));
        System.out.println(angle);
    }

    public void update(SpriteBatch batch){
        batch.draw(this,getX(),getY(), getWidth()/2, getHeight()/2,getWidth(),getHeight(),1,1,getRotation());
    }
}
