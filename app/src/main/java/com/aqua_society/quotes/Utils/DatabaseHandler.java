package com.aqua_society.quotes.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.aqua_society.quotes.Modules.Quote;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by MrCharif on 04/01/2017.
 */

public class DatabaseHandler extends SQLiteAssetHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "quotes.db";
    private static final String TABLE_QUOTES = "quote";


    private static final String KEY_ID = "_id";
    private static final String KEY_AUTHOR_NAME = "author_name";
    private static final String KEY_CATEGORY_NAME = "category_name";
    private static final String KEY_QTE = "qte";
    private static final String KEY_FAV = "fav";
    private static final String KEY_IMAGE = "image";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHandler(Context context, String name, String storageDirectory, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, storageDirectory, factory, version);
    }

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public List<Quote> getAllQuotes(){

        List<Quote> quoteList = new ArrayList<Quote>();
        String selectQuery = "SELECT * from "+TABLE_QUOTES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Quote quote = new Quote();
                quote.setId(cursor.getInt(0));
                quote.setAuthor_name(cursor.getString(1));
                quote.setCategory_name(cursor.getString(2));
                quote.setQte(cursor.getString(3));
                quote.setFav(cursor.getInt(4));
                quote.setImage(cursor.getString(5));

                quoteList.add(quote);
            } while (cursor.moveToNext());
        }

        return quoteList;
    }

    public List<Quote> getAllFav(){

        List<Quote> quoteList = new ArrayList<Quote>();
        String selectQuery = "SELECT * from "+TABLE_QUOTES+" where fav = 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Quote quote = new Quote();
                quote.setId(cursor.getInt(0));
                quote.setAuthor_name(cursor.getString(1));
                quote.setCategory_name(cursor.getString(2));
                quote.setQte(cursor.getString(3));
                quote.setFav(cursor.getInt(4));
                quote.setImage(cursor.getString(5));

                quoteList.add(quote);
            } while (cursor.moveToNext());
        }

        return quoteList;
    }

    public Quote getQuote(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_QUOTES,new String[]{KEY_ID,KEY_AUTHOR_NAME,KEY_CATEGORY_NAME,KEY_FAV
                ,KEY_QTE,KEY_IMAGE},KEY_ID + "=?",
                new String[]{ String.valueOf(id)},null,null,null,null);

        if(cursor != null){
            cursor.moveToFirst();

            Quote quote = new Quote();
            quote.setId(cursor.getInt(0));
            quote.setAuthor_name(cursor.getString(1));
            quote.setCategory_name(cursor.getString(2));
            quote.setFav(cursor.getInt(3));
            quote.setQte(cursor.getString(4));
            quote.setImage(cursor.getString(5));

            return quote;
        }


        return null;
    }

    public Quote getRandQuote(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_QUOTES,new String[]{KEY_ID,KEY_AUTHOR_NAME,KEY_CATEGORY_NAME,KEY_FAV
                        ,KEY_QTE,KEY_IMAGE},null,
                null,null,null,"RANDOM()","1");

        if(cursor != null){
            cursor.moveToFirst();

            Quote quote = new Quote();
            quote.setId(cursor.getInt(0));
            quote.setAuthor_name(cursor.getString(1));
            quote.setCategory_name(cursor.getString(2));
            quote.setFav(cursor.getInt(3));
            quote.setQte(cursor.getString(4));
            quote.setImage(cursor.getString(5));

            return quote;
        }


        return null;
    }

    public int favQuote(Quote quote){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FAV, quote.getFav());

        // updating row
        return db.update(TABLE_QUOTES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(quote.getId()) });
    }
}
