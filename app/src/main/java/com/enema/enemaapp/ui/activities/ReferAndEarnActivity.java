package com.enema.enemaapp.ui.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enema.enemaapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;


public class ReferAndEarnActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_and_earn);

        DatabaseReference referRef = FirebaseDatabase.getInstance().getReference("APP_DATA")
                .child("REFERANDEARN");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null){

            user_id = firebaseUser.getUid();
            //Toast.makeText(this, ""+user_id, Toast.LENGTH_SHORT).show();

        }

        referRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String refer_message = (String) dataSnapshot.child("refer_message").getValue();
                TextView txtReferMessage = findViewById(R.id.txtReferMessage);
                txtReferMessage.setText(refer_message);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ImageView imgWhatsappShare, imgFacebookShare, imgTwitterShare, imgSkypeShare;
        imgWhatsappShare = findViewById(R.id.imgWhatsappShare);
        imgFacebookShare = findViewById(R.id.imgFacebookShare);
        imgTwitterShare = findViewById(R.id.imgTwitterShare);
        imgSkypeShare = findViewById(R.id.imgSkypeShare);


        ImageView imgRefToAccount = findViewById(R.id.imgRefToAccount);
        imgRefToAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        imgWhatsappShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = getApplicationContext().getPackageManager();
                try {
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String text = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                        text = "I have an EnEma coupon for you. Sign up with my Referral code"+ Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber()+" to avail the coupon. Download: (EnEma link)";
                    }
                    PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    waIntent.setPackage("com.whatsapp");
                    waIntent.putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(Intent.createChooser(waIntent, "Share with"));

                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(ReferAndEarnActivity.this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                    Intent i = new Intent();
                    i.putExtra(Intent.EXTRA_TEXT, "sharelink");
                    i.setAction(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://www.whatsapp.com/" + urlEncode("sharelink")));
                    startActivity(i);

                }
            }
        });

        imgFacebookShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = getApplicationContext().getPackageManager();
                try {
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String url = "I have an EnEma coupon for you. Sign up with my Referral code XXXXXXX to avail the coupon. Download: (EnEma link)";
                    PackageInfo info = pm.getPackageInfo("com.facebook.katana", PackageManager.GET_META_DATA);
                    waIntent.setPackage("com.facebook.katana");
                    waIntent.putExtra(Intent.EXTRA_TEXT, url);
                    startActivity(Intent.createChooser(waIntent, "Share with"));
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(ReferAndEarnActivity.this, "Facebook not installed",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    i.putExtra(Intent.EXTRA_TEXT, "sharelink");
                    i.setAction(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://www.facebook.com/" + urlEncode("sharelink")));
                    startActivity(i);
                }
            }
        });

        imgTwitterShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTwitter("sharelink");
            }
        });

        imgSkypeShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                try {

                    PackageManager pm = getApplicationContext().getPackageManager();
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String text = "I have an EnEma coupon for you. Sign up with my Referral code XXXXXXX to avail the coupon. Download: (EnEma link)";
                    PackageInfo info=pm.getPackageInfo("com.skype.raider", PackageManager.GET_META_DATA);
                    waIntent.setPackage("com.skype.raider");
                    waIntent.putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(Intent.createChooser(waIntent, "Share with"));

                } catch (Exception e) {
                    Toast.makeText(ReferAndEarnActivity.this, ""+ e,Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    i.putExtra(Intent.EXTRA_TEXT, "sharelink");
                    i.setAction(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://www.skype.com/en/" + urlEncode("sharelink")));
                    startActivity(i);
                }
            }
        });

        String refer_code = null;
        DatabaseReference mobileRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
        mobileRef.child("USER_PROFILE").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String mobile = (String) dataSnapshot.child("user_mobile").getValue();
                TextView txtReferCode = findViewById(R.id.txtReferCode);
                txtReferCode.setText(mobile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void shareTwitter(String message) {
        Intent tweetIntent = new Intent(Intent.ACTION_SEND);
        tweetIntent.putExtra(Intent.EXTRA_TEXT, "I have an EnEma coupon for you. Sign up with my Referral code XXXXXXX to avail the coupon. Download: (EnEma link)");
        tweetIntent.setType("text/plain");
        PackageManager packManager = getApplicationContext().getPackageManager();
        List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);
        boolean resolved = false;
        for (ResolveInfo resolveInfo : resolvedInfoList) {
            if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
                tweetIntent.setClassName(
                        resolveInfo.activityInfo.packageName,
                        resolveInfo.activityInfo.name);
                resolved = true;
                break;
            }
        }
        if (resolved) {
            startActivity(tweetIntent);
        } else {
            Intent i = new Intent();
            i.putExtra(Intent.EXTRA_TEXT, message);
            i.setAction(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://twitter.com/intent/tweet?text=" + urlEncode(message)));
            startActivity(i);
            Toast.makeText(ReferAndEarnActivity.this, "Twitter app isn't found", Toast.LENGTH_LONG).show();
        }
    }

    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(ReferAndEarnActivity.this, ""+e, Toast.LENGTH_SHORT).show();
            return "";
        }
    }

}
