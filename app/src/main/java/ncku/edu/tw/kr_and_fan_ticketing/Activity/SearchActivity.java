package ncku.edu.tw.kr_and_fan_ticketing.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import ncku.edu.tw.kr_and_fan_ticketing.Adapter.SearchAdapter;
import ncku.edu.tw.kr_and_fan_ticketing.Data.SearchItem;
import ncku.edu.tw.kr_and_fan_ticketing.R;

public class SearchActivity extends AppCompatActivity {
    RecyclerView SearchRecyclerView;
    SearchAdapter searchAdapter;
    ArrayList<SearchItem> mSearchItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        SearchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchItems = new ArrayList<>();

        searchAdapter = new SearchAdapter(this, mSearchItems);
        SearchRecyclerView.setAdapter(searchAdapter);
        initData();
    }

    public void initData(){
        mSearchItems.add(new SearchItem("Qatar Air","$600","DAC","SIN","17:40","23:30"));
        mSearchItems.add(new SearchItem("Biman B.","$520","DAC","SIN","17:40","23:30"));
        searchAdapter.notifyDataSetChanged();
    }
}
