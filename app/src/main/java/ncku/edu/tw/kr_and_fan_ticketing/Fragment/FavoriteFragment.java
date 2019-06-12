package ncku.edu.tw.kr_and_fan_ticketing.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import ncku.edu.tw.kr_and_fan_ticketing.Activity.MainActivity;
import ncku.edu.tw.kr_and_fan_ticketing.Adapter.FavoriteAdapter;
import ncku.edu.tw.kr_and_fan_ticketing.Adapter.SearchAdapter;
import ncku.edu.tw.kr_and_fan_ticketing.Data.FavoriteItem;
import ncku.edu.tw.kr_and_fan_ticketing.Data.SearchItem;
import ncku.edu.tw.kr_and_fan_ticketing.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    RecyclerView mFavoriteRecyclerView;
    static FavoriteAdapter mFavoriteAdapter;
    static ArrayList<FavoriteItem> mFavoriteItems;
    static String showPath = "";
    static FirebaseFirestore db;

    public FavoriteFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_favorite, container, false);
        initView(viewGroup);

        return viewGroup;
    }

    public void initView(ViewGroup viewGroup){
        mFavoriteRecyclerView = (RecyclerView) viewGroup.findViewById(R.id.recyclerview);
        mFavoriteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFavoriteItems = new ArrayList<>();

        mFavoriteAdapter = new FavoriteAdapter(getActivity(), mFavoriteItems);
        mFavoriteRecyclerView.setAdapter(mFavoriteAdapter);

//        mFavoriteItems.add(new FavoriteItem("Qatar Air","$600","DAC","SIN","17:40","23:30","$100","$500"));
//        mFavoriteItems.add(new FavoriteItem("Biman B.","$520","DAC","SIN","17:40","23:30","$200","$700"));
//        mFavoriteAdapter.notifyDataSetChanged();
        showSubscribe();
    }

    public static void showSubscribe(){
        mFavoriteItems.clear();
        db = FirebaseFirestore.getInstance();
        String subPath = "/user/" + MainActivity.userName + "/subscribe";
        db.collection(subPath)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("db in sub", document.getId());
                                String plane = document.get("plane").toString();
                                String flyTime = document.get("flyTime").toString();
                                String target = document.get("target").toString();

                                Log.d("db in favorite",target);
                                searchSubinDb(target,plane,flyTime);
                            }
                            mFavoriteAdapter.notifyDataSetChanged();
                            Log.d("db in sub","search sub finished");
                        } else {
                            Log.d("db in sub", "Error getting documents: ");
                        }
                    }
                });

    }
    public static void searchSubinDb(String target, final String subPlane, final String subFlyTime){
        showPath = "/searchResult/" + target + "/tickets";
        Log.d("db in favor",showPath);
        db.collection(showPath)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.get("plane").toString().equals(subPlane) && document.get("flyTime").toString().equals(subFlyTime)){
                                    String plane = document.get("plane").toString();
                                    String price = document.get("price").toString();
                                    String flyTime = document.get("flyTime").toString();
                                    String landTime = document.get("landTime").toString();
                                    String ori = document.get("ori").toString();
                                    String dst = document.get("dst").toString();
                                    mFavoriteItems.add(new FavoriteItem(plane,price,ori,dst,flyTime,landTime,"$100","$500"));
                                    mFavoriteAdapter.notifyDataSetChanged();
//                                    Log.d("debug","here");
                                }
//                                Log.d("debug",document.get("plane").toString()+document.get("flyTime").toString());
//                                Log.d("debug","here?"+subPlane+subFlyTime);
                            }
                            Log.d("db in search","finished");
                        } else {
                            Log.d("db in search", "Error getting documents: ");
                        }
                    }
                });
    }

}
