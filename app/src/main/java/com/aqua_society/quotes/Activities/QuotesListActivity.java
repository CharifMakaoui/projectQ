package com.aqua_society.quotes.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.aqua_society.quotes.Adapters.QuoteAdapter;
import com.aqua_society.quotes.Modules.Quote;
import com.aqua_society.quotes.R;
import com.aqua_society.quotes.Utils.DatabaseHandler;
import com.aqua_society.quotes.Utils.utils;

import java.util.Collections;
import java.util.List;

public class QuotesListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QuoteAdapter quoteAdapter;

    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_list);

        databaseHandler = new DatabaseHandler(this);
        String full_fav = getIntent().getStringExtra(utils.KEY_SHOW_FULL_OR_FAV);
        List<Quote> quoteList ;

        if(full_fav.equals("FULL")){
            quoteList = databaseHandler.getAllQuotes();
            //Collections.shuffle(quoteList);
            quoteAdapter = new QuoteAdapter(this,quoteList);
            quoteAdapter.setType("FULL");
        }
        else{
            quoteList = databaseHandler.getAllFav();
            //Collections.shuffle(quoteList);
            quoteAdapter = new QuoteAdapter(this,quoteList);
            quoteAdapter.setType("FAV");
        }

        utils.QUOTE_LIST = quoteList;

        Log.d("QuotesList","Size : "+quoteList.size());

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(quoteAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        if(utils.APPS_LIST.size() > 0 && utils.isInternetAvailable(getBaseContext())){
            Intent intent = new Intent(QuotesListActivity.this, com.aqua_society.quotes.Activities.OurApps.class);
            QuotesListActivity.this.startActivity(intent);
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
}
