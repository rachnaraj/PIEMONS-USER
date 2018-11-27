package com.hfad.databaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final ImageButton button = findViewById(R.id.take_photos);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                View view = null;
                Intent myIntent = new Intent(v.getContext(), Take_photos.class);
                //startActivityForResult(myIntent, 0);
                startActivity(myIntent);
            }
        });

        final ImageButton button1 = findViewById(R.id.upload_photos);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), uploadActivity.class);
                //startActivityForResult(myIntent, 0);
                startActivity(myIntent);
                //finish();

            }
        });
        final ImageButton button2 = findViewById(R.id.upload_loc);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), LocationActivity.class);
                startActivity(myIntent);
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, Login_Activity.class));

                break;
        }

        return true;
    }




}
