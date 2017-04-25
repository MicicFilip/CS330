package com.example.micic.cs330_v04;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Micic on 29-Mar-17.
 */

public class V06Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v06);

        final DbAdapter db = new DbAdapter(this);


        Button addContacts = (Button) findViewById(R.id.addContacts);
        addContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.open();
                long id = db.insertContact("filip", "micic@mail.com");
                id = db.insertContact("pera", "p@gmail.com");
                id = db.insertContact("maja", "m@gmail.com");
                System.out.println(id);
                db.close();
            }
        });

        Button getContacts = (Button) findViewById(R.id.getContacts);
        getContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.open();
                Cursor c = db.getAllContacts();
                if (c.moveToFirst()) {
                    do {
                        DisplayContact(c);
                    } while (c.moveToNext());
                }
                db.close();
            }
        });

        Button getOneContact = (Button) findViewById(R.id.getOneContact);
        getOneContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.open();
                Cursor d = db.getContact(2);
                if (d.moveToFirst()) {
                    DisplayContact(d);
                } else {
                    DisplayToast("Nije pronadjen kontakt");
                    ;
                }
                db.close();
            }
        });

        Button updateContacts = (Button) findViewById(R.id.updateContact);
        updateContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.open();
                if (db.updateContact(2, "Milan", "mil@gmail.com")) {
                    DisplayToast("Uspešno ažuriranje");
                } else {
                    DisplayToast("Greška u ažuriranju");
                }
                db.close();
            }
        });

        try {
            String destPath = "/data/data/" + getPackageName() +
                    "/databases";
            System.out.println(destPath);
            File f = new File(destPath);
            if (!f.exists()) {
                f.mkdirs();
                f.createNewFile();
                //---kopira db iz assets foldera u databases folder---
                CopyDB(getBaseContext().getAssets().open("mydb"),
                        new FileOutputStream(destPath + "/MyDb"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //---preuzimanje svih kontakata---
//        db.open();
//        Cursor c = db.getAllContacts();
//        if (c.moveToFirst())
//        {
//            do {
//                DisplayContact(c);
//            } while (c.moveToNext());
//        }
//        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_v06, menu);
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

    public void CopyDB(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int lenght;
        while ((lenght = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, lenght);
        }
        inputStream.close();
        outputStream.close();
    }

    public void DisplayContact(Cursor c) {
        Toast.makeText(this, "id: " + c.getString(0) + "\n" + "ime: " + c.getString(1) + "\n" + "Email:  " + c.getString(2), Toast.LENGTH_LONG).show();
    }

    private void DisplayToast(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
