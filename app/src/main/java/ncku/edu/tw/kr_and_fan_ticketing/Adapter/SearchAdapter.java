package ncku.edu.tw.kr_and_fan_ticketing.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ncku.edu.tw.kr_and_fan_ticketing.Data.SearchItem;
import ncku.edu.tw.kr_and_fan_ticketing.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    Context mContext;
    ArrayList<SearchItem> mSearchItems;

    public SearchAdapter(Context context, ArrayList<SearchItem> searchItems) {
        this.mContext = context;
        this.mSearchItems = searchItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.search_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder viewHolder, int position) {
        SearchItem searchItem = mSearchItems.get(position);
        viewHolder.bindTo(searchItem);
    }

    @Override
    public int getItemCount() {
        return mSearchItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mAirName;
        TextView mPrice;
        TextView mFromCountry;
        TextView mToCountry;
        TextView mFromTime;
        TextView mToTime;

        ViewHolder(View itemView) {
            super(itemView);

            mAirName = itemView.findViewById(R.id.air_name);
            mPrice = itemView.findViewById(R.id.price);
            mFromCountry = itemView.findViewById(R.id.from_country);
            mToCountry = itemView.findViewById(R.id.to_country);
            mFromTime = itemView.findViewById(R.id.from_time);
            mToTime = itemView.findViewById(R.id.to_time);

            itemView.setOnClickListener(this);
        }

        void bindTo(SearchItem searchItem) {
            mAirName.setText(searchItem.getmAirName());
            mPrice.setText(searchItem.getmPrice());
            mFromCountry.setText(searchItem.getmFromCountry());
            mToCountry.setText(searchItem.getmToCountry());
            mFromTime.setText(searchItem.getmFromTime());
            mToTime.setText(searchItem.getmToTime());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(mContext,"yes",Toast.LENGTH_LONG).show();
        }
    }
}
