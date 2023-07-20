package com.Mkandeel.passwordmanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Facebook extends AppCompatActivity {

    private ArrayList<String> Data;
    private ArrayList<String> mails;
    private ArrayList<String> passwords;
    private ArrayList<Integer> imgs;
    private ArrayList<String> types;
    private DBConnection connection;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton fab;
    @BindView(R.id.imageView3)
    ImageView Imgs;

    private String type;
    private String mtype;
    private String Otype;
    private RecycleView adapter;
    private RecycleViewOthers Oadapter;

    private Functions functions;
    private State state;

    private  String arr[] = {"Facebook","Instagram","Twitter", "Google", "Linked In","Amazon" , "Yahoo",
            "Pinterest", "Netflix", "Crunchyroll"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        ButterKnife.bind(this);
        mails = new ArrayList<>();
        passwords = new ArrayList<>();
        imgs = new ArrayList<>();
        types = new ArrayList<>();

        functions = new Functions(this);
        state = new State(this);
        connection = new DBConnection(this);

        boolean isDark = state.getState();

        type = getIntent().getStringExtra("type");

        if (Arrays.asList(arr).contains(type)) {

            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            TextView txt_title = new TextView(this);
            functions.change_fonts("Segoe_UIb.ttf", txt_title);
            txt_title.setText(type);
            androidx.appcompat.app.ActionBar.LayoutParams params = new androidx.appcompat.app.ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            txt_title.setTextSize(23);
            txt_title.setTextColor(Color.WHITE);
            getSupportActionBar().setCustomView(txt_title, params);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            switch (type) {
                case "Facebook":
                    mtype = "Facebook";
                    Data = connection.Get_Accounts(mtype);
                    Imgs.setImageResource(R.drawable.face);
                    break;
                case "Instagram":
                    mtype = "Ig";
                    Data = connection.Get_Accounts(mtype);
                    Imgs.setImageResource(R.drawable.ig);
                    break;
                case "Twitter":
                    mtype = "Twitter";
                    Data = connection.Get_Accounts(mtype);
                    Imgs.setImageResource(R.drawable.twittter);
                    break;
                case "Google":
                    mtype = "Google";
                    Data = connection.Get_Accounts(mtype);
                    Imgs.setImageResource(R.drawable.google);
                    break;
                case "Linked In":
                    mtype = "Linked_In";
                    Data = connection.Get_Accounts(mtype);
                    Imgs.setImageResource(R.drawable.linkedin);
                    break;
                case "Amazon":
                    mtype = "Amazon";
                    Data = connection.Get_Accounts(mtype);
                    if (isDark) {
                        Imgs.setImageResource(R.drawable.amazon_dark);
                    } else {
                        Imgs.setImageResource(R.drawable.amazon_light);
                    }
                    break;
                case "Yahoo":
                    mtype = "Yahoo";
                    Data = connection.Get_Accounts(mtype);
                    Imgs.setImageResource(R.drawable.yahoo);
                    break;
                case "Pinterest":
                    mtype = "Pint";
                    Data = connection.Get_Accounts(mtype);
                    Imgs.setImageResource(R.drawable.pint);
                    break;
                case "Netflix":
                    mtype = "Netflix";
                    Data = connection.Get_Accounts(mtype);
                    Imgs.setImageResource(R.drawable.netfilex);
                    break;
                case "Crunchyroll":
                    mtype = "Roll";
                    Data = connection.Get_Accounts(mtype);
                    Imgs.setImageResource(R.drawable.roll);
                    break;
            }

            Populate_Data(isDark);
            buildRecycleView(false);

        } else {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            TextView txt_title = new TextView(this);
            functions.change_fonts("Segoe_UIb.ttf", txt_title);
            txt_title.setText("Others");
            androidx.appcompat.app.ActionBar.LayoutParams params = new androidx.appcompat.app.ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            txt_title.setTextSize(23);
            txt_title.setTextColor(Color.WHITE);
            getSupportActionBar().setCustomView(txt_title, params);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            Data = connection.Get_Others_Accounts();
            Imgs.setImageResource(R.drawable.other);
            Populate_Others_Data(isDark);
            buildRecycleView(true);
        }

    }

    private void buildRecycleView(boolean Others) {
        if (Others) {
            Oadapter = new RecycleViewOthers(this,mails,passwords,imgs,types,Otype);
            RecyclerView.LayoutManager lm = new LinearLayoutManager(this);

            rv.setLayoutManager(lm);
            rv.setHasFixedSize(true);
            rv.setAdapter(Oadapter);
        } else {
            adapter = new RecycleView(this, mails, passwords, imgs, mtype);
            RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
            rv.setLayoutManager(lm);
            rv.setHasFixedSize(true);
            rv.setAdapter(adapter);
        }
    }

    private void Populate_Data(boolean isDark) {
        if (isDark) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            for (int i = 1; i < Data.size(); i++) {
                String account = Data.get(i);
                String arr[] = account.split("\n");
                mails.add(arr[0]);
                passwords.add(arr[1]);
                imgs.add(R.drawable.eye_dark);
            }
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            for (int i = 1; i < Data.size(); i++) {
                String account = Data.get(i);
                String arr[] = account.split("\n");
                mails.add(arr[0]);
                passwords.add(arr[1]);
                imgs.add(R.drawable.eye_normal);
            }
        }
    }


    private void Populate_Others_Data(boolean isDark) {
        if (isDark) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            for (int i = 1; i < Data.size(); i++) {
                String account = Data.get(i);
                String arr[] = account.split("\n");
                mails.add(arr[0]);
                passwords.add(arr[1]);
                types.add(arr[2]);
                imgs.add(R.drawable.eye_dark);
            }
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            for (int i = 1; i < Data.size(); i++) {
                String account = Data.get(i);
                String arr[] = account.split("\n");
                mails.add(arr[0]);
                passwords.add(arr[1]);
                types.add(arr[2]);
                imgs.add(R.drawable.eye_normal);
            }
        }
    }

    @OnClick(R.id.floatingActionButton)
    public void onViewClicked() {
        //Intent mIntent = getIntent();
        Dialog dialog = new Dialog(Facebook.this);
        boolean isDark = state.getState();

        if (Arrays.asList(arr).contains(type)) {
            if (isDark) {
                dialog.setContentView(R.layout.dialog_dark);
            } else {
                dialog.setContentView(R.layout.dialog);
            }
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(true);

            TextView TextView1 = dialog.findViewById(R.id.TextMail);
            TextView TextView2 = dialog.findViewById(R.id.TextPass);
            TextView TextView3 = dialog.findViewById(R.id.txt_Dtitle);
            EditText txt_mail = dialog.findViewById(R.id.txt_mail);
            EditText txt_pass = dialog.findViewById(R.id.txt_pass);
            Button btn = dialog.findViewById(R.id.Add_new);

            functions.change_fonts("Segoe_UIb.ttf", TextView3);
            functions.change_fonts("Segoe_UI.ttf", TextView1, TextView2);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mail = txt_mail.getText().toString();
                    String pass = txt_pass.getText().toString();
                    if (mail.trim().equals("") || pass.trim().equals("")) {
                        functions.Make_Toast(Facebook.this, "Please fill all required fields");
                    } else {
                        int ID = connection.Get_Next_ID();
                        String mType = "";

                        switch (type) {
                            case "Facebook":
                                mType = "Facebook";
                                break;
                            case "Instagram":
                                mType = "Ig";
                                break;
                            case "Twitter":
                                mType = "Twitter";
                                break;
                            case "Google":
                                mType = "Google";
                                break;
                            case "Linked In":
                                mType = "Linked_In";
                                break;
                            case "Amazon":
                                mType = "Amazon";
                                break;
                            case "Yahoo":
                                mType = "Yahoo";
                                break;
                            case "Pinterest":
                                mType = "Pint";
                                break;
                            case "Netflix":
                                mType = "Netflix";
                                break;
                            case "Crunchyroll":
                                mType = "Roll";
                                break;
                        }
                        connection.InsertAccount(ID, mail, pass, mType, 1);
                        dialog.cancel();
                        mails.add(mail);
                        passwords.add(pass);
                        if (isDark) {
                            imgs.add(R.drawable.eye_dark);
                        } else {
                            imgs.add(R.drawable.eye_normal);
                        }
                        types.add(mType);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
            dialog.show();

        } else {
            if (isDark) {
                dialog.setContentView(R.layout.dialog_others_dark);
            } else {
                dialog.setContentView(R.layout.dialog_others);
            }
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(true);

            TextView TextView1 = dialog.findViewById(R.id.TextMail);
            TextView TextView2 = dialog.findViewById(R.id.TextPass);
            TextView TextView3 = dialog.findViewById(R.id.txt_Dtitle);
            TextView TextView4 = dialog.findViewById(R.id.TextType);

            EditText txt_type = dialog.findViewById(R.id.txt_type);
            EditText txt_mail = dialog.findViewById(R.id.txt_mail);
            EditText txt_pass = dialog.findViewById(R.id.txt_pass);

            Button btn = dialog.findViewById(R.id.Add_new);

            functions.change_fonts("Segoe_UIb.ttf", TextView3);
            functions.change_fonts("Segoe_UI.ttf", TextView1, TextView2,TextView4);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mtype = txt_type.getText().toString();
                    String mail = txt_mail.getText().toString();
                    String pass = txt_pass.getText().toString();
                    if(mtype.trim().equals("") || mail.trim().equals("") ||
                            pass.trim().equals("")) {
                        functions.Make_Toast(Facebook.this,"Please fill all required fields");
                    } else {
                        int ID = connection.Get_NextOthers_ID();
                        Otype = mtype;
                        connection.InsertOther(ID,mail,pass,mtype,1);
                        dialog.cancel();

                        mails.add(mail);
                        passwords.add(pass);
                        types.add(mtype);
                        if(isDark) {
                            imgs.add(R.drawable.eye_dark);
                        } else {
                            imgs.add(R.drawable.eye_normal);
                        }
                        Oadapter.notifyDataSetChanged();
                    }
                }
            });
            dialog.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                MoveToListActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MoveToListActivity();
    }

    private void MoveToListActivity() {
        String name = getIntent().getStringExtra("name");
        Intent intent = new Intent(Facebook.this,Account_List.class);
        intent.putExtra("name",name);
        startActivity(intent);
        this.finish();
    }

}