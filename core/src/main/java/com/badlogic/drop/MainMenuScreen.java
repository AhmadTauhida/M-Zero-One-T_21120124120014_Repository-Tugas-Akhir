package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    final MathBob game;
    private Texture MainMenu;

    public MainMenuScreen(final MathBob game) {
    this.game = game;

    MainMenu = new Texture((Gdx.files.internal("MainMenu.png")));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();
        // Draw the background image
        game.batch.draw(MainMenu, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());

        // Draw text
        game.font.draw(game.batch, "Klik dimana saja untuk lanjut", 1, 1);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new Peraturan(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        MainMenu.dispose();

    }
}

