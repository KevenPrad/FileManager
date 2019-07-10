package com.example.fileconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    private TextView txt1;
    private TextView txt2;
    private TextView compare;

    final String pathsave= "R.res.write.txt";
    final File fiche = new File(pathsave);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        compare= findViewById(R.id.compare);

        try {
            // Creation du fichier
            fiche.createNewFile();
            // creation d'un writer (un écrivain)
            final FileWriter writer = new FileWriter(fiche);
            try {
                writer.write("ceci est un texte\n");
                writer.write("encore et encore");
            } finally {
                // quoiqu'il arrive, on ferme le fichier
                writer.close();
            }
        } catch (Exception e) {
            System.out.println("Impossible de creer le fichier");
        }

        String a1= printFile(R.raw.text1);
        String a2= printFile(R.raw.text2);

        txt1.setText(a1);
        txt2.setText(a2);

        String[]b1= toWord(a1);
        String[]b2= toWord(a2);

        toCompareAll(b1,b2);

        compare.setText(printFile(R.raw.write));


    }

    private String printFile(int _id) {

        InputStream file1 = getResources().openRawResource(_id);
        StringBuilder buf = new StringBuilder();
        try {
            if (file1 != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(file1));

                // String used to store the lines
                String str;


                // Read the file
                while ((str = reader.readLine()) != null) {
                    buf.append(str + "\r\n");
                }
                // Close streams
                reader.close();
                file1.close();
            }
        } catch (java.io.FileNotFoundException e) {
            Toast.makeText(this, "FileNotFoundException", Toast.LENGTH_LONG);
        } catch (IOException e) {
            Toast.makeText(this, "FileNotFoundException", Toast.LENGTH_LONG);
        }
        return buf.toString();
    }

    private String[] toWord(String a) {
        final String SEPARATEUR = "";
        String word[] = a.split(SEPARATEUR);
        return word;
    }

    private byte[] toByte(String a) {
        byte[] word = a.getBytes(Charset.forName("UTF-8"));

        return word;
    }

    private String byteToString(byte[] a){
        String s = new String (a);
        return s;
    }

    private boolean toCompareSingle(String a, String b) {
        if (a.equals(b)) {
            return true;
        } else {
            return false;
        }
    }

    private void toCompareAll(String[] a1, String[] a2){
        int n = a2.length;
        if (a1.length < a2.length){
            n=a1.length;
        }
        for (int i=0; i<n; i++){
            if (toCompareSingle(a1[i],a2[i])){
                compare.setText("same text");
            }else{
                compare.setText("different text rang : " + i );
                break;
            }
        }
    }


}



