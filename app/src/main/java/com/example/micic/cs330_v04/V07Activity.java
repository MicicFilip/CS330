package com.example.micic.cs330_v04;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

/**
 * Created by Micic on 12-Apr-17.
 */

public class V07Activity extends AppCompatActivity {

    private ListView lstNames;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v07);


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
