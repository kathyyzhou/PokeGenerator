package com.example.kathyzhou.pokegenerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class Pokemon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon);

        final Button regenerate = findViewById(R.id.regenerate);
        final Button home = findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v  == home) {
                    Intent intentMain = new Intent(Pokemon.this, MainActivity.class);
                    Pokemon.this.startActivity(intentMain);
                    Log.d("button", "Pokemon to Main Layout");
                } else if (v == regenerate) {
                    Log.d("button", "New Pokemon Button clicked");
                }
            }
        });

    }

}

