package com.aqua_society.quotes.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aqua_society.quotes.Activities.QuoteActivity;
import com.aqua_society.quotes.Modules.Apps;
import com.aqua_society.quotes.Modules.Quote;
import com.aqua_society.quotes.R;
import com.aqua_society.quotes.Utils.DatabaseHandler;
import com.aqua_society.quotes.Utils.utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MrCharif on 04/01/2017.
 */

public class QuoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Quote> quoteList;

    private String Type = "";

    public void setType(String Type){
        this.Type = Type;
    }

    public QuoteAdapter(Context context, List<Quote> list){
        this.context = context;
        this.quoteList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 9988998){
            return new AdsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.add_row, parent, false));
        }
        else{
            return new QuoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_row, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        if(viewType == 9988998){
            AdsViewHolder adsHolder = (AdsViewHolder) holder;
            utils.nativAds(context, adsHolder.adView_native);
        }
        else{
            final QuoteViewHolder quoteViewHolder = (QuoteViewHolder) holder;
            final Quote quote = quoteList.get(position);
            quoteViewHolder.quote_title.setText(quote.getTitle());
            quoteViewHolder.quote_author_name.setText(quote.getAuthor_name());
            quoteViewHolder.quote_description.setText(quote.getDescription(80));

            if(quote.getImage() != null && !quote.getImage().equals("")){
                Picasso.with(context)
                        .load(quote.getImage())
                        .placeholder(R.drawable.no_image)
                        .error(R.drawable.no_image)
                        .into(quoteViewHolder.quote_image);
            }
            else{
                quoteViewHolder.quote_image.setImageResource(R.drawable.no_image);
            }


            if(quote.getFav() == 0){
                quoteViewHolder.btn_fav.setImageResource(R.drawable.ic_favorite_border_white_24dp);
            }
            else{
                quoteViewHolder.btn_fav.setImageResource(R.drawable.ic_favorite_white_24dp);
            }

            quoteViewHolder.btn_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(quote.getFav() == 0){
                        quote.setFav(1);
                        quoteViewHolder.btn_fav.setImageResource(R.drawable.ic_favorite_white_24dp);
                    }

                    else{
                        quote.setFav(0);
                        quoteViewHolder.btn_fav.setImageResource(R.drawable.ic_favorite_border_white_24dp);

                        if(Type.equals("FAV")){
                            quoteList.remove(position);
                            notifyDataSetChanged();
                        }
                    }

                    DatabaseHandler db = new DatabaseHandler(context);
                    db.favQuote(quote);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return quoteList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if((position % 6) == 0 && position > 0){
            return 9988998;
        }
        else{
            return super.getItemViewType(position);
        }
    }

    public void update(List<Quote> list){
        this.quoteList = list;
    }


    public class QuoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView quote_image,btn_fav;
        private TextView quote_title,quote_author_name,quote_description;


        public QuoteViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            quote_image = (ImageView) itemView.findViewById(R.id.quote_image);
            btn_fav = (ImageView) itemView.findViewById(R.id.btn_fav);

            quote_title = (TextView) itemView.findViewById(R.id.quote_title);
            quote_author_name = (TextView) itemView.findViewById(R.id.quote_author_name);
            quote_description = (TextView) itemView.findViewById(R.id.quote_description);

        }

        @Override
        public void onClick(View v) {
            ShowQuote(getAdapterPosition());
        }
    }

    public class AdsViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout adView_native;

        public AdsViewHolder(View itemView) {
            super(itemView);
            adView_native = (LinearLayout) itemView.findViewById(R.id.adView_native);
        }
    }

    private void ShowQuote(int id){
        Intent intent = new Intent(context,QuoteActivity.class);
        intent.putExtra(utils.KEY_QUOTE_ID , id);
        context.startActivity(intent);
    }
}
