package com.example.kathyzhou.pokegenerator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Random;


/**
 * class for the pokemon activity
 * calls API to get pokemon
 */
public class Pokemon extends AppCompatActivity {

    /**
     *  decimal format to format weight to 2 decimal places.
     */
    DecimalFormat twoDecimals = new DecimalFormat("#.##");

    /**
     * name of pokemon
     */
    private String name;
    /**
     * weight of pokemon
     */
    private String weight;
    /**
     * number of pokemon
     */
    private String pokemonNo;
    /**
     * type of pokemon
     */
    private String typeName;

    /** Request queue for our network requests. */
    private static RequestQueue requestQueue;

    /**
     * runs on creation of app instance
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon);

        //creates buttons for the page
        //regenerate a new pokemon button
        final Button regenerate = findViewById(R.id.regenerate);
        //go home button
        final Button home = findViewById(R.id.home);

        // Set up a queue for our Volley requests
        requestQueue = Volley.newRequestQueue(this);

        //creates listeners for the home butotn
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v  == home) {
                    //returns to main activity upon click
                    Intent intentMain = new Intent(Pokemon.this, MainActivity.class);
                    Pokemon.this.startActivity(intentMain);
                    Log.d("button", "Pokemon to Main Layout");
                }
            }
        });

        //creates listener for the regenerate button
        regenerate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v == regenerate) {
                    //recalls the API
                    Log.d("button", "regenerate a new pokemon");
                    Log.d("API", "pokemon but before API call1");
                    startAPICall();
                    Log.d("API", "Started API on Pokemon.java1");
                }
            }
        });

        //calls the API
        Log.d("API", "pokemon but before API call");
        startAPICall();
        Log.d("API", "Started API on Pokemon.java");
    }

    /**
     * random number generator
     * @return a random number between 1 and 720
     */
    public String generateRandom() {
        Random generator = new Random();
        int n = generator.nextInt(720) + 1;
        return Integer.toString(n);
    }


    /**
     * Make an API call.
     */
    void startAPICall() {
        //generates a random pokemon
        pokemonNo = generateRandom();
        //makes a request
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

                            //json parser
                            JsonParser parser = new JsonParser();
                            JsonObject rootObj = parser.parse(json).getAsJsonObject();

                            //gets name and weight
                            name = rootObj.get("name").getAsString();
                            int weightHectograms = rootObj.get("weight").getAsInt();
                            //recalculates weight from hectograms to pounds and formats it
                            weight = twoDecimals.format(weightHectograms * 0.220462);

                            Log.w("API", name);
                            Log.w("API", weight + " lbs");

                            //creates an array of json objects
                            JsonArray typeArr = rootObj.getAsJsonArray("types");


                            //gets type of pokemon
                            for (int i = 0; i < typeArr.size(); i++) {
                                typeName = typeArr.get(i).getAsJsonObject().get("type").getAsJsonObject().get("name").toString();

                            }

                            //json object of sprites
                            JsonObject image = rootObj.getAsJsonObject("sprites");

                            //front sprite
                            String frontDefault = image.get("front_default").toString();
                            frontDefault = frontDefault.replaceAll("^\"|\"$", "");
                            Log.d("API", "Image link front default " + frontDefault);

                            //back sprite
                            String backDefault = image.get("back_default").toString();
                            backDefault = backDefault.replaceAll("^\"|\"$", "");


                            //text views on app
                            final TextView nameOfPokemon = findViewById(R.id.name);
                            final TextView weightOfPokemon = findViewById(R.id.weight);
                            final TextView pokemonNumber = findViewById(R.id.number);
                            final TextView pokemonType = findViewById(R.id.type);

                            //centers text views
                            weightOfPokemon.setGravity(Gravity.CENTER_HORIZONTAL);
                            nameOfPokemon.setGravity(Gravity.CENTER_HORIZONTAL);
                            pokemonNumber.setGravity(Gravity.CENTER_HORIZONTAL);
                            pokemonType.setGravity(Gravity.CENTER_HORIZONTAL);

                            //sets variable to textview
                            nameOfPokemon.setText(name);
                            weightOfPokemon.setText(weight + " lbs");
                            pokemonNumber.setText(pokemonNo);
                            pokemonType.setText(typeName);

                            //frontView avatar
                            ImageView frontView = findViewById(R.id.frontView);
                            new ImageLoadTask(frontDefault, frontView).execute();

                            //backView avatar
                            ImageView backView = findViewById(R.id.backView);
                            new ImageLoadTask(backDefault, backView).execute();


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

