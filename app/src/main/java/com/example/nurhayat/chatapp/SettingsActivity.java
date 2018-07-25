package com.example.nurhayat.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    Button hesap, sohbetler, bildirimler,veri , yardim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

    }

    public void profile (View view){

        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
        startActivity(intent);
    }

    public void hesap (View view){

        Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
        startActivity(intent);
    }

    public void sohbet (View view){

        Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
        startActivity(intent);
    }

    public void bildirim (View view){

        Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
        startActivity(intent);
    }

    public void veri (View view){

        Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
        startActivity(intent);
    }

    public void yardim (View view){

        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
        startActivity(intent);
    }
}
