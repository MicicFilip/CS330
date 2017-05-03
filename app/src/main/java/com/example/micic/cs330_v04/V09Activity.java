package com.example.micic.cs330_v04;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Micic on 03-May-17.
 */

public class V09Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v09);

        new AccessWebServiceTask().execute("Belgrade");
        new ReadJSONFeedTask().execute("https://restcountries.eu" + "/rest/" + "v2/" + "name/" + "serbia");

    }

    private class AccessWebServiceTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return WordDefinition(urls[0]);
        }

        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
        }
    }

    private String WordDefinition(String word) {
        InputStream in = null;
        String strDefinition = "";


        try {
            URL url = new URL("http://services.aonaware.com/DictService/" + "DictService.asmx/Define?word=" + word);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //in = OpenHttpConnection("http://services.aonaware.com/DictService/" + "DictService.asmx/Define?word=" + word);
            in = new BufferedInputStream(urlConnection.getInputStream());

            Document doc = null;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db;
            try {
                db = dbf.newDocumentBuilder();
                doc = db.parse(in);
            } catch (ParserConfigurationException e) {

                e.printStackTrace();
            } catch (Exception e) {

                e.printStackTrace();
            }
            doc.getDocumentElement().normalize();

            NodeList definitionElements =
                    doc.getElementsByTagName("Definition");
            for (int i = 0; i < definitionElements.getLength(); i++) {
                Node itemNode = definitionElements.item(i);
                if (itemNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element definitionElement = (Element) itemNode;
                    NodeList wordDefinitionElements = (definitionElement).getElementsByTagName("WordDefinition");
                    strDefinition = "";

                    for (int j = 0; j < wordDefinitionElements.getLength(); j++) {

                        Element wordDefinitionElement =
                                (Element) wordDefinitionElements.item(j);
                        NodeList textNodes = ((Node) wordDefinitionElement).getChildNodes();
                        strDefinition += ((Node) textNodes.item(0)).getNodeValue() + ". \n";
                    }
                }
            }
        } catch (IOException e1) {
            Log.d("NetworkingActivity", e1.getLocalizedMessage());
        }
//---vraćanje definicije reči---
        return strDefinition;
    }

    // JSON

    public String readJSONFeed(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } else {
                Log.e("JSON", "Neuspešno učitavanje datoteke");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private class ReadJSONFeedTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return readJSONFeed(urls[0]);
        }

        protected void onPostExecute(String result) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                Log.i("JSON", "Broj nizova u toku: " +
                        jsonArray.length());
//---Prikazuje sadržaj JSON toka---
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Toast.makeText(getBaseContext(), jsonObject.getString("appeId")
                                    +
                                    " - " + jsonObject.getString("inputTime"),
                            Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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
