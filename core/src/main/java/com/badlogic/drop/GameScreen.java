package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final MathBob game;

    Texture backgroundTexture;
    Music music;
    private int correctAnswers;
    private int wrongAnswers;
    boolean isGameOver;
    private float questionTimer;
    Sound correct;
    Sound wrong2;

    Texture[] questionSprites; // Array untuk soal
    Texture currentQuestionSprite; // Sprite soal saat ini
    String playerAnswer = ""; // Jawaban dari pemain
    String[] answers = {"63", "14", "13", "7", "324", "32", "45", "64", "173", "81"}; // Jawaban benar
    int currentQuestion = 0; // Indeks soal saat ini



    public GameScreen(final MathBob game) {
        this.game = game;

        backgroundTexture = new Texture(Gdx.files.internal("MainBG.png"));
        music = Gdx.audio.newMusic(Gdx.files.internal("Ending.mp3"));
        music.setLooping(true);
        music.setVolume(.5f);

        wrong2 = Gdx.audio.newSound(Gdx.files.internal("wrong2.mp3"));
        correct = Gdx.audio.newSound(Gdx.files.internal("correct.mp3"));


        questionSprites = new Texture[]{
            new Texture(Gdx.files.internal("question1.png")),
            new Texture(Gdx.files.internal("question2.png")),
            new Texture(Gdx.files.internal("question3.png")),
            new Texture(Gdx.files.internal("question4.png")),
            new Texture(Gdx.files.internal("question5.png")),
            new Texture(Gdx.files.internal("question6.png")),
            new Texture(Gdx.files.internal("question7.png")),
            new Texture(Gdx.files.internal("question8.png")),
            new Texture(Gdx.files.internal("question9.png")),
            new Texture(Gdx.files.internal("question10.png"))
        };

        currentQuestionSprite = questionSprites[0]; // Tampilkan soal pertama


    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();

        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();
        float cellWidth = worldWidth / 8;
        float cellHeight = worldHeight / 6;

        // Gambar background
        game.batch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);

        // Gambar soal di tengah atas layar (baris 2)
        game.batch.draw(currentQuestionSprite, cellWidth * 2, cellHeight * 1, cellWidth * 4, cellHeight * 4);

        // Gambar kotak jawaban di tengah bawah layar (baris 1)
        Texture answerBox = new Texture(Gdx.files.internal("answer_box.png"));
        game.batch.draw(answerBox, cellWidth * 2, cellHeight, cellWidth * 4, cellHeight);

        // Gambar jawaban pemain di dalam kotak jawaban
        BitmapFont font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.draw(game.batch, playerAnswer, cellWidth * 3, cellHeight * 1.5f);

        game.font.draw(game.batch, "Jawaban Benar: " + correctAnswers, 0, worldHeight);
        game.font.draw(game.batch, "Jawaban Salah: " + wrongAnswers, 6, worldHeight);
        float timeLeft = Math.max(7.0f - questionTimer, 0); // Hitung waktu tersisa
        game.font.draw(game.batch, "Waktu: " + String.format("%.1f detik", timeLeft), 3, worldHeight);
        game.font.draw(game.batch, "Jawaban: " + playerAnswer, 3, 2);
        game.font.setColor(Color.WHITE);

        game.batch.end();
    }


    private void logic() {
        if (isGameOver) return;

        questionTimer += Gdx.graphics.getDeltaTime();



        // Tangkap input pemain untuk kotak jawaban
        for (int i = Input.Keys.NUM_0; i <= Input.Keys.NUM_9; i++) {
            if (Gdx.input.isKeyJustPressed(i)) {
                playerAnswer += (i - Input.Keys.NUM_0); // Tambahkan angka ke jawaban
            }
        }

        // Hapus karakter terakhir jika Backspace ditekan
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) && playerAnswer.length() > 0) {
            playerAnswer = playerAnswer.substring(0, playerAnswer.length() - 1);
        }



        if (questionTimer >= 7.0f) {
            System.out.println("Waktu habis untuk soal ini!");
            wrong2.play();
            nextQuestion();
            wrongAnswers++;

        }

        // Jika pemain menekan Enter, periksa jawaban
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) ) {

            if (playerAnswer.equals(answers[currentQuestion])) {
                correctAnswers++;
                correct.play();
                System.out.println("Jawaban benar!");
            } else {
                wrongAnswers++;
                wrong2.play();
                System.out.println("Jawaban salah!");
            }

            playerAnswer = ""; // Reset jawaban pemain
            nextQuestion();
        }

        // Periksa kondisi game over atau menang
        if (wrongAnswers >= 3) {
            isGameOver = true;
            game.setScreen(new BadEndingScreen(game));
            dispose();
            return;
        } else if (currentQuestion >= 10 || correctAnswers >= 7) {
            isGameOver = true;
            game.setScreen(new GoodEndingScreen(game));
            dispose();
            return;
        }
    }

    private void nextQuestion() {
        currentQuestion++;
        questionTimer = 0;

        if (currentQuestion < questionSprites.length) {
            currentQuestionSprite = questionSprites[currentQuestion];
        }
    }


    @Override
    public void show() {
        music.play();
    }

    @Override
    public void render(float delta) {

        draw();
        logic();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        music.dispose();
        correct.dispose();
        wrong2.dispose();


    }




}
