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

import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    private List<AccountItem> accounts;

    private OnAccountClickListener listener;

    public interface OnAccountClickListener {
        void onAccountClick(AccountItem account);
    }

    public void setOnAccountClickListener(OnAccountClickListener listener) {
        this.listener = listener;
    }

    public AccountAdapter(ArrayList<AccountItem> accounts) {
        this.accounts = accounts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dialog_accounts_selection, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountAdapter.ViewHolder holder, int position) {
        AccountItem account = accounts.get(position);
        holder.tvAccount.setText(account.getAccountName());
        holder.accountNext.setVisibility(View.GONE);
        if (!account.getAccountName().isEmpty()) {
            holder.accountNext.setVisibility(View.VISIBLE);
        }

        holder.clAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onAccountClick(account);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvAccount;
        ImageView accountNext;

        ConstraintLayout clAccount;


        ViewHolder(View view) {
            super(view);

            tvAccount = view.findViewById(R.id.tvAccount);
//            accountNext = view.findViewById(R.id.accountNext);
            clAccount = view.findViewById(R.id.clAccount);
        }
    }
}
