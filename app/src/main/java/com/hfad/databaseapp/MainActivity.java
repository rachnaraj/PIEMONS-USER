package com.hfad.databaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = findViewById(R.id.take_photos);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                View view = null;
                Intent myIntent = new Intent(v.getContext(), Take_photos.class);
                startActivityForResult(myIntent, 0);
            }
        });

        final Button button1 = findViewById(R.id.upload_photos);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), uploadActivity.class);
                startActivityForResult(myIntent, 0);
                //finish();

            }
        });


    }
}
