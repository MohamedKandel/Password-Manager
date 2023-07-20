package com.Mkandeel.passwordmanager;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleViewOthers extends RecyclerView.Adapter<RecycleViewOthers.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<String> mails;
    private ArrayList<String> passes;
    private ArrayList<Integer> imgs;
    private ArrayList<String> mtypes;
    private DBConnection connection;
    private State state;
    private Functions functions;
    private String type;

    public RecycleViewOthers(Context context, ArrayList<String> mails, ArrayList<String> passes, ArrayList<Integer> imgs, ArrayList<String> mtype, String type) {
        this.context = context;
        this.mails = mails;
        this.passes = passes;
        this.imgs = imgs;
        this.mtypes = mtype;
        state = new State(context);
        functions = new Functions(context);
        this.type = type;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycle_view_others, parent, false);
        ViewHolder holder = new ViewHolder(view);
        connection = new DBConnection(context);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_mail.setText(mails.get(position));
        holder.txt_pass.setText(passes.get(position));
        holder.txt_type.setText(mtypes.get(position));


        boolean isDark = state.getState();
        if (isDark) {
            holder.img.setImageResource(R.drawable.eye_dark);
            functions.Change_Color("white", holder.txt_mail, holder.txt_pass,
                    holder.txt_email, holder.txt_password, holder.txt_type);
        } else {
            holder.img.setImageResource(R.drawable.eye_normal);
            functions.Change_Color("black", holder.txt_mail, holder.txt_pass,
                    holder.txt_email, holder.txt_password, holder.txt_type);
        }
        functions.change_fonts("Segoe_UIb.ttf", holder.txt_type);

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = holder.txt_pass.getText().toString();
                String email = holder.txt_mail.getText().toString();
                String type = holder.txt_type.getText().toString();

                Dialog dialog = new Dialog(context);
                if (isDark) {
                    dialog.setContentView(R.layout.alert_dialog_others_dark);
                } else {
                    dialog.setContentView(R.layout.alert_dialog_others);
                }


                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(true);

                TextView TextView1 = dialog.findViewById(R.id.textView2);
                TextView TextView2 = dialog.findViewById(R.id.textView3);
                TextView TextView3 = dialog.findViewById(R.id.textView4);
                TextView TextView4 = dialog.findViewById(R.id.textView5);
                TextView TextView5 = dialog.findViewById(R.id.textView6);
                TextView TextView6 = dialog.findViewById(R.id.textView7);

                Button btn = dialog.findViewById(R.id.btn_ok);

                functions.change_fonts("Segoe_UI.ttf", TextView1, TextView2, TextView3,
                        TextView4, TextView5, TextView6);

                TextView2.setText(email);
                TextView4.setText(password);
                TextView6.setText(type);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView txt_mail, txt_email, txt_password, txt_type;
        EditText txt_pass;
        LinearLayout layout;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_eye);
            txt_mail = itemView.findViewById(R.id.txt_mail);
            txt_pass = itemView.findViewById(R.id.txt_pass);
            txt_email = itemView.findViewById(R.id.txt_email);
            txt_password = itemView.findViewById(R.id.txt_password);
            txt_type = itemView.findViewById(R.id.txt_type);
            layout = itemView.findViewById(R.id.layout);
            card = itemView.findViewById(R.id.card);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu menu = new PopupMenu(context, v);
                    menu.getMenuInflater().inflate(R.menu.delete_edit, menu.getMenu());
                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int id = item.getItemId();
                            switch (id) {
                                case R.id.edit:
                                    //Edit account
                                    String Ousername = txt_mail.getText().toString();
                                    String Opassword = txt_pass.getText().toString();
                                    String Otype = txt_type.getText().toString();

                                    Dialog dialog = new Dialog(context);
                                    boolean isDark = state.getState();
                                    if (isDark) {
                                        dialog.setContentView(R.layout.dialog_others_dark);
                                    } else {
                                        dialog.setContentView(R.layout.dialog_others);
                                    }

                                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    dialog.setCancelable(false);
                                    dialog.setCanceledOnTouchOutside(true);

                                    TextView txt_tilte = dialog.findViewById(R.id.txt_Dtitle);
                                    TextView Txt_Mail = dialog.findViewById(R.id.TextMail);
                                    TextView Txt_Pass = dialog.findViewById(R.id.TextPass);
                                    TextView Txt_Type = dialog.findViewById(R.id.TextType);

                                    EditText mtype = dialog.findViewById(R.id.txt_type);
                                    EditText mmail = dialog.findViewById(R.id.txt_mail);
                                    EditText mpass = dialog.findViewById(R.id.txt_pass);

                                    Button btn_save = dialog.findViewById(R.id.Add_new);
                                    txt_tilte.setText("Edit your account");
                                    btn_save.setText("Edit account");

                                    mtype.setText(Otype);
                                    mmail.setText(Ousername);
                                    mpass.setText(Opassword);

                                    functions.change_fonts("Segoe_UIb.ttf", txt_tilte, Txt_Mail, Txt_Pass, Txt_Type);
                                    btn_save.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String Nuser = mmail.getText().toString();
                                            String Npass = mpass.getText().toString();
                                            String Ntype = mtype.getText().toString();
                                            if (Nuser.trim().equals("") || Npass.trim().equals("") ||
                                                    Ntype.trim().equals("")) {
                                                functions.Make_Toast(context, "Please fill all required fields");
                                            } else {
                                                if (Ousername.trim().equals(Nuser.trim()) && Opassword.trim().equals(Npass.trim())
                                                && Otype.trim().equals(Ntype.trim())) {
                                                    functions.Make_Toast(context,"You didn't change any data");
                                                } else {
                                                    connection.UpdateOthersAccount(Ousername, Opassword, Otype,
                                                            Nuser, Npass, Ntype);
                                                    mails.set(getAbsoluteAdapterPosition(), Nuser);
                                                    passes.set(getAbsoluteAdapterPosition(), Npass);
                                                    mtypes.set(getAbsoluteAdapterPosition(), Ntype);
                                                    notifyItemChanged(getAbsoluteAdapterPosition());
                                                }
                                                dialog.cancel();
                                            }
                                        }
                                    });
                                    dialog.show();

                                    break;

                                case R.id.delete:
                                    //Delete account
                                    String username = txt_mail.getText().toString();
                                    String password = txt_pass.getText().toString();
                                    String type = txt_type.getText().toString();

                                    connection.DeleteOthersAccount(username, password, type);
                                    mails.remove(getAbsoluteAdapterPosition());
                                    passes.remove(getAbsoluteAdapterPosition());
                                    imgs.remove(getAbsoluteAdapterPosition());
                                    mtypes.remove(getAbsoluteAdapterPosition());
                                    notifyItemRemoved(getAbsoluteAdapterPosition());
                                    break;
                            }
                            return true;
                        }
                    });
                    menu.show();
                }
            });
        }

    }

}
