package com.example.kathyzhou.pokegenerator;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class Pokemon extends AppCompatActivity {

    /** Request queue for our network requests. */
    private static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon);

        final Button regenerate = findViewById(R.id.regenerate);
        final Button home = findViewById(R.id.home);

        // Set up a queue for our Volley requests
        requestQueue = Volley.newRequestQueue(this);

        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v  == home) {
                    Intent intentMain = new Intent(Pokemon.this, MainActivity.class);
                    Pokemon.this.startActivity(intentMain);
                    Log.d("button", "Pokemon to Main Layout");
                }
            }
        });

        regenerate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v == regenerate) {
                    Log.d("button", "regenerate a new pokemon");
                }
            }
        });

        Log.d("API", "pokemon but before API call");
        startAPICall();
        Log.d("API", "Started API on Pokemon.java");

    }

    /**
     * Make an API call.
     */
    void startAPICall() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://pokeapi.co/api/v2/",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            Log.d("API", response.toString());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.w("API", error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

