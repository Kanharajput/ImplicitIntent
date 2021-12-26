package com.example.implicitintentsender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    // object to get the link from the user
    private EditText msiteEditText;
    private EditText mLocationEditText;
    private EditText mMessageEditText;
    private static final int pic_id = 123;        // requestCode for image
    public static final String ExtraMessage = "com.example.implicitintentsender.extra.Message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();                       // to remove ActionBarN
        setContentView(R.layout.activity_main);
        msiteEditText = findViewById(R.id.url);
        mLocationEditText = findViewById(R.id.loc);
        mMessageEditText = findViewById(R.id.mess);
    }

    // onclick methods
    public void openSite(View view) {
        String url_by_user;     // to store the data which is passed to the screen by the user
        // get the data
        url_by_user = msiteEditText.getText().toString();
        Uri webpage = Uri.parse(url_by_user);
        Intent intent = new Intent(Intent.ACTION_VIEW,webpage);       // action view to view data happing with our data that is webpage

        // check whether there(android device) has an acitivity to handle our intent(action and data both) or not
        String chsr_title = getString(R.string.chooserWeb);
        Intent chooser = Intent.createChooser(intent,chsr_title);         // creating a chooser because by default we are not getting it

        try {
            startActivity(chooser);
        }
        catch(ActivityNotFoundException e) {
            Log.d("ImplicitIntent", "No-one to handle intent");
        }
    }

    public void openLocation(View view) {
        String location;
        location = mLocationEditText.getText().toString();
        Uri adressUri = Uri.parse("geo:0,0?q=" + location);      // query string for general location other option is to use lattitude & longitude
        Intent intent = new Intent(Intent.ACTION_VIEW,adressUri);

        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else {
            Log.d("ImplicitIntent","No-one to handle");
        }
    }

    public void shareMessage(View view) {
        String message;
        message = mMessageEditText.getText().toString();
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(R.string.chooserMessage)
                .setText(message)
                .startChooser();
    }

    public void openCamera(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,pic_id);
    }

    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == pic_id) {
            // BitMap is data structure of image file
            // which stor the image in memory
            Bitmap photo = (Bitmap)data.getExtras().get("data");
            // I don't have place to show image to this layout so we are sending user to next activity
            Intent intent = new Intent(this,SecondActivity.class);
            ByteArrayOutputStream _bs = new ByteArrayOutputStream();                // to convert bitmap to ByteArray
            photo.compress(Bitmap.CompressFormat.JPEG,50,_bs);               // compressing the image  , this same worked for jpg
            intent.putExtra("byteArray",_bs.toByteArray());                  // Bitmap is parcelable so we can directly send it using intent
            startActivity(intent);
        }
    }



}