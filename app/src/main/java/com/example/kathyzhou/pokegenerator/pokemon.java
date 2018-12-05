package com.example.kathyzhou.pokegenerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Random;


public class Pokemon extends AppCompatActivity {

    DecimalFormat twoDecimals = new DecimalFormat("#.##");

    private static String name;
    private static String weight;
    private static String pokemonNo;

    /** Request queue for our network requests. */
    private static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon);

        final Button regenerate = findViewById(R.id.regenerate);
        final Button home = findViewById(R.id.home);
        final TextView nameOfPokemon = findViewById(R.id.name);
        final TextView weightOfPokemon = findViewById(R.id.weight);
        final TextView pokemonNumber = findViewById(R.id.number);

        weightOfPokemon.setGravity(Gravity.CENTER_HORIZONTAL);
        nameOfPokemon.setGravity(Gravity.CENTER_HORIZONTAL);
        pokemonNumber.setGravity(Gravity.CENTER_HORIZONTAL);

        // Set up a queue for our Volley requests
        requestQueue = Volley.newRequestQueue(this);

        Log.d("API", "pokemon but before API call");
        startAPICall();
        Log.d("API", "Started API on Pokemon.java");

        nameOfPokemon.setText(name);
        weightOfPokemon.setText(weight + " lbs");
        pokemonNumber.setText(pokemonNo);


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
                    Log.d("API", "pokemon but before API call1");
                    startAPICall();
                    Log.d("API", "Started API on Pokemon.java1");

                    nameOfPokemon.setText(name);
                    weightOfPokemon.setText(weight + " lbs");
                    pokemonNumber.setText(pokemonNo);
                }
            }
        });

    }

    public String generateRandom() {
        Random generator = new Random();
        int n = generator.nextInt(720) + 1;
        return Integer.toString(n);
    }

    /**
     * Make an API call.
     */
    void startAPICall() {
        pokemonNo = generateRandom();
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://pokeapi.co/api/v2/pokemon/" + pokemonNo + "/",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {

                            Log.d("API", response.toString());
                            String json = new String(response.toString());


                            JsonParser parser = new JsonParser();
                            JsonObject rootObj = parser.parse(json).getAsJsonObject();


                            name = rootObj.get("name").getAsString();
                            int weightHectograms = rootObj.get("weight").getAsInt();
                            weight = twoDecimals.format(weightHectograms * 0.220462);

                            Log.w("API", name);
                            Log.w("API", weight + " lbs");
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

