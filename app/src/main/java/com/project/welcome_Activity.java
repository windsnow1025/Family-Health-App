package com.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.project.Sqlite.UserLocalDao;
import com.project.utils.ChangeColor;

import java.util.Timer;
import java.util.TimerTask;

public class welcome_Activity extends AppCompatActivity {

    private UserLocalDao userLocalDao;
    private String userID = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_main);

        ChangeColor.setStatusBarColor(this, Color.parseColor("#FFFFFFFF"), false);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                userLocalDao = new UserLocalDao(getApplicationContext());
                userLocalDao.open();
                userID = userLocalDao.getUser();
                Intent intent = new Intent();
                if (userID==null) {
                    intent.setClass(welcome_Activity.this, Login_1.class);
                } else {
                    intent.setClass(welcome_Activity.this, MainActivity.class);
                }
                startActivity(intent);
                welcome_Activity.this.finish();
            }
        }, 1000);


    }
}