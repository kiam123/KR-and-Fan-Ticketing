package ncku.edu.tw.kr_and_fan_ticketing.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ncku.edu.tw.kr_and_fan_ticketing.Data.SearchItem;
import ncku.edu.tw.kr_and_fan_ticketing.Fragment.FavoriteFragment;
import ncku.edu.tw.kr_and_fan_ticketing.Fragment.SearchSubscriptionDiaglogFragment;
import ncku.edu.tw.kr_and_fan_ticketing.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    Context mContext;
    ArrayList<SearchItem> mSearchItems;
    SearchItem mSearchItem;

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
        ImageView imageView;
        ImageView imgAirName;

        ViewHolder(View itemView) {
            super(itemView);

            mAirName = itemView.findViewById(R.id.air_name);
            mPrice = itemView.findViewById(R.id.price);
            mFromCountry = itemView.findViewById(R.id.from_country);
            mToCountry = itemView.findViewById(R.id.to_country);
            mFromTime = itemView.findViewById(R.id.from_time);
            mToTime = itemView.findViewById(R.id.to_time);
            imageView = itemView.findViewById(R.id.imageView);
            imgAirName = itemView.findViewById(R.id.img_air_name);

            itemView.setOnClickListener(this);
        }

        void bindTo(SearchItem searchItem) {
            mAirName.setText(searchItem.getmAirName());
            mPrice.setText(searchItem.getmPrice());
            mFromCountry.setText(searchItem.getmFromCountry());
            mToCountry.setText(searchItem.getmToCountry());
            mFromTime.setText(searchItem.getmFromTime());
            mToTime.setText(searchItem.getmToTime());
            Log.v("llll", searchItem.getmUrlImage());
            Log.d("not db", String.valueOf(FavoriteFragment.getFavoriteList().size()));

            Boolean flag = false;
            String targetNow = searchItem.getmFromCountry() + searchItem.getmToCountry() + searchItem.getmDate();
            String planeNow = searchItem.getmAirName();
            String fromTimeNow = searchItem.getmFromTime();
            for (String check : FavoriteFragment.getFavoriteList()) {
                String[] toCheck = check.split(",");
                if (toCheck[0].equals(targetNow)) {
                    if (toCheck[1].equals(planeNow) && toCheck[2].equals(fromTimeNow)) {
                        flag = true;
                    }
                }
            }
            if(flag) {
                imageView.setImageResource(R.drawable.subscription);
            } else {
                imageView.setImageResource(R.drawable.no_subscription);
            }
            if(!searchItem.getmUrlImage().trim().equals("")){
                Log.v("aaaa", searchItem.getmUrlImage());
                Picasso.with(mContext).load(searchItem.getmUrlImage()).into(imgAirName);
            }
        }

        @Override
        public void onClick(View view) {
            mSearchItem = mSearchItems.get(getLayoutPosition());

            SearchSubscriptionDiaglogFragment searchSubscriptionDiaglogFragment = SearchSubscriptionDiaglogFragment.newInstance(mSearchItem);
            searchSubscriptionDiaglogFragment.show(((AppCompatActivity)mContext).getSupportFragmentManager(), "search");
        }
    }
}
