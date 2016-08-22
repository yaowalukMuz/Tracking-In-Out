package com.hitachi_tstv.yodpanom.yaowaluk.trackinginout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private  static final String TAG = "CallCamera";
    private  static final int  CAPTURE_IMAGE_ACTIVITY_REQ = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private  static final int CAMERA_REQUEST = 1888;
    Uri fileUri = null;
    private ImageView photoImage = null;
    private ImageView selectImageInCameraBtn;
    private EditText licenseEditText;
    private Bitmap imageBitmap;


    public void inButtonClick(View view) {
        SynUploadImageTask synUploadImageTask = new SynUploadImageTask(MainActivity.this, imageBitmap);
        synUploadImageTask.execute();
    }

    public void outButtonClick(View view) {

    }


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
            imageBitmap = (Bitmap) extras.get("data");
            photoImage.setImageBitmap(imageBitmap);
        }
    }//end onActivityResult;


    private class SynUploadImageTask extends AsyncTask<Void, Void, String> {

        private Context context;
        private  Bitmap mPhotoBitMap;
        private String mUploadedFileName;
        private UploadImageUtils uploadImageUtils;

        public SynUploadImageTask(Context context, Bitmap mPhotoBitMap) {
            this.context = context;
            this.mPhotoBitMap = mPhotoBitMap;
        }

        @Override
        protected String doInBackground(Void... params) {

            String urlServer = "http://service.eternity.co.th/TrackingInOut/upload.php";
            uploadImageUtils = new UploadImageUtils();
            mUploadedFileName = uploadImageUtils.getRandomFileName();
            final String result = uploadImageUtils.uploadFile(mUploadedFileName, urlServer, mPhotoBitMap);
            Log.d("TAG", "Do in back after save:-->" + result);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Call", "Success");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Add Image Successful!!", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }




}//main class
