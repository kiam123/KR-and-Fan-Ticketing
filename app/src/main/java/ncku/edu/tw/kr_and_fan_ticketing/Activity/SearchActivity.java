package ncku.edu.tw.kr_and_fan_ticketing.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ncku.edu.tw.kr_and_fan_ticketing.Adapter.SearchAdapter;
import ncku.edu.tw.kr_and_fan_ticketing.Data.SearchCallBack;
import ncku.edu.tw.kr_and_fan_ticketing.Data.SearchItem;
import ncku.edu.tw.kr_and_fan_ticketing.R;
import ncku.edu.tw.kr_and_fan_ticketing.Service.SearchHelper;

public class SearchActivity extends AppCompatActivity implements SearchCallBack {
    RecyclerView SearchRecyclerView;
    SearchAdapter searchAdapter;
    ArrayList<SearchItem> mSearchItems;
    Map<String, String> query;
    FirebaseFirestore db;
    String date,ori,dst,id,path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        SearchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchItems = new ArrayList<>();

        searchAdapter = new SearchAdapter(this, mSearchItems);
        SearchRecyclerView.setAdapter(searchAdapter);

        // get intent data
        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        ori = intent.getStringExtra("ori");
        dst = intent.getStringExtra("dst");

        // get firebase
        db = FirebaseFirestore.getInstance();
        initData();
    }

    public Map<String, String> getQuery(String date, String ori, String dst) {
        Map<String, String> inputQuery = new HashMap<>();
        inputQuery.put("date", date);
        inputQuery.put("ori", ori);
        inputQuery.put("dst", dst);
        return inputQuery;
    }

    public void initData() {
        id = ori + dst + date;
        if (id != "") {

            // set path
            final String searchPath = "/searchResult/";
            final String showPath = "/searchResult/" + id + "/tickets/";
            path = "/query/";


            query = getQuery(date, ori, dst);

            DocumentReference queryRef = db.collection(searchPath).document(id);
            Log.d("state", "find : " + id);
            queryRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("db", "db has result:" + searchPath  + id);
                            mSearchItems.add(new SearchItem("Wait for database...","","","","","","",false));
                            searchAdapter.notifyDataSetChanged();
                            db.collection(showPath)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                mSearchItems.remove(0);
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    Log.d("db", document.get("plane").toString());
                                                    String plane = document.get("plane").toString();
                                                    String price = document.get("price").toString();
                                                    String flyTime = document.get("flyTime").toString();
                                                    String landTime = document.get("landTime").toString();
                                                    String date = document.get("date").toString();

                                                    SearchHelper searchHelper = new SearchHelper(SearchActivity.this);
                                                    Cursor cus = searchHelper.getAllData(plane, ori, dst, flyTime, landTime);
                                                    if(cus.getCount() > 1){
                                                        while(cus.moveToNext()) {
                                                            Log.v("ttt", cus.getString(0)+" "+plane);
                                                            Log.v("ttt", cus.getString(1)+" "+plane);
                                                            Log.v("ttt", cus.getString(2)+" "+plane);
                                                            Log.v("ttt", cus.getString(3)+" "+plane);
                                                            Log.v("ttt", cus.getString(4)+" "+plane);
                                                        }
                                                    }

                                                    mSearchItems.add(new SearchItem(plane,date, price, ori, dst, flyTime, landTime, false));
                                                }
                                                Log.d("db","search finished");
                                                searchAdapter.notifyDataSetChanged();
                                            } else {
                                                Log.d("db", "Error getting documents: ");
                                            }
                                        }
                                    });
                        } else {

                            mSearchItems.add(new SearchItem("Send query to server","","","turn back after few minute","","","",false));
                            searchAdapter.notifyDataSetChanged();
                            Log.d("db", "No such document:" + searchPath  + id);
                            Log.d("state","query : " + id);

                            db.collection(path).document(id)
                                    .set(query)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("db", "DocumentSnapshot successfully written!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("db", "Error writing document");
                                        }
                                    });
                        }
                    } else {
                        Log.d("db", "get failed");
                    }
                }
            });
        } else {
            mSearchItems.add(new SearchItem("Qatar Air","", "$600", "DAC", "SIN", "17:40", "23:30", false));
            mSearchItems.add(new SearchItem("Biman B.","", "$520", "DAC", "SIN", "17:40", "23:30", false));
        }
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);


    }

    @Override
    public void callbackFragment() {
        Toast.makeText(this,"yes", Toast.LENGTH_LONG).show();
    }
}
