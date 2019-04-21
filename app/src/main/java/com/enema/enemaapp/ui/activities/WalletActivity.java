package com.enema.enemaapp.ui.activities;

import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.enema.enemaapp.R;
import com.enema.enemaapp.ui.fragments.AddMoneyFragment;
import com.enema.enemaapp.ui.fragments.TransactionFragment;

public class WalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        ImageView imgWalletToAccount = findViewById(R.id.imgWalletToAccount);
        imgWalletToAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        final TextView txtAddMoney, txtTnx;

        txtAddMoney = findViewById(R.id.txtAddMoney);
        txtTnx = findViewById(R.id.txtTnx);

        Fragment addMoneyFragment = new AddMoneyFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameWallet, addMoneyFragment).commit();

        txtAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment addMoneyFragment = new AddMoneyFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameWallet, addMoneyFragment).commit();

                txtAddMoney.setBackground(getResources().getDrawable(R.drawable.border_nothing));
                txtAddMoney.setTextColor(getResources().getColor(R.color.colorBlack));
                txtTnx.setBackground(getResources().getDrawable(R.drawable.rect_border_teal));
                txtTnx.setTextColor(getResources().getColor(R.color.colorWhite));

            }
        });

        txtTnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment tranxFragment = new TransactionFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameWallet, tranxFragment).commit();

                txtTnx.setBackground(getResources().getDrawable(R.drawable.border_nothing));
                txtTnx.setTextColor(getResources().getColor(R.color.colorBlack));
                txtAddMoney.setBackground(getResources().getDrawable(R.drawable.rect_border_teal));
                txtAddMoney.setTextColor(getResources().getColor(R.color.colorWhite));

            }
        });

    }
}
