package com.example.moneymanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.models.AccountItem;
import com.example.moneymanager.models.CategoryItem;

import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    private List<AccountItem> accounts;

    private OnAccountClickListener clickListener;


    public interface OnAccountClickListener {
        void onAccountClick(AccountItem account);
    }

    public void setOnAccountClickListener(OnAccountClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public AccountAdapter(ArrayList<AccountItem> accounts) {
        if (accounts != null) {
            this.accounts = accounts;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_account, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AccountItem account = accounts.get(position);
        holder.tvAccount.setText(account.getAccountName());
        if (!account.getAccountName().isEmpty())


            holder.cl_Account.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onAccountClick(account);
                }
            });
    }


    @Override
    public int getItemCount() {
        return accounts.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAccount;
        RelativeLayout cl_Account;

        ViewHolder(View view) {
            super(view);
            tvAccount = view.findViewById(R.id.tvAccount);
            cl_Account = view.findViewById(R.id.cl_Account);
        }
    }
}
