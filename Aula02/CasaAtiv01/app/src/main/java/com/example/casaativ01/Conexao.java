package com.example.casaativ01;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.MalformedInputException;

public class Conexao {

    public InputStream obterRespostaHTTP(String end) {
        //FAZER CONEXÃO HTTP NO JAVA
        try {
            URL url =new URL(end);
            HttpURLConnection conexao = (HttpURLConnection)
                    url.openConnection();
            conexao.setRequestMethod("GET");
            return new BufferedInputStream(conexao.getInputStream());

        }catch (MalformedInputException e){
            e.printStackTrace();

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
