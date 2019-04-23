package com.enema.enemaapp.ui.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.enema.enemaapp.R;
import com.google.firebase.database.core.view.View;

public class ReferAndEarnActivity extends AppCompatActivity {

    private Uri dynamicLink = null;
    private static final String TAG = "DynamicLinks";
    private Button share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_and_earn);

        share = findViewById(R.id.share);

    }


    public void getLink(View view) {
        String appCode = "<app_code>";
        final Uri deepLink = Uri.parse("http://example.com/promo?discount");

        String packageName = getApplicationContext().getPackageName();

        // Build the link with all required parameters
        Uri.Builder builder = new Uri.Builder()
                .scheme("https")
                .authority(appCode + ".app.goo.gl")
                .path("/")
                .appendQueryParameter("link", deepLink.toString())
                .appendQueryParameter("apn", packageName);

        dynamicLink = builder.build();
        share.setEnabled(true);
    }

}
