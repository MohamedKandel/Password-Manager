package com.Mkandeel.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPassword extends AppCompatActivity {

    @BindView(R.id.textView13)
    TextView textView13;
    @BindView(R.id.textView11)
    TextView textView11;
    @BindView(R.id.txt_Npass)
    EditText txt_pass;
    @BindView(R.id.textView12)
    TextView textView12;
    @BindView(R.id.txt_Npass2)
    EditText txt_pass2;
    @BindView(R.id.btn_update)
    Button btn_update;

    Functions functions;
    State state;
    DBConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);

        functions = new Functions(this);
        state = new State(this);
        connection = new DBConnection(this);

        getSupportActionBar().hide();
        functions.change_fonts("Segoe_UIb.ttf",textView13);
        functions.change_fonts("Segoe_UI.ttf",textView11,textView12);

        boolean isDark = state.getState();
        if (isDark) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            functions.Change_Color("white", textView11, textView12, textView13);
            functions.Change_Hint_Color("dark", txt_pass2, txt_pass);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            functions.Change_Color("black", textView11, textView12, textView13);
            functions.Change_Hint_Color("light", txt_pass2, txt_pass);
        }
    }

    @OnClick(R.id.btn_update)
    public void onViewClicked() {
        boolean isEmpty = functions.isEmpty(txt_pass,txt_pass2);
        if (isEmpty) {
            functions.Make_Toast(ForgetPassword.this,"please fill all required fields");
            functions.Clear(txt_pass,txt_pass2);
        } else {
            String pass1 = txt_pass.getText().toString();
            String pass2 = txt_pass2.getText().toString();
            if (pass1.equals(pass2)) {
                connection.UpdateUser(1,pass1);
                functions.Make_Toast(ForgetPassword.this,"password changed " +
                        "please login again");
                Intent intent = new Intent(ForgetPassword.this,Login.class);
                startActivity(intent);
                finish();
            } else {
                functions.Make_Toast(ForgetPassword.this,"password doesn't match");
                functions.ClearAll(txt_pass,txt_pass2);
            }
            //functions.Make_Toast(ForgetPassword.this,"updated");
        }
    }
}