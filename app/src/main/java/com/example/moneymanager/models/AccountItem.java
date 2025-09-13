package com.example.moneymanager.models;

public class AccountItem {

    private String accountName;

    public AccountItem(String accountName){
        this.accountName = accountName;


    }
    public  String getAccountName(){
        return accountName;

    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


}
