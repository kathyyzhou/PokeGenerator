package com.example.kathyzhou.pokegenerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class pokemon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon);

        final Button generateNew = findViewById(R.id.generate);
        final Button home = findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v == home) {
                    Intent intentMain = new Intent(pokemon.this, MainActivity.class);
                    pokemon.this.startActivity(intentMain);
                    Log.i("Content", "Main Layout");
                }
            }
        });
    }
}

