package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class Peraturan implements Screen {
    final MathBob game;
    private Texture peraturan;


    public Peraturan(final MathBob game) {
        this.game = game;


        peraturan = new Texture(Gdx.files.internal("PERATURAN.png"));

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1); // Bersihkan layar dengan warna hitam

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();
        // Gambar gambar "bad ending" di tengah layar
        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();
        game.batch.draw(peraturan, 0, 0, worldWidth, worldHeight);
        game.font.draw(game.batch, "Tekan Enter untuk memulai", 1, 1);
        game.font.setColor(Color.GREEN);

        game.batch.end();


        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
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
        peraturan.dispose();

    }
}
