package com.krzysztofczereczon.spaceinvaders;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.krzysztofczereczon.spaceinvaders.scenes.MainGameScene;
import com.krzysztofczereczon.spaceinvaders.scenes.Menu;

public class Game extends com.badlogic.gdx.Game {
	SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();

		setScreen(new MainGameScene(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}

	public SpriteBatch getBatch() {
		return batch;
	}


}
