package com.aqua_society.quotes.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.aqua_society.quotes.Adapters.AppsAdapter;
import com.aqua_society.quotes.Api.MyApi;
import com.aqua_society.quotes.Modules.Apps;
import com.aqua_society.quotes.R;
import com.aqua_society.quotes.Utils.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OurApps extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppsAdapter appsAdapter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_apps);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        Collections.shuffle(utils.APPS_LIST);
        appsAdapter = new AppsAdapter(getBaseContext(), utils.APPS_LIST);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(appsAdapter);

    }




}
