package com.example.casaativ01;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private final String URL = "https://jsonplaceholder.typicode.com/posts";
    private ArrayAdapter<User> adapter;
    private List<User> dadosBaixados = null;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.dadosID);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        ModificarDados modificarDados = new ModificarDados();
        executor.execute(modificarDados);
    }

    private class ModificarDados implements Runnable {
        @Override
        public void run() {
            // Background work here
            Conexao conexao = new Conexao();
            InputStream inputStream = conexao.obterRespostaHTTP(URL);
            Auxilia auxilia = new Auxilia();
            String textoJSON = auxilia.converter(inputStream);
            Log.i("JSON", "doInBackground: " + textoJSON);
            Gson gson = new Gson();

            if(textoJSON != null){
                Type type = new TypeToken<List<User>>(){}.getType();
                dadosBaixados = gson.fromJson(textoJSON,type);
                handler.post(() -> {
                    adapter.addAll(dadosBaixados);
                });
            } else {
                handler.post(() -> {
                    Toast.makeText(getApplicationContext(),"Não foi possível obter JSON",
                            Toast.LENGTH_SHORT).show();
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
