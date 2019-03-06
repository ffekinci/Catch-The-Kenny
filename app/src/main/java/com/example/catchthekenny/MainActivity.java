package com.example.catchthekenny;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView scoreText;
    TextView timeText;
    int score;
    ImageView[] imageView = new ImageView[9];
    Handler handler;
    Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreText = (TextView) findViewById(R.id.tw_Score);
        timeText = (TextView) findViewById(R.id.tw_Time);

        imageView[0] = findViewById(R.id.imageView);
        imageView[1] = findViewById(R.id.imageView2);
        imageView[2] = findViewById(R.id.imageView3);
        imageView[3] = findViewById(R.id.imageView4);
        imageView[4] = findViewById(R.id.imageView5);
        imageView[5] = findViewById(R.id.imageView6);
        imageView[6] = findViewById(R.id.imageView7);
        imageView[7] = findViewById(R.id.imageView8);
        imageView[8] = findViewById(R.id.imageView9);

        score = 0;

        hideImage();

        new CountDownTimer(10000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Time: " + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {

                timeText.setText("Time's up!");
                handler.removeCallbacks(runnable);

                for(ImageView img : imageView){
                    img.setVisibility(View.INVISIBLE);
                }

                SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
                int highScore = sp.getInt("highScore",0);
                if(score > highScore){
                    sp.edit().putInt("highScore", score).apply();
                }


                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Restart?");
                alert.setMessage("Your score: "+ score + " High Score: "+highScore);




                alert.setPositiveButton("Tekrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);

                    }
                });

                alert.setNegativeButton("Çıkış", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                alert.show();
            }
        }.start();
    }

    public void increaseScore(View  view){

        score++;

        scoreText.setText("Score: "+score);

    }

    public void hideImage(){

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for(ImageView img : imageView){
                    img.setVisibility(View.INVISIBLE);
                }

                Random random = new Random();

                int sayi = random.nextInt(9);
                imageView[sayi].setVisibility(View.VISIBLE);

                handler.postDelayed(runnable, 400);
            }
        };

        handler.post(runnable);


    }
}
