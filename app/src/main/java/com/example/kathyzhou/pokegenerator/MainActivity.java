package com.example.kathyzhou.pokegenerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button generateFirst = findViewById(R.id.generate);
        generateFirst.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v == generateFirst) {
                    Intent intentMain = new Intent(MainActivity.this, pokemon.class);
                    MainActivity.this.startActivity(intentMain);
                    Log.i("Content", "Main Layout");
                }
            }
        });
    }
}
