package com.aqua_society.quotes.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aqua_society.quotes.Activities.QuoteActivity;
import com.aqua_society.quotes.Modules.Apps;
import com.aqua_society.quotes.R;
import com.aqua_society.quotes.Utils.utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MrCharif on 01/01/2017.
 */

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.AppsViewHolder> {

    Context context;
    private List<Apps> appsList;

    public AppsAdapter(Context context, List<Apps> list){
        this.context = context;
        this.appsList = list;
    }

    @Override
    public AppsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.app_row, parent, false);

        return new AppsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AppsViewHolder holder, int position) {
        final Apps apps = appsList.get(position);

        Picasso.with(context)
                .load(apps.getCover())
                .into(holder.app_avatar);

        holder.app_name.setText(apps.getName());
        holder.app_description.setText(apps.getDescription());
        holder.nbr_download.setText(apps.getNbr_download());

        holder.app_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id="+apps.getPackage_name()));

                try{
                    context.startActivity(intent);
                }
                catch(Exception e){
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id="
                            +apps.getPackage_name()));
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appsList.size();
    }

    public class AppsViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout app_container;
        public ImageView app_avatar;
        public TextView app_name,app_description,nbr_download;
        public Button btn_download;

        public AppsViewHolder(View itemView) {
            super(itemView);

            app_container = (RelativeLayout) itemView.findViewById(R.id.app_container);
            app_avatar = (ImageView) itemView.findViewById(R.id.app_avatar);
            app_name = (TextView) itemView.findViewById(R.id.app_name);
            app_description = (TextView) itemView.findViewById(R.id.app_description);
            nbr_download = (TextView) itemView.findViewById(R.id.nbr_download);
            btn_download = (Button) itemView.findViewById(R.id.btn_download);
        }
    }

    public void update(List<Apps> list){
        this.appsList = list;
    }
}
