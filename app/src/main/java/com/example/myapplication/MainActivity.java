package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    ImageView Image_show;
    VideoView Video_watch;
    Uri Image_uri;
    ActivityResultLauncher<Uri> getResult=registerForActivityResult(new ActivityResultContracts.TakePicture(),
            result -> {
                if(result){
                    Image_show.setImageURI(null);
                    Image_show.setImageURI(Image_uri);
                }
            }
    );
    ActivityResultLauncher<String> getGallery=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            Image_show.setImageURI(result);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Video_watch=findViewById(R.id.videoView);
        Image_show=findViewById(R.id.imageView);
        Image_uri=createUri();
        Uri uri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.christmas);
        Video_watch.setVideoURI(uri);
        Video_watch.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Video_watch.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Video_watch.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.camera) {
            Toast.makeText(this, "You have selected " + item.getTitle(), Toast.LENGTH_SHORT).show();
//           startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            getResult.launch(Image_uri);
        }
        else if(item.getItemId() == R.id.gallery){
            Toast.makeText(this, "You have selected " + item.getTitle(), Toast.LENGTH_SHORT).show();
            getGallery.launch("image/*");
        }
        else if(item.getItemId() == R.id.nothing){
            Toast.makeText(this, "You have selected " + item.getTitle(), Toast.LENGTH_SHORT).show();

        }
        return true;
    }
    private Uri createUri(){
        File imgfile= new File(getApplicationContext().getFilesDir(),"camera_photo.png");
        return FileProvider.getUriForFile(
                getApplicationContext(),
                "com.example.myapplication.provider",
                imgfile
        );
    }
}