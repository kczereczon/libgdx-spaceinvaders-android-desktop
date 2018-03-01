package com.krzysztofczereczon.spaceinvaders.analog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.krzysztofczereczon.spaceinvaders.GameInfo;

public class Analog extends Sprite {

    Analog(){
        super(new Texture("analog.png"));
    }

    public void update(Vector3 updatedPos, Vector3 startPos, float radius, SpriteBatch batch){
        if(Gdx.input.isTouched(0)) {
            Vector2 positionOnCircle = vectorToPointOnCircle(new Vector3(startPos.x, startPos.y, 0), updatedPos, radius);
            float x = (Math.pow(updatedPos.x - startPos.x, 2) + Math.pow(updatedPos.y - startPos.y, 2) <= Math.pow(radius, 2)) ? updatedPos.x - getWidth() / 2 / GameInfo.PPM : positionOnCircle.x - getWidth() / 2 / GameInfo.PPM;
            float y = (Math.pow(updatedPos.x - startPos.x, 2) + Math.pow(updatedPos.y - startPos.y, 2) <= Math.pow(radius, 2)) ? updatedPos.y - getHeight() / 2 / GameInfo.PPM : positionOnCircle.y - getHeight() / 2 / GameInfo.PPM;
            setPosition(x + getWidth() / 2 / GameInfo.PPM, y + getHeight() / 2 / GameInfo.PPM);
        }

        batch.draw(this, getX()-getWidth()/2/GameInfo.PPM, getY()-getHeight()/2/GameInfo.PPM, getWidth() / GameInfo.PPM, getHeight() / GameInfo.PPM);
    }

    private Vector2 vectorToPointOnCircle(Vector3 circeOrgin, Vector3 vector, float radius){
        float tan = (float)Math.atan2(vector.x - circeOrgin.x, vector.y - circeOrgin.y);
        float x = circeOrgin.x + (float)(Math.sin(tan) * radius);
        float y = circeOrgin.y + (float)(Math.cos(tan) * radius);
        return new Vector2(x,y);
    }
}
