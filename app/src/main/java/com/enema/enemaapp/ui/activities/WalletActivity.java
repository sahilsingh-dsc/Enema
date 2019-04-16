package com.enema.enemaapp.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.enema.enemaapp.R;

public class WalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        final TextView txtAddMoney, txtTnx;

        txtAddMoney = findViewById(R.id.txtAddMoney);
        txtTnx = findViewById(R.id.txtTnx);

        txtAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtAddMoney.setBackground(getResources().getDrawable(R.drawable.border_nothing));
                txtAddMoney.setTextColor(getResources().getColor(R.color.colorBlack));
                txtTnx.setBackground(getResources().getDrawable(R.drawable.rect_border_teal));
                txtTnx.setTextColor(getResources().getColor(R.color.colorWhite));

            }
        });

        txtTnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtTnx.setBackground(getResources().getDrawable(R.drawable.border_nothing));
                txtTnx.setTextColor(getResources().getColor(R.color.colorBlack));
                txtAddMoney.setBackground(getResources().getDrawable(R.drawable.rect_border_teal));
                txtAddMoney.setTextColor(getResources().getColor(R.color.colorWhite));

            }
        });

    }
}
