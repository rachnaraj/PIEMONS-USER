package com.hfad.databaseapp;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class uploadActivity extends AppCompatActivity {
    public static final int PICK_IMAGE_CHOOSER = 1;
    private static final int PICK_IMAGE_REQUEST = 1;
    // declaring variables for taking longitude and latitude..
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;
//    private TextView mUploadText;
    TextView txtLat;
    String lat;
    String provider;
    private Button mbuttonUpload;
    private Button mbuttonChoose;
    private EditText mFileName;
    private ImageView mImageChoose;
    private ProgressBar mProgressBar;
    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef, rootRef, demoRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        // taking id value for location..
        txtLat = (TextView) findViewById(R.id.textview1);

        mbuttonChoose = (Button) findViewById(R.id.button_choose_image);
        mbuttonUpload = (Button) findViewById(R.id.button_upload);
        mFileName = (EditText) findViewById(R.id.give_file_name);
        mImageChoose = (ImageView) findViewById(R.id.imageUpload);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar2);
//        mUploadText = (TextView) findViewById(R.id.text_view_show_uploads);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // return;
        }
        // locationManager.requestLocationUpdates//(LocationManager.GPS_PROVIDER, 0, 0, this);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mbuttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });


        mbuttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
//                //database reference pointing to root of database
//                rootRef = FirebaseDatabase.getInstance().getReference();
//                //database reference pointing to demo node
//                demoRef = rootRef.child("demo");
//			 String value = txtLat.getText().toString();
//                //push creates a unique id in database
//                demoRef.push().setValue(value);

            }
        });

//        mUploadText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//// need to add logic for showing uploads...
//            }
//        });

    }

    //defining methods for getting location...
//	@Override
//	public void onLocationChanged(Location location) {
//	txtLat = (TextView) findViewById(R.id.textview1);
//	txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
//	}
//
//	@Override
//	public void onProviderDisabled(String provider) {
//	Log.d("Latitude","disable");
//	}
//
//	@Override
//	public void onProviderEnabled(String provider) {
//	Log.d("Latitude","enable");
//	}
//
//	@Override
//	public void onStatusChanged(String provider, int status, Bundle extras) {
//	Log.d("Latitude","status");
//	}


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }

    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Handler handler = new Handler();

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 5000);

                            Toast.makeText(getApplicationContext(), "upload successful", Toast.LENGTH_LONG).show();

                            UploadTo upload = new UploadTo(mFileName.getText().toString().trim(),
                                    taskSnapshot.getDownloadUrl().toString());
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);


                        }


                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);

                        }
                    });

        } else {
            Toast.makeText(this, "no file selected", Toast.LENGTH_SHORT).show();
        }
    }

    //**********************************************************************
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_CHOOSER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(mImageChoose);


        }
    }
}
