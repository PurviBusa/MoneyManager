package com.example.moneymanager.models;

import java.util.ArrayList;

public class AccountItem {

    private String accountName;
    private ArrayList<String> account;

    public AccountItem(String accountName, ArrayList<String> account) {
        this.accountName = accountName;
        this.account = account;
    }

    public AccountItem(int i, String cash) {
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public ArrayList<String> getAccount() {
        return account;
    }

    public void setAccount(ArrayList<String> account) {
        this.account = account;
    }
//    private double accountAmount;
//
//    private String accountName;
//
//    private ArrayList<String>accountName;
//
//    public AccountItem(int accountAmount, String cash) {
//    }
//
//    public AccountItem(double accountAmount, ArrayList<String> accountName) {
//        this.accountAmount = accountAmount;
//        this.accountName = accountName;
//    }
//
//    public double getAccountAmount() {
//        return accountAmount;
//    }
//
//    public void setAccountAmount(double accountAmount) {
//        this.accountAmount = accountAmount;
//    }
//
//    public ArrayList<String> getAccountName() {
//        return accountName;
//    }
//
//    public void setAccountName(ArrayList<String> accountName) {
//        this.accountName = accountName;
//    }
}
