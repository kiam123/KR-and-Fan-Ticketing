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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import javax.annotation.Nullable;

import ncku.edu.tw.kr_and_fan_ticketing.Activity.MainActivity;
import ncku.edu.tw.kr_and_fan_ticketing.Adapter.FavoriteAdapter;
import ncku.edu.tw.kr_and_fan_ticketing.Data.FavoriteItem;
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
    static ListenerRegistration registration;

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
//        ckeckSubscribe();
        dbListener();
    }


    public static void dbListener(){
        db = FirebaseFirestore.getInstance();
        final String path = "user/" + MainActivity.userName + "/subscribe";
        registration = db.collection(path)
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.d("dbListener", "Listen failed.");
                        return;
                    }
                    Log.d("dbListener", "change~" + MainActivity.userName);

                    mFavoriteItems.clear();
//                    for (QueryDocumentSnapshot doc : value) {
//                        if (doc.getId() != null) {
//                            Log.d("dbListener",doc.getId());
//                            setItems(doc);
//                        }
//                    }

                    db.collection(path)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()){
                                        for (QueryDocumentSnapshot doc : task.getResult()) {
                                            Log.d("dbListener",doc.getId());
                                            setItems(doc);
                                        }
                                    }
                                }
                            });
                }

                public void setItems(QueryDocumentSnapshot doc){
                    String plane = doc.get("plane").toString();
                    String price = doc.get("price").toString();
                    String flyTime = doc.get("flyTime").toString();
                    String landTime = doc.get("landTime").toString();
                    String ori = doc.get("ori").toString();
                    String dst = doc.get("dst").toString();
                    String fromPrice = doc.get("fromPrice").toString();
                    String toPrice = doc.get("toPrice").toString();
                    mFavoriteItems.add(new FavoriteItem(plane,price,ori,dst,flyTime,landTime,fromPrice,toPrice));
                    mFavoriteAdapter.notifyDataSetChanged();
                }
            });
    }

    public static void removeSnapListener(){
        mFavoriteItems.clear();
        mFavoriteAdapter.notifyDataSetChanged();
        registration.remove();
    }
}
