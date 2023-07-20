package com.Mkandeel.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.imageView)
    ImageView img;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.txt_username)
    TextView txt_username;
    @BindView(R.id.txt_user)
    EditText txt_user;
    @BindView(R.id.txt_password)
    TextView txt_password;
    @BindView(R.id.txt_pass)
    EditText txt_pass;
    @BindView(R.id.txt_phone_number)
    TextView txt_phone_number;
    @BindView(R.id.txt_phone)
    EditText txt_phone;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.swch)
    Switch swit;
    @BindView(R.id.txtView_question)
    TextView txtView_question;
    @BindView(R.id.spn)
    Spinner spn;
    @BindView(R.id.txt_answer)
    EditText txt_answer;
    @BindView(R.id.textView14)
    TextView txt_login;

    private DBConnection connection;
    private Functions functions;
    private State state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        functions = new Functions(this);
        state = new State(this);
        connection = new DBConnection(this);
        img.setImageResource(R.drawable.logo);


        ArrayList<String> users = connection.Get_Users();
        if (users.size() == 0) {
            functions.change_fonts("Segoe_UIb.ttf", textView,txt_login);
            functions.change_fonts("Segoe_UI.ttf", txt_username, txt_password, txt_phone_number, txtView_question);
            functions.Change_Color("black", textView, txt_username, txt_password, txt_phone_number, txtView_question);

            swit = findViewById(R.id.swch);

            swit.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    functions.Change_Color("white", textView, txt_username, txt_password, txt_phone_number, txtView_question);
                    functions.Change_Hint_Color("dark", txt_user, txt_pass, txt_phone, txt_answer);
                } else {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    functions.Change_Color("black", textView, txt_username, txt_password, txt_phone_number, txtView_question);
                    functions.Change_Hint_Color("light", txt_user, txt_pass, txt_phone, txt_answer);
                }
            });
            txt_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (swit.isChecked()) {
                        state.setState(true);
                    } else {
                        state.setState(false);
                    }

                    Intent intent = new Intent(MainActivity.this,Login.class);
                    startActivity(intent);
                    finish();
                }
            });

        } else {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    @Optional
    @OnClick({R.id.btn})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn:
                boolean flag = functions.isEmpty(txt_user, txt_pass, txt_phone);
                if (flag) {
                    functions.Make_Toast(getBaseContext(), "fill all field");
                    functions.Clear(txt_user, txt_pass, txt_phone, txt_answer);
                } else {
                    if (txt_phone.getText().toString().trim().length() < 10) {
                        functions.Make_Toast(getBaseContext(), "Enter a valid phone number");
                        txt_phone.requestFocus();
                    } else {
                        if (swit.isChecked()) {
                            state.setState(true);
                        } else {
                            state.setState(false);
                        }

                        //Save data into Database
                        String username = txt_user.getText().toString();
                        String password = txt_pass.getText().toString();
                        String phone = txt_phone.getText().toString();
                        String question = spn.getSelectedItem().toString().trim();
                        String answer = txt_answer.getText().toString().trim().toLowerCase();

                        connection.DeleteUser();
                        connection.InsertUser(1, username, password, phone, question, answer);
                        Intent intent = new Intent(MainActivity.this, Login.class);
                        startActivity(intent);
                        finish();


                    }
                }
                break;
        }
    }
}