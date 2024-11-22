package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class BadEndingScreen implements Screen {
    final MathBob game;
    private Texture badEndingImage; // Variabel untuk gambar
    private Music badEndingMusic;  // Variabel untuk audio

    public BadEndingScreen(final MathBob game) {
        this.game = game;


        badEndingImage = new Texture(Gdx.files.internal("failed.png"));
        badEndingMusic = Gdx.audio.newMusic(Gdx.files.internal("rawr.mp3"));
        badEndingMusic.setLooping(false); // Pastikan audio tidak loop
        badEndingMusic.play(); // Mulai memutar audio
        badEndingMusic.setVolume(5f);
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
        game.batch.draw(badEndingImage, 0, 0, worldWidth, worldHeight);
        game.font.draw(game.batch, "Tekan Enter untuk mulai kembali", 1, 1);
        game.font.setColor(Color.GREEN);

        game.batch.end();


        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new Peraturan(game));
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
        badEndingImage.dispose(); // Hapus gambar
        badEndingMusic.dispose(); // Hentikan dan hapus audio
    }
}
