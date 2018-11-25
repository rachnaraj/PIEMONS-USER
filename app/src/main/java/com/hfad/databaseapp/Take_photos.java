package com.hfad.databaseapp;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.provider.MediaStore;
import android.graphics.Bitmap;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import android.util.Base64;


public class Take_photos extends AppCompatActivity {

    static final int CAMERA_REQUEST_CODE = 1;
    private ImageView rachnaImageView;
    private EditText mFileName;
    private Button mbuttonUpload;

    private StorageReference mStorage;
    private ProgressDialog mProgress;



    private Uri mImageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photos);


//        mbuttonTake = (Button) findViewById(R.id.rachna_button);
        rachnaImageView = (ImageView) findViewById(R.id.rachnaImageView);

        mbuttonUpload = (Button) findViewById(R.id.button_upload);
        mFileName = (EditText) findViewById(R.id.give_file_name);

        mStorage = FirebaseStorage.getInstance().getReference();

        mProgress = new ProgressDialog(this);

        mbuttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    Toast.makeText(getApplicationContext(),"stuck before startActivity",Toast.LENGTH_LONG).show();
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                    Toast.makeText(getApplicationContext(),"stuck after2 startActivity",Toast.LENGTH_LONG).show();
                    }
                }
        });
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(getApplicationContext(),"stuck after1222 startActivity",Toast.LENGTH_LONG).show();

        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getApplicationContext(),"stuck after startActivity",Toast.LENGTH_LONG).show();
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK)
        {

//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            rachnaImageView.setImageBitmap(imageBitmap);

            Toast.makeText(getApplicationContext(),"stuck inside startActivity",Toast.LENGTH_LONG).show();
            mProgress.setMessage("Uploading Image.....");
            mProgress.show();
            Uri uri = data.getData();
            StorageReference filepath = mStorage.child("photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mProgress.dismiss();

                Toast.makeText(getApplicationContext(),"Uploading Successful....", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"upload failed...",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
