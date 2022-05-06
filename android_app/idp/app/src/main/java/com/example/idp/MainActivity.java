package com.example.idp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {


    private Socket client;
    private PrintWriter printwriter;
    private BufferedReader reader;
    private EditText textField;
    private EditText numberField;
    private EditText decimalField;
    private TextView servertextField;
    private TextView servernumberField;
    private TextView serverdecimalField;
    private EditText ipField;
    private Button button;
    private String message;


//    public void clickbtn(View view) {
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textField = (EditText) findViewById(R.id.text);
        numberField = (EditText) findViewById(R.id.number);
        decimalField = (EditText) findViewById(R.id.decimal);
        ipField = (EditText) findViewById(R.id.ip);

        servertextField = (TextView) findViewById(R.id.serverText);
        servernumberField = (TextView) findViewById(R.id.serverNumber);
        serverdecimalField = (TextView) findViewById(R.id.serverDecimal);

        // reference to the send button
        button = (Button) findViewById(R.id.button);

        new Thread(new ClientThread()).start();

    }


// this thread runs the networking operations.
    class ClientThread implements Runnable {
        @Override
        public void run() {
            try {
                // the IP and port should be correct to have a connection established
                // Creates a stream socket and connects it to the specified port number on the named host.
                client = new Socket("34.125.211.48", 3389);  // connect to google cloud server
//                client = new Socket("192.168.0.115", 3389);  // connect to local host server

                // new thread which listens to data from the web server
                new Thread(new clientRecv()).start();

                //on submit button click, send data to web server
                while(true) {
                    button.setOnClickListener(new View.OnClickListener() {

                        // new thread which sends data to the web server
                        @Override
                        public void onClick(View v) {
                            new Thread(new clientSend()).start();
                        }
                    });
                }

            } catch (IOException e) {
                Log.i("client","connect cannot");
                e.printStackTrace();
            }

        }
    }

    // create a json string from the user inputs
    public String createJson(){
        JSONObject obj = new JSONObject();
        if(!ipField.getText().toString().matches("")){
            try {
                obj.put("ip",ipField.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(!textField.getText().toString().matches("")) {
            try {
                obj.put("text", textField.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(!numberField.getText().toString().matches("")) {
            try {
                obj.put("number", numberField.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(!decimalField.getText().toString().matches("")){
            try {
                obj.put("decimal",decimalField.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return obj.toString();
    }

    // parser the received json string from the web server and updates corresponding fields
    public void parseJson(String message){
        JSONObject obj = null;
        try {
            obj = new JSONObject(message);
            if(obj.has("text")){
                servertextField.setText(obj.getString("text"));
            }
            else {
                servertextField.setText("");
            }

            if(obj.has("number")){
                servernumberField.setText(obj.getString("number"));
            }
            else {
                servernumberField.setText("");
            }

            if(obj.has("decimal")){
                serverdecimalField.setText(obj.getString("decimal"));
            }
            else {
                serverdecimalField.setText("");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // send data to web server
    class clientSend implements  Runnable{
        @Override
        public void run(){
            // get the text message from user inputs
            message = createJson();
            try {
                printwriter = new PrintWriter(client.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            printwriter.println(message);  // write the message to output stream

            // clear all the user input fields on app
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textField.setText("");
                    numberField.setText("");
                    decimalField.setText("");
                    ipField.setText("");
                }
            });
        }

    }

    // receive data from web server
    class clientRecv implements Runnable{
        private String message = null;
        @Override
        public void run() {
            while(true) {
                try {
                    // read data from the web server
                    reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    this.message = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (message != null) {
                            // parse json string and update fields
                            parseJson(message);
                        }
                    }
                });
            }

        }
    }
}