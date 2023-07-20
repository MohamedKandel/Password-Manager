package com.Mkandeel.passwordmanager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Account_List extends AppCompatActivity {

    private ArrayList<String> accounts;
    private ArrayList<Integer> sized;
    private ArrayList<Integer> imgs;

    private DBConnection connection;
    @BindView(R.id.lv)
    ListView lv;
    private Intent intent;
    private String selectedItem;
    private String pwd;

    //private int number;
    private InterstitialAd mInterstitialAd;
    private myAdapter adapter;

    private Functions functions;
    private State state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__list);
        ButterKnife.bind(this);
        state = new State(this);
        functions = new Functions(this);
        connection = new DBConnection(this);

        boolean isDark = state.getState();
        if (isDark) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        ArrayList<String> Data = connection.Get_Accounts("Facebook");

        //Random random = new Random();

        if (Data.size() == 0) {
            functions.initiate(connection);
        }
        accounts = new ArrayList<>();
        sized = new ArrayList<>();
        imgs = new ArrayList<>();

        functions.Fill_Lists(connection, isDark, accounts, sized, imgs);
        adapter = new myAdapter(this, accounts, imgs, sized);
        lv.setAdapter(adapter);

        MobileAds.initialize(Account_List.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        createAd();

        //ArrayList<String> others = connection.Get_Others_Accounts();

        String name = getIntent().getStringExtra("name");
        getSupportActionBar().setTitle("Welcome, " + name);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //number = random.nextInt(2);
                switch (position) {
                    case 0:
                        //facebook data
                        intent = new Intent(Account_List.this, Facebook.class);
                        intent.putExtra("type", "Facebook");
                        intent.putExtra("name", name);
                        /*startActivity(intent);
                        finish();*/
                        break;
                    case 1:
                        //Ig data
                        intent = new Intent(Account_List.this, Facebook.class);
                        intent.putExtra("type", "Instagram");
                        intent.putExtra("name", name);
                        /*startActivity(intent);
                        finish();*/
                        break;
                    case 2:
                        //Twitter data
                        intent = new Intent(Account_List.this, Facebook.class);
                        intent.putExtra("type", "Twitter");
                        intent.putExtra("name", name);
                        /*startActivity(intent);
                        finish();*/
                        break;
                    case 3:
                        //Google data
                        intent = new Intent(Account_List.this, Facebook.class);
                        intent.putExtra("type", "Google");
                        intent.putExtra("name", name);
                        /*startActivity(intent);
                        finish();*/
                        break;
                    case 4:
                        //Linked-In data
                        intent = new Intent(Account_List.this, Facebook.class);
                        intent.putExtra("type", "Linked In");
                        intent.putExtra("name", name);
                        /*startActivity(intent);
                        finish();*/
                        break;
                    case 5:
                        //Amazon data
                        intent = new Intent(Account_List.this, Facebook.class);
                        intent.putExtra("type", "Amazon");
                        intent.putExtra("name", name);
                        /*startActivity(intent);
                        finish();*/
                        break;
                    case 6:
                        //Yahoo data
                        intent = new Intent(Account_List.this, Facebook.class);
                        intent.putExtra("type", "Yahoo");
                        intent.putExtra("name", name);
                        /*startActivity(intent);
                        finish();*/
                        break;
                    case 7:
                        //Pint. data
                        intent = new Intent(Account_List.this, Facebook.class);
                        intent.putExtra("type", "Pinterest");
                        intent.putExtra("name", name);
                        /*startActivity(intent);
                        finish();*/
                        break;
                    case 8:
                        //Netflix data
                        intent = new Intent(Account_List.this, Facebook.class);
                        intent.putExtra("type", "Netflix");
                        intent.putExtra("name", name);
                        /*startActivity(intent);
                        finish();*/
                        break;
                    case 9:
                        //Roll data
                        intent = new Intent(Account_List.this, Facebook.class);
                        intent.putExtra("type", "Crunchyroll");
                        intent.putExtra("name", name);
                        /*startActivity(intent);
                        finish();*/
                        break;
                    case 10:
                        //others data
                        intent = new Intent(Account_List.this, Facebook.class);
                        intent.putExtra("type", "Other");
                        intent.putExtra("name", name);
                        /*startActivity(intent);
                        finish();*/
                        break;
                }
                // Ads code here
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(Account_List.this);
                } else {
                    //Intent intent = new Intent(MainActivity.this, second.class);
                    startActivity(intent);
                    finish();
                }
                //Log.d("Admob  number ",number+"");
            }
        });

    }

    private void createAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        loadAd(adRequest);
    }

    private void loadAd(AdRequest adRequest) {
        InterstitialAd.load(this, "ca-app-pub-6190384966850284/4640047693", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("Admob ", "onAdLoaded");

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when fullscreen content is dismissed.
                                Log.d("Admob ", "The ad was dismissed.");

                                //Intent intent = new Intent(MainActivity.this, second.class);
                                startActivity(intent);
                                //finish();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when fullscreen content failed to show.
                                Log.d("Admob ", "The ad failed to show.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when fullscreen content is shown.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                mInterstitialAd = null;
                                Log.d("Admob ", "The ad was shown.");
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("Admob ", loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.theme:
                //change theme activity
                boolean isDark = state.getState();
                Intent mIntent = getIntent();
                final String[] items = {"dark Mode", "light Mode"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Account_List.this);
                builder.setTitle("Choose Mode");
                if (isDark) {
                    builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            selectedItem = items[which];
                        }
                    });
                } else {
                    builder.setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            selectedItem = items[which];
                        }
                    });
                }
                builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (selectedItem.equals("dark Mode")) {
                            state.setState(true);
                            startActivity(mIntent);
                            finish();
                        } else if (selectedItem.equals("light Mode")) {
                            state.setState(false);
                            startActivity(mIntent);
                            finish();
                        }

                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                break;
            case R.id.tot:
                //tutorial activity
                Intent intent = new Intent(Account_List.this, tutorial.class);
                startActivity(intent);

                break;
            case R.id.generate:
                pwd = functions.Generate_Passwords(10);
                AlertDialog.Builder alert = new AlertDialog.Builder(Account_List.this);
                alert.setTitle("Generate Password");
                alert.setMessage(pwd);

                alert.setPositiveButton("Copy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData data = ClipData.newPlainText("Generated_Pwd", pwd);
                        manager.setPrimaryClip(data);
                        functions.Make_Toast(Account_List.this, pwd + " copied");
                    }
                });

                alert.setNeutralButton("Generate another password", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pwd = functions.Generate_Passwords(10);
                        alert.setMessage(pwd);
                        alert.show();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();
                break;
        }
        return true;
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

}

class myAdapter extends BaseAdapter {
    ArrayList<String> accounts;
    ArrayList<Integer> imgs;
    ArrayList<Integer> sized;
    Context context;
    LayoutInflater inflater;

    Functions functions;
    State state;

    public myAdapter(Context context, ArrayList<String> accounts, ArrayList<Integer> imgs, ArrayList<Integer> size) {
        this.accounts = accounts;
        this.context = context;
        this.imgs = imgs;
        this.sized = size;
        functions = new Functions(context);
        state = new State(context);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return accounts.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.list_row, null);

        TextView txt_account = convertView.findViewById(R.id.txt_account);
        TextView msize = convertView.findViewById(R.id.txt_size);
        ImageView img = convertView.findViewById(R.id.img);

        txt_account.setText(accounts.get(position));
        msize.setText(sized.get(position) + "");
        img.setImageResource(imgs.get(position));

        functions.change_fonts("Segoe_UIb.ttf", txt_account, msize);
        boolean isDark = state.getState();
        if (isDark) {
            functions.Change_Color("white", txt_account, msize);
        } else {
            functions.Change_Color("black", txt_account, msize);
        }
        return convertView;
    }
}

