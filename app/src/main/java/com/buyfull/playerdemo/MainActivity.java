package com.buyfull.playerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.buyfull.player.BuyfullPlayer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private BuyfullPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = new BuyfullPlayer();
        try {
            InputStream is = getAssets().open("a1.wav");
            int length = is.available();
            byte[] wav = new byte[length];
            is.read(wav);
            is.close();
            player.setDataSource(wav);
            player.play();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
