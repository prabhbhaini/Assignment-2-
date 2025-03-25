package com.omdb.app.omdb;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class OMDBHandler {

    public interface IComplete {
        void onComplete(OMDBData data);
    }

    private String APIKey;
    private String url = "https://www.omdbapi.com/?";
    private IComplete iComplete;


    public OMDBHandler(String APIKey, IComplete iComplete){
        this.APIKey = APIKey;
        this.iComplete = iComplete;
    }

    public void getSearch(Context context, String term){
        String url1 = url + "s=" + term + "&apiKey=" + this.APIKey.strip();
        Log.d("App", url1);

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        parseSearches(queue, response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("App", error.getMessage());
                    }
        });
        queue.add(stringRequest);
    }

    private void parseSearches(RequestQueue queue, String response){
        Log.d("App", response);


        try {
            JSONObject top = new JSONObject(response);
            JSONArray searches = top.getJSONArray("Search");

            ArrayList<String> results = new ArrayList<>();
            for(int i = 0; i< searches.length(); i++){
                JSONObject search = searches.getJSONObject(i);

                String imdbId = search.getString("imdbID");

                results.add(imdbId);
            }

            getExtraDetails(queue, results);

        } catch (Exception e) {
            Log.d("App", e.getMessage());
        }
    }

    private void getExtraDetails(RequestQueue queue, ArrayList<String> data){
       // http://www.omdbapi.com/?i=tt2370248&apikey=c908629c


        for (int i = 0; i < data.size(); i++) {
            String imdbId = data.get(i);
            String url1 = url + "i=" + imdbId + "&apiKey=" + this.APIKey;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url1,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            OMDBData result = parseExtraDetails(imdbId, response);
                            if(result != null){
                                iComplete.onComplete(result);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("App", error.getMessage());
                }
            });
            queue.add(stringRequest);
        }



    }

    private OMDBData parseExtraDetails(String imdbId, String response) {


        try {
            JSONObject top = new JSONObject(response);

            String title = top.getString("Title");
            String syear = top.getString("Year");
            int year = Integer.parseInt(syear);

            String srating = top.getString("imdbRating");
            int rating = (int)Math.round(Double.parseDouble(srating));

            String desc = top.getString("Plot");
            String poster = top.getString("Poster");
            String genre = top.getString("Genre");
            String studio = "None";


            return new OMDBData(title, studio, year,rating, desc,genre,imdbId, poster );



        } catch (Exception e) {
            Log.d("App", e.getMessage());
        }
        return  null;
    }


}
