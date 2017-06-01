package com.example.micic.cs330_v04;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import util.CommsThread;

/**
 * Created by Micic on 03-May-17.
 */

public class V09Activity extends AppCompatActivity {

    static final String NICKNAME = "Student1";
    InetAddress serverAddress;
    Socket socket;
    static TextView txtMessagesReceived;
    EditText txtMessage;
    CommsThread commsThread;

    public static Handler UIupdater = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int numOfBytesReceived = msg.arg1;
            byte[] buffer = (byte[]) msg.obj;
//---prevođenje byte niza u string---
            String strReceived = new String(buffer);
//---pruzimanje aktuelnog stringa---
            strReceived = strReceived.substring(
                    0, numOfBytesReceived);
//---prikazivanje primljenog teksta u TextView---
            txtMessagesReceived.setText(
                    txtMessagesReceived.getText().toString() +
                            strReceived);
        }
    };

    private class CreateCommThreadTask extends AsyncTask
            <Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
//---kreiranje soketa---
                serverAddress =
                        InetAddress.getByName("192.168.1.101");
                socket = new Socket(serverAddress, 500);
                commsThread = new CommsThread(socket);
                commsThread.start();
//---prijavljivanje korisnika; šalje nadimak---
                sendToServer(NICKNAME);
            } catch (UnknownHostException e) {
                Log.d("Sockets", e.getLocalizedMessage());
            } catch (IOException e) {
                Log.d("Sockets", e.getLocalizedMessage());
            }
            return null;
        }
    }

    private class WriteToServerTask extends AsyncTask
            <byte[], Void, Void> {
        protected Void doInBackground(byte[]...data) {
            commsThread.write(data[0]);
            return null;
        }
    }

    private class CloseSocketTask extends AsyncTask
            <Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                socket.close();
            } catch (IOException e) {
                Log.d("Sockets", e.getLocalizedMessage());
            }
            return null;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v09);


        txtMessage = (EditText) findViewById(R.id.txtMessage);
        txtMessagesReceived = (TextView)
                findViewById(R.id.txtMessagesReceived);
    }


    public void onClickSend(View view) {
//---prosleđivanje poruke na server---
        sendToServer(txtMessage.getText().toString());
    }
    private void sendToServer(String message) {
        byte[] theByteArray =
                message.getBytes();
        new WriteToServerTask().execute(theByteArray);
    }
    @Override
    public void onResume() {
        super.onResume();
        new CreateCommThreadTask().execute();
    }

    @Override
    public void onPause() {
        super.onPause();
        new CloseSocketTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_v09, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.back:
                Intent back = new Intent(this, MainActivity.class);
                this.startActivity(back);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
