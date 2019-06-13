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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ncku.edu.tw.kr_and_fan_ticketing.Activity.MainActivity;
import ncku.edu.tw.kr_and_fan_ticketing.Data.FavoriteItem;
import ncku.edu.tw.kr_and_fan_ticketing.Fragment.FavoriteChangeDialogFragment;
import ncku.edu.tw.kr_and_fan_ticketing.R;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    Context mContext;
    ArrayList<FavoriteItem> mSearchItems;
    FavoriteItem mSearchItem;
    FirebaseFirestore db;

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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, FavoriteChangeDialogFragment.FavoriteCallBack {
        TextView mAirName;
        TextView mPrice;
        TextView mFromCountry;
        TextView mToCountry;
        TextView mFromTime;
        TextView mToTime;
        TextView mFromRangePrice;
        TextView mToRangePrice;
        ImageView imgDelete;
        ImageView imgAirName;
        TextView txvDate;

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
            imgAirName = itemView.findViewById(R.id.img_air_name);
            imgDelete = itemView.findViewById(R.id.img_delete);
            txvDate = itemView.findViewById(R.id.txv_date);
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db = FirebaseFirestore.getInstance();
                    FavoriteItem delItem = mSearchItems.get(getLayoutPosition());
                    String target = getDelTarget(delItem);
                    Log.d("db in favorite",delItem.getmAirName());
                    String delPath = "user/" + MainActivity.userName + "/subscribe/" + target;
                    db.document(delPath)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("db in favorite","delete success");
                                    // because realtime no need to remove mSearchItems
//                                    mSearchItems.remove(getLayoutPosition());
                                    notifyDataSetChanged();
                                }
                            });
                }
            });

            itemView.setOnClickListener(this);
        }

        public String getDelTarget(FavoriteItem delItem){
            return delItem.getmFromCountry() + delItem.getmToCountry() + delItem.getmDate()
                    + delItem.getmAirName() + delItem.getmFromTime();
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
            txvDate.setText(searchItem.getmDate());
            if(!searchItem.getmUrlImage().trim().equals("")){
                Log.v("aaaa", searchItem.getmUrlImage());
                Picasso.with(mContext).load(searchItem.getmUrlImage()).into(imgAirName);
            }
        }

        @Override
        public void onClick(View view) {
            mSearchItem = mSearchItems.get(getLayoutPosition());
            FavoriteChangeDialogFragment favoriteChangeDialogFragment = FavoriteChangeDialogFragment.newInstance(mFromRangePrice.getText().toString(), mToRangePrice.getText().toString());
            favoriteChangeDialogFragment.setListener(this);
            favoriteChangeDialogFragment.show(((AppCompatActivity)mContext).getSupportFragmentManager(), "favorite");
        }

        @Override
        public void changeData(String toRangePrice, String fromRangePrice) {
            mSearchItems.get(getLayoutPosition()).setmToRangePrice(toRangePrice);
            mSearchItems.get(getLayoutPosition()).setmToRangePrice(fromRangePrice);
            notifyDataSetChanged();
        }


    }


}
