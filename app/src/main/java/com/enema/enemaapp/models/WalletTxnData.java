package com.enema.enemaapp.models;

public class WalletTxnData {

    String wallet_balance, wallet_txn_amount, wallet_txn_id, wallet_txn_purpose, wallet_txn_status, wallet_txn_timestamp;

    public WalletTxnData(String wallet_balance, String wallet_txn_amount, String wallet_txn_id, String wallet_txn_purpose, String wallet_txn_status, String wallet_txn_timestamp) {
        this.wallet_balance = wallet_balance;
        this.wallet_txn_amount = wallet_txn_amount;
        this.wallet_txn_id = wallet_txn_id;
        this.wallet_txn_purpose = wallet_txn_purpose;
        this.wallet_txn_status = wallet_txn_status;
        this.wallet_txn_timestamp = wallet_txn_timestamp;
    }

    public String getWallet_balance() {
        return wallet_balance;
    }

    public void setWallet_balance(String wallet_balance) {
        this.wallet_balance = wallet_balance;
    }

    public String getWallet_txn_amount() {
        return wallet_txn_amount;
    }

    public void setWallet_txn_amount(String wallet_txn_amount) {
        this.wallet_txn_amount = wallet_txn_amount;
    }

    public String getWallet_txn_id() {
        return wallet_txn_id;
    }

    public void setWallet_txn_id(String wallet_txn_id) {
        this.wallet_txn_id = wallet_txn_id;
    }

    public String getWallet_txn_purpose() {
        return wallet_txn_purpose;
    }

    public void setWallet_txn_purpose(String wallet_txn_purpose) {
        this.wallet_txn_purpose = wallet_txn_purpose;
    }

    public String getWallet_txn_status() {
        return wallet_txn_status;
    }

    public void setWallet_txn_status(String wallet_txn_status) {
        this.wallet_txn_status = wallet_txn_status;
    }

    public String getWallet_txn_timestamp() {
        return wallet_txn_timestamp;
    }

    public void setWallet_txn_timestamp(String wallet_txn_timestamp) {
        this.wallet_txn_timestamp = wallet_txn_timestamp;
    }
}
