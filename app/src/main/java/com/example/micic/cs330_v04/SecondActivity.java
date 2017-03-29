package com.example.micic.cs330_v04;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;


/**
 * Created by Micic on 22-Mar-17.
 */

public class SecondActivity extends AppCompatActivity implements ViewFactory {


    public void theThirdPage(View v){
        Intent treciPage = new Intent(this, V05Activity.class);
        startActivity(treciPage);
    }

    // Action Bar na Main strani
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        WebView wv = (WebView) findViewById(R.id.webview1);
        WebSettings webSettings = wv.getSettings();
        switch (id) {
            case R.id.back:
                Intent back = new Intent(this, MainActivity.class);
                this.startActivity(back);
                break;
            case R.id.uni:
                webSettings.setBuiltInZoomControls(true);
                wv.setWebViewClient(new Callback());
                wv.loadUrl("https://www.metropolitan.ac.rs");
                break;
            case R.id.fit:
                webSettings.setBuiltInZoomControls(true);
                wv.setWebViewClient(new Callback());
                wv.loadUrl("http://www.metropolitan.ac.rs/osnovne-studije/fakultet-informacionih-tehnologija/");
                break;
            case R.id.fdu:
                webSettings.setBuiltInZoomControls(true);
                wv.setWebViewClient(new Callback());
                wv.loadUrl("http://www.metropolitan.ac.rs/fakultet-digitalnih-umetnosti-2/");
                break;
            case R.id.fam:
                webSettings.setBuiltInZoomControls(true);
                wv.setWebViewClient(new Callback());
                wv.loadUrl("http://www.metropolitan.ac.rs/osnovne-studije/fakultet-za-menadzment/");
                break;


        }
        return super.onOptionsItemSelected(item);
    }




    //---slike za prikazivanje---
    Integer[] imageIDs = {
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4

    };

    private ImageSwitcher imageSwitcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);




//        WebView wv = (WebView) findViewById(R.id.webview1);
//        WebSettings webSettings = wv.getSettings();
//        webSettings.setBuiltInZoomControls(true);
//        wv.setWebViewClient(new Callback());
//        wv.loadUrl("https://www.metropolitan.ac.rs");

        Button btn = (Button) findViewById(R.id.btn1);
        btn.setOnCreateContextMenuListener(this);

        imageSwitcher = (ImageSwitcher) findViewById(R.id.switcher1);
        imageSwitcher.setFactory(this);

        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.slide_in_left));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.slide_out_right));
        Gallery gallery = (Gallery) findViewById(R.id.gallery1);
        gallery.setAdapter(new ImageAdapter(this));
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                imageSwitcher.setImageResource(imageIDs[position]);
            }
        });


    }



    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return (false);
        }
    }

    public View makeView() {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundColor(0xFF000000);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new
                ImageSwitcher.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
        return imageView;
    }

    public class ImageAdapter extends BaseAdapter {
        Context context;
        int itemBackground;

        public ImageAdapter(Context c) {
            context = c;
//---podešava stil---
            TypedArray a = obtainStyledAttributes(R.styleable.Gallery1);
            itemBackground = a.getResourceId(
                    R.styleable.Gallery1_android_galleryItemBackground, 0);
            a.recycle();
        }

        //---vraća broj slika---
        public int getCount() {
            return imageIDs.length;
        }

        //---vraća stavku---
        public Object getItem(int position) {
            return position;
        }

        //---vraća ID of stavke---
        public long getItemId(int position) {
            return position;
        }

        //---vraća ImageView pogled---
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setImageResource(imageIDs[position]);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new Gallery.LayoutParams(150, 120));
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setBackgroundResource(itemBackground);
            return imageView;
        }
    }

    // STARI MENU

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        CreateMenu(menu);
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        return MenuChoice(item);
//    }
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        CreateMenu(menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return MenuChoice(item);
//    }
//
//
//    public void CreateMenu(Menu menu) {
//        menu.setQwertyMode(true);
//        MenuItem mnu1 = menu.add(0, 0, 0, "CS101");
//        {
//            mnu1.setAlphabeticShortcut('a');
//            mnu1.setIcon(R.mipmap.ic_launcher);
//        }
//        MenuItem mnu2 = menu.add(0, 1, 1, "CS102");
//        {
//            mnu2.setAlphabeticShortcut('b');
//            mnu2.setIcon(R.mipmap.ic_launcher);
//        }
//        MenuItem mnu3 = menu.add(0, 2, 2, "CS330");
//        {
//            mnu3.setAlphabeticShortcut('c');
//            mnu3.setIcon(R.mipmap.ic_launcher);
//        }
//        MenuItem mnu4 = menu.add(0, 3, 3, "IT355");
//        {
//            mnu4.setAlphabeticShortcut('d');
//        }
//        menu.add(0, 4, 4, "IT205");
//        menu.add(0, 5, 5, "MAT101");
//        menu.add(0, 6, 6, "CS103");
//        menu.add(0, 7, 7, "Domaci");
//    }
//
//    private boolean MenuChoice(MenuItem item) {
//        switch (item.getItemId()) {
//            case 0:
//                Toast.makeText(this, "Izabrali ste predmet CS101",
//                        Toast.LENGTH_LONG).show();
//                return true;
//            case 1:
//                Toast.makeText(this, "Izabrali ste predmet CS102",
//                        Toast.LENGTH_LONG).show();
//                return true;
//            case 2:
//                Toast.makeText(this, "Izabrali ste predmet CS330",
//                        Toast.LENGTH_LONG).show();
//                return true;
//            case 3:
//                Toast.makeText(this, "Izabrali ste predmet IT355",
//                        Toast.LENGTH_LONG).show();
//                return true;
//            case 4:
//                Toast.makeText(this, "Izabrali ste predmet IT205",
//                        Toast.LENGTH_LONG).show();
//                return true;
//            case 5:
//                Toast.makeText(this, "Izabrali ste predmet MAT101",
//                        Toast.LENGTH_LONG).show();
//                return true;
//            case 6:
//                Toast.makeText(this, "Izabrali ste predmet CS103",
//                        Toast.LENGTH_LONG).show();
//                return true;
//            case 7:
//                Toast.makeText(this, "bla bla bla",
//                        Toast.LENGTH_LONG).show();
//                return true;
//        }
//        return false;
//    }
}
