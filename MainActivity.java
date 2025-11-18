package com.example.ap_usoapi;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.freetogame.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GameApiService gameApiService = retrofit.create(GameApiService.class);
        Call<List<Game>> call = gameApiService.getGames();

        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.isSuccessful()) {
                    List<Game> games = response.body();
                    gameAdapter = new GameAdapter(games);
                    recyclerView.setAdapter(gameAdapter);
                } else {
                    Log.e("MainActivity", "Error en la respuesta de la API");
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Log.e("MainActivity", "Error en la llamada a la API", t);
            }
        });
    }
}