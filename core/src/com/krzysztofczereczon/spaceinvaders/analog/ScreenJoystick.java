package com.krzysztofczereczon.spaceinvaders.analog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.krzysztofczereczon.spaceinvaders.GameInfo;

public class ScreenJoystick extends Sprite{

    private Analog analog;


    public ScreenJoystick(){
        super(new Texture("analogbackground.png"));
        analog = new Analog();
    }

    public void update(Vector3 startPos, Vector3 updatedPos, SpriteBatch batch){
        if(Gdx.input.isTouched()) {
            setPosition(startPos.x, startPos.y);
            analog.update(updatedPos, startPos, getHeight() / 2 / GameInfo.PPM, batch);
            System.out.println(getAxis());
            batch.draw(this, startPos.x - getWidth() / 2 / GameInfo.PPM, startPos.y - getHeight() / 2 / GameInfo.PPM, getWidth() / 2 / GameInfo.PPM, getHeight() / 2 / GameInfo.PPM, getWidth() / GameInfo.PPM, getHeight() / GameInfo.PPM, 1, 1, getRotation());
        }
    }

    public Vector2 getAxis(){
        return new Vector2(analog.getX() - getX(),analog.getY() - getY());
    }
}
