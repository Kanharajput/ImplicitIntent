package com.example.implicitintentsender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mUrlTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();

        Uri uri = intent.getData();
        if(uri != null) {
            String uri_String = getString(R.string.urlLabel) + uri.toString();
            mUrlTextView = findViewById(R.id.txt_here);
            mUrlTextView.setText(uri_String);
        }
    }
}