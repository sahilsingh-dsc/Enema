package com.enema.enemaapp.ui.fragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.enema.enemaapp.R;
import com.enema.enemaapp.adapters.WalletTxnAdapter;
import com.enema.enemaapp.models.WalletTxnData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class TransactionFragment extends Fragment {

    View view;
    private List<WalletTxnData> walletTxnDataList;
    private AlertDialog loadingDialog;
    TextView txtNoTnx;

    public TransactionFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transaction, container, false);


        txtNoTnx = view.findViewById(R.id.txtNoTnx);

        loadingDialog = new SpotsDialog.Builder().setContext(getContext())
                .setTheme(R.style.loading)
                .setMessage("Please Wait")
                .setCancelable(false)
                .build();

        walletTxnDataList = new ArrayList<>();
        walletTxnDataList.clear();
        getAllTxn();

        return view;
    }

    private void getAllTxn(){
        loadingDialog.show();
        final RecyclerView recyclerTxn = view.findViewById(R.id.recyclerTxn);
        final RecyclerView.Adapter[] txnAdapter = new RecyclerView.Adapter[1];
        recyclerTxn.hasFixedSize();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerTxn.setLayoutManager(mLayoutManager);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        final String username = firebaseUser.getUid();
        final String user_mobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        DatabaseReference walletRef = FirebaseDatabase.getInstance().getReference("USER_DATA");
        assert user_mobile != null;
        walletRef.child(user_mobile).child(username)
                .child("WALLET_DATA")
                .child("PREVIOUS_TNX_DATA")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loadingDialog.dismiss();
                if (dataSnapshot.getChildrenCount() == 0){
                    recyclerTxn.setVisibility(View.GONE);
                    txtNoTnx.setVisibility(View.VISIBLE);
                } else {
                    recyclerTxn.setVisibility(View.VISIBLE);
                    txtNoTnx.setVisibility(View.GONE);
                }

                walletTxnDataList.clear();
                for (DataSnapshot txnSnap : dataSnapshot.getChildren()){
                        String wallet_balance = (String) txnSnap.child("wallet_balance").getValue();
                        String wallet_txn_amount = (String) txnSnap.child("wallet_txn_amount").getValue();
                        String wallet_txn_id = (String) txnSnap.child("wallet_txn_id").getValue();
                        String wallet_txn_purpose = (String) txnSnap.child("wallet_txn_purpose").getValue();
                        String wallet_txn_status = (String) txnSnap.child("wallet_txn_status").getValue();
                        String wallet_txn_timestamp = (String) txnSnap.child("wallet_txn_timestamp").getValue();
                        loadingDialog.dismiss();

                        WalletTxnData walletTxnData = new WalletTxnData
                                (wallet_balance,
                                wallet_txn_amount,
                                wallet_txn_id,
                                wallet_txn_purpose,
                                wallet_txn_status,
                                wallet_txn_timestamp);

                    walletTxnDataList.add(walletTxnData);
                        loadingDialog.dismiss();
                    }

                    txnAdapter[0] = new WalletTxnAdapter(walletTxnDataList, getContext());
                    recyclerTxn.setAdapter(txnAdapter[0]);
                    txnAdapter[0].notifyDataSetChanged();
                    loadingDialog.dismiss();
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadingDialog.dismiss();
            }
        });

    }

}
