package com.example.application01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivitySegunda extends AppCompatActivity {
    private String dadosJSON;
    private ListView listView;
    private List<Estudante> lista;
    private ArrayAdapter<Estudante> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);
        dadosJSON = getIntent().getStringExtra("dados");
        Toast.makeText(ActivitySegunda.this,dadosJSON,Toast.LENGTH_LONG).show();
        listView = findViewById(R.id.listaViewDados);
        lista = consumirJSON();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,
                lista);
        listView.setAdapter(adapter);
    }

    private List<Estudante> consumirJSON() {
        List<Estudante> listaEstudantes = null;
        if (dadosJSON != null) {
            listaEstudantes = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(dadosJSON);
                JSONArray jsonArray = jsonObject.getJSONArray("estudantes");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Estudante estudante = new Estudante();
                    estudante.setNome(object.getString("nomeEstudante"));
                    estudante.setDisciplina(object.getString("disciplinaEstudante"));
                    estudante.setNota(object.getInt("notaEstudante"));
                    listaEstudantes.add(estudante);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }//try
        }
        return listaEstudantes;
    }
    }
