package com.akshaymethaniya.hisaabapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdListener;

public class HomeActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    private Boolean viewOpened=false;
    private AdView mAdView,mAdView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Hissab");

        MobileAds.initialize(this,"ca-app-pub-3913368499058974~6133919896");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3913368499058974/8290962332");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mAdView = (AdView) findViewById(R.id.adView);
        mAdView1 =(AdView)findViewById(R.id.adView1);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView1.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                Intent intent=new Intent();
                if(!viewOpened) {
                    intent.setClass(getApplicationContext(), AddRecord.class);

                }
                else{
                    intent.setClass(getApplicationContext(), ViewRecord.class);

                }
                startActivity(intent);
            }
        });

    }
    public void onClickAddRecord(View view){
        viewOpened=false;
        Intent intent=new Intent(this,AddRecord.class);
        /*if(adOpen){
            startActivity(intent);
        }*/
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest adRequest1 = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);
        mAdView1.loadAd(adRequest1);
        if (mInterstitialAd.isLoaded()) {
            //adOpen=true;
            mInterstitialAd.show();
        } else {
            startActivity(intent);
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
        //
    }
    public void onClickViewRecord(View view){
        Intent intent=new Intent(this,ViewRecord.class);
        viewOpened=true;
        /*if(adOpen){
            startActivity(intent);
        }*/
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);
        if (mInterstitialAd.isLoaded()) {
            //adOpen=true;
            mInterstitialAd.show();
        } else {
            startActivity(intent);
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }


    }
    public void onClickDeleteAllRecord(View view){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Are You Sure ? This Will Delete All The Records.");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HisaabDatabaseHandler handler=new HisaabDatabaseHandler(HomeActivity.this);
                SQLiteDatabase sqLiteDatabase=handler.getWritableDatabase();
                sqLiteDatabase.execSQL("delete from HISAAB_RECORD");
                Toast toast=Toast.makeText(HomeActivity.this,"All Records Deleted Successfully",Toast.LENGTH_SHORT);
                toast.show();
                handler.close();

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.about,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.menu_about)
        {
            Intent intent=new Intent();
            intent.setClass(HomeActivity.this,About.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
}
