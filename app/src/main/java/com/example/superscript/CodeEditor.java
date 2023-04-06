package com.example.superscript;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CodeEditor extends AppCompatActivity {

    private Button run;
    private TextView output;
    private EditText codeView;
    private Button exit;
    private String filename = "new.ssp";
    private Button save;
    private String lastSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_editor);

        getSupportActionBar().setTitle("Code Editor");

        run = (Button) findViewById(R.id.run);
        run.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ runCode(); }
        });

        output = (TextView) findViewById(R.id.output);
        output.setMovementMethod(new ScrollingMovementMethod());

        exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                exitEditor();
            }
        });

        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                save(true);
            }
        });

        codeView = (EditText) findViewById(R.id.code);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            filename = extras.getString("name") + ".ssp";
        }

        load();
        save(false);
    }

    @Override
    public void onBackPressed(){

    }

    public void runCode(){
        String code = codeView.getText().toString();
        SSP ssp = new SSP();
        output.setText("");
        ssp.output = output;
        ssp.execute(ssp.compile(code));
    }

    public void goBack(){
        super.onBackPressed();
    }

    public void exitEditor(){
        if(!lastSaved.equals(codeView.getText().toString())) {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("    Unsaved Changes").setMessage(" Would you like to exit without saving?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            goBack();
                        }
                    }).setNegativeButton("No", null).show();
        } else{
            goBack();
        }
    }


    public void save(Boolean showSaving){
        String code = codeView.getText().toString();
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(filename, MODE_PRIVATE);
            fos.write(code.getBytes());
            if(showSaving) Toast.makeText(this,"Your code has been saved.", Toast.LENGTH_LONG).show();
            lastSaved = code;
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            if(showSaving) Toast.makeText(this,"An error occurred while saving.", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            if(showSaving) Toast.makeText(this,"An error occurred while saving.", Toast.LENGTH_LONG).show();
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load(){
        FileInputStream fis = null;

        try {
            fis = openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String code;

            while ((code = br.readLine()) != null) {
                    sb.append(code).append("\n");
            }
            codeView.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}