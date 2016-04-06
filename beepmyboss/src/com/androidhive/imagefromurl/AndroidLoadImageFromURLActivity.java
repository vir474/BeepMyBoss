package com.androidhive.imagefromurl;

import com.example.androidhive.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class AndroidLoadImageFromURLActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_post);
        
        // Loader image - will be shown before loading image
        int loader = R.drawable.loader;
        
        // Imageview to show
        ImageView image = (ImageView) findViewById(R.id.imageView1);
        
        // Image url
        String image_url = "http://192.168.1.14/Admin%20Panel%20Design/upload_profile/new_02192013015258_1920x1080179.jpg";
        
        // ImageLoader class instance
        ImageLoader imgLoader = new ImageLoader(getApplicationContext());
        
        // whenever you want to load an image from url
        // call DisplayImage function
        // url - image url to load
        // loader - loader image, will be displayed before getting image
        // image - ImageView 
        imgLoader.DisplayImage(image_url, loader, image);
     
    }
}