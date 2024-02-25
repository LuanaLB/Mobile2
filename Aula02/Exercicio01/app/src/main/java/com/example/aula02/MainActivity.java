package com.example.aula02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView textViewID;
    private final String URL = "https://jsonplaceholder.typicode.com/posts";
    private StringBuilder builder = null;
    private List<User> dadosBaixados = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewID = findViewById(R.id.dadosID);

        new ObterDados().execute();

        //doInBackground realiza a conexão com o Conexao.java e não e realizado na thread principal
        //onProgressUpdate e onPostExecute realizado na thread principal
    }//onCreate

    private class ObterDados extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            Conexao conexao = new Conexao();
            InputStream inputStream = conexao.obterRespostaHTTP(URL);
            Auxilia auxilia = new Auxilia();
            String textoJSON = auxilia.converter(inputStream);
            Log.i("JSON", "doInBackground: "+textoJSON);
            Gson gson = new Gson();
            builder = new StringBuilder();


            if(textoJSON != null){
                Type type = new TypeToken<List<User>>(){}.getType();
                dadosBaixados = gson.fromJson(textoJSON,type);
                for (int i = 0; i < dadosBaixados.size(); i++){
                    builder.append(dadosBaixados.get(i).getUserId()).append("\n");
                }//for
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Não foi possível obter JSON",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }//if else
            return null;
        }//doIn

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(),"Download começando...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            textViewID.setText(builder.toString());
        }

    }//inner class

}//class