package ncku.edu.tw.kr_and_fan_ticketing.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ncku.edu.tw.kr_and_fan_ticketing.Data.FavoriteItem;
import ncku.edu.tw.kr_and_fan_ticketing.Data.SearchItem;
import ncku.edu.tw.kr_and_fan_ticketing.Fragment.FavoriteDialogFragment;
import ncku.edu.tw.kr_and_fan_ticketing.Fragment.SearchSubscriptionDiaglogFragment;
import ncku.edu.tw.kr_and_fan_ticketing.R;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>{

    Context mContext;
    ArrayList<FavoriteItem> mSearchItems;

    public FavoriteAdapter(Context context, ArrayList<FavoriteItem> searchItems) {
        this.mContext = context;
        this.mSearchItems = searchItems;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new FavoriteAdapter.ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.favorite_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder viewHolder, int position) {
        FavoriteItem searchItem = mSearchItems.get(position);
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
        TextView mFromRangePrice;
        TextView mToRangePrice;

        ViewHolder(View itemView) {
            super(itemView);

            mAirName = itemView.findViewById(R.id.air_name);
            mPrice = itemView.findViewById(R.id.price);
            mFromCountry = itemView.findViewById(R.id.from_country);
            mToCountry = itemView.findViewById(R.id.to_country);
            mFromTime = itemView.findViewById(R.id.from_time);
            mToTime = itemView.findViewById(R.id.to_time);
            mFromRangePrice = itemView.findViewById(R.id.from_range_price);
            mToRangePrice = itemView.findViewById(R.id.to_range_price);

            itemView.setOnClickListener(this);
        }

        void bindTo(FavoriteItem searchItem) {
            mAirName.setText(searchItem.getmAirName());
            mPrice.setText(searchItem.getmPrice());
            mFromCountry.setText(searchItem.getmFromCountry());
            mToCountry.setText(searchItem.getmToCountry());
            mFromTime.setText(searchItem.getmFromTime());
            mToTime.setText(searchItem.getmToTime());
            mFromRangePrice.setText(searchItem.getmFromRangePrice());
            mToRangePrice.setText(searchItem.getmToRangePrice());
        }

        @Override
        public void onClick(View view) {
            FavoriteDialogFragment favoriteDialogFragment = FavoriteDialogFragment.newInstance(mFromRangePrice.getText().toString(), mToRangePrice.getText().toString());
            favoriteDialogFragment.show(((AppCompatActivity)mContext).getSupportFragmentManager(), "favorite");
        }
    }
}
