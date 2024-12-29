package com.example.myloginapp;




import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.DirectAction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class camera extends AppCompatActivity {


private static final int REUEST_CODE=22;






    Button btnpicture;

    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        btnpicture = findViewById(R.id.btncamera_id);
        imageView = findViewById(R.id.imageview1);
        Button shareImage=findViewById(R.id.btn_share);

        shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable= (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap=bitmapDrawable.getBitmap();
                shareImageAndText(bitmap);
            }
        });





        btnpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,REUEST_CODE);
            }


        });
    }

    private void shareImageAndText(Bitmap bitmap) {

        Uri uri=getImageToShare(bitmap);
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM,uri);
        intent.putExtra(Intent.EXTRA_TEXT,"Image Text");
        intent.putExtra(Intent.EXTRA_SUBJECT,"Image Subject");
        intent.setType("image/*");
        startActivity(Intent.createChooser(intent,"Share Via"));


    }

    private Uri getImageToShare(Bitmap bitmap) {


        File folder=new File(getCacheDir(),"image");
        Uri uri=null;
        try{
        folder.mkdirs();
        File file=new File(folder,"image.jpg");

            FileOutputStream fileOutputStream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,90,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            uri = FileProvider.getUriForFile(this,"com.example.myloginapp",file);

        } catch (Exception e) {
            throw new RuntimeException(e);
            //Toast.makeText(camera.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        return uri;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if(requestCode == REUEST_CODE && resultCode == RESULT_OK){
           Bitmap photo=(Bitmap) data.getExtras().get("data");
           imageView.setImageBitmap(photo);
       }
       else {

           Toast.makeText(this,"Cancelled",Toast.LENGTH_SHORT).show();
           super.onActivityResult(requestCode, resultCode, data);
       }
    }


    }




