package com.hitachi_tstv.yodpanom.yaowaluk.trackinginout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private  static final String TAG = "CallCamera";
    private  static final int  CAPTURE_IMAGE_ACTIVITY_REQ = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private  static final int CAMERA_REQUEST = 1888;
    Uri fileUri = null;
    ImageView photoImage = null;

    private File getOutputPhotoFile() {

        File directory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), getPackageName());

        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                Log.e(TAG, "Failed to create storage directory.");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.US).format(new Date());

        return new File(directory.getPath() + File.separator + "IMG_"
                + timeStamp + ".jpg");
    }


    private ImageView selectImageInCameraBtn;
    private EditText licenseEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /// Bind widget
        photoImage = (ImageView) findViewById(R.id.imageView);
        selectImageInCameraBtn = (ImageView) findViewById(R.id.selectImageInCameraBtn);
        licenseEditText = (EditText) findViewById(R.id.editText);


        selectImageInCameraBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Failed to onclike");
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(i.resolveActivity(getPackageManager())!= null){
                    startActivityForResult(i, REQUEST_IMAGE_CAPTURE );
                }
            }

        });

    }//main method

    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            photoImage.setImageBitmap(imageBitmap);


//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            photoImage.setImageBitmap(photo);
//            if (resultCode == RESULT_OK) {
//                Uri photoUri = null;
//                if (data == null) {
//                    Toast.makeText(this, "Image save successfully", Toast.LENGTH_LONG).show();
//                    photoUri = fileUri;
//                } else {
//                    photoUri = data.getData();
//                    Log.e(TAG, "image::::::" +photoUri);
//                    Toast.makeText(this,"Callout for image capture failed!",Toast.LENGTH_LONG).show();
//                }
//                showPhoto(photoUri.getPath());
//            } else if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Callout for image capture failed!",
//                        Toast.LENGTH_LONG).show();
//            }
        }
    }//end onActivityResult;

    private  void showPhoto(String photoUri) {
        File imageFile = new File(photoUri);

        if (imageFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            BitmapDrawable drawable = new BitmapDrawable(this.getResources(), bitmap);
            photoImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            photoImage.setImageDrawable(drawable);
    }
}






}//main class
