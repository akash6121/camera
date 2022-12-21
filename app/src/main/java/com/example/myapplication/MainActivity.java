package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {
    ImageView Image_show;
    VideoView Video_watch;
    ActivityResultLauncher<Intent> getResult=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode()== Activity.RESULT_OK){
                    Intent data= result.getData();
//                    Bundle extras = data.getExtras();
//                    String img_data=extras.getString("data");
                    Bitmap img=(Bitmap)(data.getExtras().get("data"));
                    Image_show.setImageBitmap(img);

                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Video_watch=findViewById(R.id.videoView);
        Image_show=findViewById(R.id.imageView);
        Uri uri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.christmas);
        Video_watch.setVideoURI(uri);
        Video_watch.start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.camera) {
            Toast.makeText(this, "You have selected " + item.getTitle(), Toast.LENGTH_SHORT).show();
//           startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            getResult.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
        }
        else if(item.getItemId() == R.id.gallery){
            Toast.makeText(this, "You have selected " + item.getTitle(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MediaStore.ACTION_PICK_IMAGES));
        }
        else if(item.getItemId() == R.id.nothing){
            Toast.makeText(this, "You have selected " + item.getTitle(), Toast.LENGTH_SHORT).show();

        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}