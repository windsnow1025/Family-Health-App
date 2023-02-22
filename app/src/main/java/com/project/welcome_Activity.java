package com.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.project.utils.ChangeColor;

import java.util.Timer;
import java.util.TimerTask;

public class welcome_Activity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_main);

        ChangeColor.setStatusBarColor(this, Color.parseColor("#FFFFFFFF"),false);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //start new activity
                Intent intent = new Intent();
                intent.setClass(welcome_Activity.this,MainActivity.class);
                startActivity(intent);
                welcome_Activity.this.finish();
            }
        }, 1000);


    }
}