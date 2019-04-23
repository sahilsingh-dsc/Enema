package com.enema.enemaapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.enema.enemaapp.R;
import com.enema.enemaapp.models.WalletTxnData;

import java.util.List;

public class WalletTxnAdapter extends RecyclerView.Adapter<WalletTxnAdapter.WalletTnxViewHolder> {

    private List<WalletTxnData> walletTxnDataList;
    private Context context;

    public WalletTxnAdapter(List<WalletTxnData> walletTxnDataList, Context context) {
        this.walletTxnDataList = walletTxnDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public WalletTxnAdapter.WalletTnxViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wallet_tnx_list_item, viewGroup, false);
        return new WalletTnxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletTxnAdapter.WalletTnxViewHolder walletTnxViewHolder, int i) {

        WalletTxnData walletTxnData = walletTxnDataList.get(i);
        walletTnxViewHolder.txtPurposeOfTxn.setText(walletTxnData.getWallet_txn_purpose());
        walletTnxViewHolder.txtTimeStampOfTxn.setText(walletTxnData.getWallet_txn_timestamp());
        walletTnxViewHolder.txtClosingBalOfTxn.setText(walletTxnData.getWallet_balance());
        walletTnxViewHolder.txtTxnId.setText(walletTxnData.getWallet_txn_id());
        walletTnxViewHolder.txtTxnAmount.setText(walletTxnData.getWallet_txn_amount());
        walletTnxViewHolder.txtTxnStatus.setText(walletTxnData.getWallet_txn_status());
        if (walletTxnData.getWallet_txn_status().equals("Payment was cancelled")) {
            walletTnxViewHolder.txtTxnStatus.setTextColor(Color.RED);
        }else {
            walletTnxViewHolder.txtTxnStatus.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return walletTxnDataList.size();
    }

    public class WalletTnxViewHolder extends RecyclerView.ViewHolder {

        TextView txtPurposeOfTxn, txtTimeStampOfTxn, txtClosingBalOfTxn, txtTxnId, txtTxnAmount, txtTxnStatus;
        ImageView imgTxnType;

        public WalletTnxViewHolder(@NonNull View itemView) {
            super(itemView);

            txtPurposeOfTxn =  itemView.findViewById(R.id.txtPurposeOfTxn);
            txtTimeStampOfTxn =  itemView.findViewById(R.id.txtTimeStampOfTxn);
            txtClosingBalOfTxn =  itemView.findViewById(R.id.txtClosingBalOfTxn);
            txtTxnId =  itemView.findViewById(R.id.txtTxnId);
            txtTxnAmount =  itemView.findViewById(R.id.txtTxnAmount);
            txtTxnStatus =  itemView.findViewById(R.id.txtTxnStatus);
            imgTxnType = itemView.findViewById(R.id.imgTxnType);

        }
    }
}
