package com.example.moneymanager.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.adapter.AccountAdapter;
import com.example.moneymanager.adapter.CategoryRecyclerAdapter;
import com.example.moneymanager.models.AccountItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class AccountSelectionDialog extends BottomSheetDialogFragment implements AccountAdapter.OnAccountClickListener {

    RecyclerView account_recycler;

    private ArrayList<AccountItem> accountList = new ArrayList<>();

    private AccountAdapter accountAdapter;

    public OnselectAccountClickListner onselectAccountClickListner;

    public void setOnSelectAccountClickListener(OnselectAccountClickListner listner){
        this.onselectAccountClickListner = listner;

    }

   public interface OnselectAccountClickListner{
       void onSelectAccount(AccountItem account);

   }

   public AccountSelectionDialog(ArrayList<AccountItem>accountList){
        this.accountList.clear();
        this.accountList.addAll(accountList);

   }

   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.dialog_accounts_selection, container, false);
       account_recycler = view.findViewById(R.id.account_recycler);

       return view;
   }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        accountAdapter = new AccountAdapter(accountList);
        account_recycler.setAdapter(accountAdapter);
        accountAdapter.setOnAccountClickListener(this);
    }

    public void updateAccountList(ArrayList<AccountItem>accountList){
        this.accountList.clear();
        this.accountList.addAll(accountList);
        accountAdapter.notifyDataSetChanged();
    }
    @Override
    public void onAccountClick(AccountItem account) {
        accountAdapter = new AccountAdapter(accountList);
        account_recycler.setAdapter(accountAdapter);
        accountAdapter.setOnAccountClickListener(this);

        if (account.getAccountName().isEmpty()){
            onselectAccountClickListner.onSelectAccount(account);
            dismiss();
        }

    }
}
