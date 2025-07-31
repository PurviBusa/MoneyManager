package com.example.moneymanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.moneymanager.R;
import com.example.moneymanager.models.ScreenItem;

import java.util.List;

public class ViewPagerAdpter extends PagerAdapter {
    TextView headrTxt, headrTxt_2, headrTxt_3, subTxt_2, subTxt_3, subTxt;
    Context context;
    List<ScreenItem> listScreen;


    public ViewPagerAdpter(Context context, List<ScreenItem> listScreen) {
        this.context = context;
        this.listScreen = listScreen;



    }



    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.fragment_intro, null);

        ImageView img = layoutScreen.findViewById(R.id.imgChatImage);
        TextView title = layoutScreen.findViewById(R.id.headrTxt);
        TextView decs = layoutScreen.findViewById(R.id.subTxt);

        title.setText(listScreen.get(position).getTitle());
        decs.setText(listScreen.get(position).getDescription());
        img.setImageResource(listScreen.get(position).getScreenImg());
        container.addView(layoutScreen);


        return layoutScreen;

    }

    @Override
    public int getCount() {
        return listScreen.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View) object);

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


}