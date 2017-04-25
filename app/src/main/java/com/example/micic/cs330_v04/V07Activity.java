package com.example.micic.cs330_v04;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Micic on 12-Apr-17.
 */

public class V07Activity extends AppCompatActivity {

    private ListView lstNames;

    // Traži kod za čitanje kontakata
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v07);

        this.lstNames = (ListView) findViewById(R.id.lstNames);
        // Read and show the contacts
        showContacts();

    }

    /**
     * SPrikazuje kontakte u pogledu ListView.
     */
    private void showContacts() {
        // Proverava SDK verziju
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);

        } else {
            // Android verzija je niža od Android 6 i dozvola se automatski dodeljuje
            List<String> contacts = getContactNames();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
            lstNames.setAdapter(adapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Dozvola je potvrđena
                showContacts();
            } else {
                Toast.makeText(this, "Dok ne potvrdite dozvolu, ne mogu da prikažem kontakte", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private List<String> getContactNames() {
        List<String> contacts = new ArrayList<>();
        // Uzima ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        // Pomera kursor na prvog i proverava da li je prazan ili ne
        if (cursor.moveToFirst()) {
            // Iterira kroz kursor
            do {
                // Uzima naziv kontakta
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contacts.add(name);
            } while (cursor.moveToNext());
        }
        // Zatvara kursor
        cursor.close();
        return contacts;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_v07, menu);
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
