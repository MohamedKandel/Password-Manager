package com.Mkandeel.passwordmanager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class tutorial extends AppCompatActivity {

    @BindView(R.id.pager)
    ViewPager2 pager;

    private List<SliderItem> items;
    private State state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        ButterKnife.bind(this);

        state = new State(this);

        getSupportActionBar().hide();
        boolean isDark = state.getState();
        if (isDark) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        items = new ArrayList<>();

        items.add(new SliderItem(R.drawable.welcome));
        items.add(new SliderItem(R.drawable.tutorail1));
        items.add(new SliderItem(R.drawable.tut_2));
        items.add(new SliderItem(R.drawable.theme1));
        items.add(new SliderItem(R.drawable.theme2));
        items.add(new SliderItem(R.drawable.darkmode));
        items.add(new SliderItem(R.drawable.passwordgene));
        items.add(new SliderItem(R.drawable.addnew));
        items.add(new SliderItem(R.drawable.addnew2));
        items.add(new SliderItem(R.drawable.addnew3));
        items.add(new SliderItem(R.drawable.edit_delet));
        items.add(new SliderItem(R.drawable.others1));
        items.add(new SliderItem(R.drawable.others2));

        pager.setAdapter(new ImageAdapter(items,pager));
    }
}