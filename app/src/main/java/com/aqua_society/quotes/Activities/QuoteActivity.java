package com.aqua_society.quotes.Activities;

import android.animation.Animator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aqua_society.quotes.Modules.Quote;
import com.aqua_society.quotes.R;
import com.aqua_society.quotes.Utils.DatabaseHandler;
import com.aqua_society.quotes.Utils.utils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;
import com.melnykov.fab.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class QuoteActivity extends AppCompatActivity {

    int QUOTE_ID;

    ImageView Quote_Image,prevButton,nextButton;
    FloatingActionButton shareButton,LikeButton,CutButton;

    TextView quote_title,quote_content,quote_author_name,quote_category;
    Quote quote;

    InterstitialAd mInterstitialAd;
    NativeExpressAdView native_adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);

        QUOTE_ID = getIntent().getIntExtra(utils.KEY_QUOTE_ID,-1);

        if(QUOTE_ID > -1){
            quote = utils.QUOTE_LIST.get(QUOTE_ID);

            initView();
            initData();
            enterAnimation();
            ButtonClick();
            start_Ads();
        }

    }

    private void initData() {

        quote_title.setText(quote.getTitle());
        quote_content.setText(quote.getContent()[2]);
        quote_author_name.setText(quote.getAuthor_name());
        quote_category.setText(quote.getCategory_name());

        if(quote.getImage() != null && !quote.getImage().equals("")){
            Picasso.with(this)
                    .load(quote.getImage())
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(Quote_Image);
        }
        else{
            Quote_Image.setImageResource(R.drawable.no_image);
        }

        if(quote.getFav() == 0){
            LikeButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
        }
        else{
            LikeButton.setImageResource(R.drawable.ic_favorite_white_24dp);
        }
    }

    private void ButtonClick(){
        LikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quote.getFav() == 0){
                    quote.setFav(1);
                    LikeButton.setImageResource(R.drawable.ic_favorite_white_24dp);
                }

                else{
                    quote.setFav(0);
                    LikeButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                }

                DatabaseHandler db = new DatabaseHandler(getBaseContext());
                db.favQuote(quote);
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setShareIntent();
            }
        });

        CutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }

                CopyToClip();
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cur = QUOTE_ID;

                if(QUOTE_ID > 0){
                    QUOTE_ID --;
                }
                else{
                    QUOTE_ID = 0;
                }

                if(cur != QUOTE_ID){
                    utils.nbr_click ++;
                    if (mInterstitialAd.isLoaded() && (utils.nbr_click % utils.nbr_click_to_show_add) == 0) {
                        mInterstitialAd.show();
                    }
                    else{
                        workToDoAfterAdsClose();
                    }
                }

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cur = QUOTE_ID;

                if(QUOTE_ID < utils.QUOTE_LIST.size() - 1){
                    QUOTE_ID ++;
                }
                else{
                    QUOTE_ID = utils.QUOTE_LIST.size() - 1;
                }

                if(cur != QUOTE_ID){
                    utils.nbr_click ++;
                    if (mInterstitialAd.isLoaded() && (utils.nbr_click % utils.nbr_click_to_show_add) == 0) {
                        mInterstitialAd.show();
                    }
                    else{
                        workToDoAfterAdsClose();
                    }
                }
            }
        });
    }

    private void ShowQuote(int id){
        finish();
        Intent intent = new Intent(QuoteActivity.this,QuoteActivity.class);
        intent.putExtra(utils.KEY_QUOTE_ID , id);
        QuoteActivity.this.startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void initView() {
        Quote_Image = (ImageView) findViewById(R.id.Quote_Image);
        prevButton = (ImageView) findViewById(R.id.prevButton);
        nextButton = (ImageView) findViewById(R.id.nextButton);

        shareButton = (FloatingActionButton) findViewById(R.id.shareButton);
        LikeButton = (FloatingActionButton) findViewById(R.id.LikeButton);
        CutButton = (FloatingActionButton) findViewById(R.id.CutButton);

        quote_title = (TextView) findViewById(R.id.quote_title);
        quote_content = (TextView) findViewById(R.id.quote_content);
        quote_author_name = (TextView) findViewById(R.id.quote_author_name);
        quote_category = (TextView) findViewById(R.id.quote_category);
    }

    private void enterAnimation(){
        quote_content.setTranslationY(100);
        quote_title.setTranslationX(-100f);
        Quote_Image.setScaleY(0.1f);
        shareButton.setTranslationY(100);
        LikeButton.setTranslationY(100);
        CutButton.setTranslationY(100);

        Quote_Image.animate().scaleY(1)
                .setDuration(500)
                .setInterpolator(new DecelerateInterpolator())
                .start();

        quote_content.animate().translationY(0)
                .setDuration(500)
                .setInterpolator(new DecelerateInterpolator())
                .start();

        quote_title.animate().translationX(0)
                .setDuration(500)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();

        shareButton.animate().translationY(0)
                .setDuration(500)
                .setInterpolator(new DecelerateInterpolator())
                .start();

        LikeButton.animate().translationY(0)
                .setDuration(500)
                .setInterpolator(new DecelerateInterpolator())
                .start();

        CutButton.animate().translationY(0)
                .setDuration(500)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    public void CopyToClip(){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("test", quote.getQte());
        clipboard.setPrimaryClip(clip);
    }

    public void setShareIntent() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello World :D!");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "شارك التطبيق"));
    }

    private void start_Ads() {
        //Start Ads :
        native_AdsInit();
        //banner_AdsInit();
        interstitial_AddInit();
    }

    private void workToDoAfterAdsClose(){
        ShowQuote(QUOTE_ID);
    }

    // Ads Functions :
    private void native_AdsInit() {

        LinearLayout container = (LinearLayout) findViewById(R.id.adView_native);
        native_adView = utils.nativAds(getBaseContext(), container);
    }

    private void interstitial_AddInit() {
        mInterstitialAd = utils.interstitialAds(getBaseContext());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });

        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        mInterstitialAd.loadAd(utils.adRequest());
    }
}
