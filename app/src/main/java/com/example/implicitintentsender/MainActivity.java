package com.example.implicitintentsender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    // object to get the link from the user
    private EditText msiteEditText;
    private EditText mLocationEditText;
    private EditText mMessageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if(intent.resolveActivity(getPackageManager()) != null) {      // package manager find activity satisifying our intent
            startActivity(intent);
        }
        else {
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
}