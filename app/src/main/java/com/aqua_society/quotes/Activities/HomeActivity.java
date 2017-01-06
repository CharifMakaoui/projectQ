package com.aqua_society.quotes.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aqua_society.quotes.Api.MyApi;
import com.aqua_society.quotes.Modules.AdsObject;
import com.aqua_society.quotes.Modules.Quote;
import com.aqua_society.quotes.R;
import com.aqua_society.quotes.Utils.DatabaseHandler;
import com.aqua_society.quotes.Utils.utils;
import com.aqua_society.quotes.Views.MyButton;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;
import com.melnykov.fab.FloatingActionButton;

import java.util.EmptyStackException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    InterstitialAd mInterstitialAd;

    ConstraintLayout SplashScreen;
    FloatingActionButton shareButton;
    MyButton ShowQuotes,RatThis,OurApps,YourFav;
    LinearLayout ads_native,HomeNativeHided;
    RelativeLayout dayQuote,BottomContent;
    ImageView button_close_dayQuote;
    TextView text_dayQuote;

    int animation_duration = 700;
    boolean LoadAdComplet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        GetAdsCodes();
        StartSplash();
        getRandQuote();

        utils.GetOurApps();
    }

    private void initView() {
        SplashScreen = (ConstraintLayout) findViewById(R.id.SplashScreen);

        ShowQuotes = (MyButton) findViewById(R.id.ShowQuotes);
        YourFav = (MyButton) findViewById(R.id.YourFav);
        RatThis = (MyButton) findViewById(R.id.RatThis);
        OurApps = (MyButton) findViewById(R.id.OurApps);

        ads_native = (LinearLayout) findViewById(R.id.adView_native);
        shareButton = (FloatingActionButton) findViewById(R.id.shareButton);

        BottomContent = (RelativeLayout) findViewById(R.id.BottomContent);
        HomeNativeHided = (LinearLayout) findViewById(R.id.HomeNativeHided);
        dayQuote = (RelativeLayout) findViewById(R.id.dayQuote);
        button_close_dayQuote = (ImageView) findViewById(R.id.button_close_dayQuote);
        text_dayQuote = (TextView) findViewById(R.id.text_dayQuote);

        ButtonsClick();
    }

    int action = 0;
    private void ButtonsClick(){
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setShareIntent();
            }
        });

        ShowQuotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action = 1;
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
                else{
                    workToDoAfterCloseAdd();
                }
            }
        });

        YourFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action = 2;
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
                else{
                    workToDoAfterCloseAdd();
                }
            }
        });

        RatThis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id="+getPackageName()));

                try{
                    startActivity(intent);
                }
                catch(Exception e){
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id="
                            +getPackageName()));
                }
                startActivity(intent);
            }
        });

        OurApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(utils.APPS_LIST.size() > 0 && utils.isInternetAvailable(getBaseContext())){
                    Intent intent = new Intent(HomeActivity.this, com.aqua_society.quotes.Activities.OurApps.class);
                    HomeActivity.this.startActivity(intent);
                }
                else{
                    if(utils.isInternetAvailable(getBaseContext())){
                        Toast.makeText(getBaseContext(),"صفحة التطبيقات لم تحمل بسبب جودة الإتصال لديك",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getBaseContext(),"المرجو التأكد من إتصالك",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        button_close_dayQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeNativeHided.setVisibility(View.VISIBLE);
                dayQuote.setVisibility(View.GONE);
            }
        });
    }

    private void StartSplash() {
        StartSection_Group(View.GONE);

        SplashScreen.setVisibility(View.VISIBLE);
        SplashScreen.setTranslationY(100);
        SplashScreen.animate().translationY(0)
                .setDuration(750)
                .setInterpolator(new DecelerateInterpolator())
                .start();



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashScreen.setVisibility(View.GONE);
                StartSection_Group(View.VISIBLE);

                enterAnimation();
            }
        }, 5000);

    }

    private void workToDoAfterCloseAdd(){
        if(action == 1){
            Intent intent = new Intent(HomeActivity.this, com.aqua_society.quotes.Activities.QuotesListActivity.class);
            intent.putExtra(utils.KEY_SHOW_FULL_OR_FAV, "FULL");
            HomeActivity.this.startActivity(intent);
        }

        else if(action == 2){
            Intent intent = new Intent(HomeActivity.this, com.aqua_society.quotes.Activities.QuotesListActivity.class);
            intent.putExtra(utils.KEY_SHOW_FULL_OR_FAV, "FAV");
            HomeActivity.this.startActivity(intent);
        }
    }

    private void StartSection_Group(int visibility){
        ShowQuotes.setVisibility(visibility);
        YourFav.setVisibility(visibility);
        RatThis.setVisibility(visibility);
        OurApps.setVisibility(visibility);

        ads_native.setVisibility(visibility);
        shareButton.setVisibility(visibility);
        BottomContent.setVisibility(visibility);
    }

    private void GetAdsCodes(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.dropbox.com/s/gmonpx7kzmtm4pz/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyApi admobCodes = retrofit.create(MyApi.class);

        Call<AdsObject> connection = admobCodes.getAdsConfig();
        connection.enqueue(new Callback<AdsObject>() {
            @Override
            public void onResponse(Call<AdsObject> call, Response<AdsObject> response) {
                if(!utils.protection(getBaseContext()))
                    throw new EmptyStackException();
                Log.d("data","Nice Get Admob Codes From File URL "+ response.body().getBannerAd());
                if(response.body().getBannerAd() != null)
                    utils.bannerAd_CODE = response.body().getBannerAd();

                if(response.body().getInterstitialAd() != null)
                    utils.interstitialAd_CODE = response.body().getInterstitialAd();

                if(response.body().getNativeAd() != null)
                    utils.nativeAd_CODE = response.body().getNativeAd();

                LoadAdComplet = true;
                start_Ads();
            }

            @Override
            public void onFailure(Call<AdsObject> call, Throwable t) {
                Log.d("data","Error Get Admob Codes From File URL");
                LoadAdComplet = true;
                start_Ads();
            }
        });

    }

    private void start_Ads() {
        native_AdsInit();
        interstitial_AddInit();
    }

    private void enterAnimation(){
        ShowQuotes.setTranslationX(-100f);
        YourFav.setTranslationX(100f);
        RatThis.setTranslationX(-100f);
        OurApps.setTranslationX(100f);

        shareButton.setTranslationY(100);
        BottomContent.setTranslationY(100);

        ShowQuotes.animate().translationX(0)
                .setDuration(animation_duration)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();

        YourFav.animate().translationX(0)
                .setDuration(animation_duration)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();

        RatThis.animate().translationX(0)
                .setDuration(animation_duration)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();

        OurApps.animate().translationX(0)
                .setDuration(animation_duration)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();

        shareButton.animate().translationY(0)
                .setDuration(animation_duration)
                .setInterpolator(new DecelerateInterpolator())
                .start();

        BottomContent.animate().translationY(0)
                .setDuration(animation_duration)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    private void getRandQuote(){
        DatabaseHandler db = new DatabaseHandler(this);
        Quote quote = db.getRandQuote();
        text_dayQuote.setText(quote.getContent()[2]);
        Log.d("RandQuote", quote.toString());
    }

    // Ads Functions :
    private void native_AdsInit() {
        LinearLayout container = (LinearLayout) findViewById(R.id.adView_native);
        utils.nativAds(getBaseContext(), container);
    }

    private void interstitial_AddInit() {
        mInterstitialAd = utils.interstitialAds(getBaseContext());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                workToDoAfterCloseAdd();
            }
        });

        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        mInterstitialAd.loadAd(utils.adRequest());
    }

    public void setShareIntent() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello World :D!");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "شارك التطبيق"));
    }
}
