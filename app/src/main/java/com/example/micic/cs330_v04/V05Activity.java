package com.example.micic.cs330_v04;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Micic on 28-Mar-17.
 */

public class V05Activity extends AppCompatActivity {


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_v05, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch (id) {
//            case R.id.back:
//                Intent back = new Intent(this, MainActivity.class);
//                this.startActivity(back);
//                break;
//
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v05);

        ImageView imageTest = (ImageView) findViewById(R.id.imageview1);
        imageTest.setOnCreateContextMenuListener(this);

        imageTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayToast("Kliknuli ste na dugme Save");
            }
        });

    }




    private void DisplayToast(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        CreateMenu(menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return MenuChoice(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        CreateMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuChoice(item);
    }


    public void CreateMenu(Menu menu) {
        menu.setQwertyMode(true);
        MenuItem mnu1 = menu.add(0, 0, 0, "CS101");
        {
            mnu1.setAlphabeticShortcut('a');
            mnu1.setIcon(R.mipmap.ic_launcher);
        }
        MenuItem mnu2 = menu.add(0, 1, 1, "CS102");
        {
            mnu2.setAlphabeticShortcut('b');
            mnu2.setIcon(R.mipmap.ic_launcher);
        }
        MenuItem mnu3 = menu.add(0, 2, 2, "CS330");
        {
            mnu3.setAlphabeticShortcut('c');
            mnu3.setIcon(R.mipmap.ic_launcher);
        }
        MenuItem mnu4 = menu.add(0, 3, 3, "IT355");
        {
            mnu4.setAlphabeticShortcut('d');
        }
        menu.add(0, 4, 4, "IT205");
        menu.add(0, 5, 5, "MAT101");

    }

    private boolean MenuChoice(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(this, "Izabrali ste predmet CS101",
                        Toast.LENGTH_LONG).show();
                return true;
            case 1:
                Toast.makeText(this, "Izabrali ste predmet CS102",
                        Toast.LENGTH_LONG).show();
                return true;
            case 2:
                Toast.makeText(this, "Izabrali ste predmet CS330",
                        Toast.LENGTH_LONG).show();
                return true;
            case 3:
                Toast.makeText(this, "Izabrali ste predmet IT355",
                        Toast.LENGTH_LONG).show();
                return true;
            case 4:
                Toast.makeText(this, "Izabrali ste predmet IT205",
                        Toast.LENGTH_LONG).show();
                return true;
            case 5:
                Toast.makeText(this, "Izabrali ste predmet MAT101",
                        Toast.LENGTH_LONG).show();
                return true;
        }
        return false;
    }

}
