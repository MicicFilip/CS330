package com.example.micic.cs330_v04;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
                db.insertContact("filip", "micic@mail.com");
                db.insertContact("pera", "p@gmail.com");
                db.insertContact("maja", "m@gmail.com");
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
            String destPath = "/data/data/" + getPackageName() + "/databases";

            System.out.println(destPath);

            File f = new File(destPath);
            if (!f.exists()) {
                f.mkdirs();
                f.createNewFile();
                CopyDB(getBaseContext().getAssets().open("database"), new FileOutputStream(destPath + "/Database"));


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
