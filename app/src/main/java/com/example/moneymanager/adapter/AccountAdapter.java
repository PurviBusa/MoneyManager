package com.example.moneymanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    private List<AccountItem> accounts = new ArrayList<>();

    private OnAccountClickListener clickListener;



    public interface OnAccountClickListener {
        void onAccountClick(AccountItem account);
    }

    public void setOnAccountClickListener(OnAccountClickListener listener) {
        this.clickListener = clickListener;
    }

    public AccountAdapter(List<AccountItem> accounts) {
        if (accounts !=null) {
            this.accounts = accounts;
        }
    }

    public void setSelectedAccount (List<AccountItem> newAccounts) {
        if (newAccounts != null) {
            this.accounts = newAccounts;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_account, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        AccountItem account = accounts.get(position);
        holder.tvAccount.setText(account.getAccountName());

        holder.accountNext.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onAccountClick(account);
            }
        });
    }





    @Override
    public int getItemCount() {
        return accounts != null ? accounts.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAccount;
        View accountNext;

        ViewHolder(View view) {
            super(view);
            tvAccount = view.findViewById(R.id.tvAccount);
            accountNext = view.findViewById(R.id.accountNext);
        }
    }
}
