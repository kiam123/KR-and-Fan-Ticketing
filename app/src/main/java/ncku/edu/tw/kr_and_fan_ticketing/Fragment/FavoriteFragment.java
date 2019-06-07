package ncku.edu.tw.kr_and_fan_ticketing.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

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
    FavoriteAdapter mFavoriteAdapter;
    ArrayList<FavoriteItem> mFavoriteItems;

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

        mFavoriteItems.add(new FavoriteItem("Qatar Air","$600","DAC","SIN","17:40","23:30"));
        mFavoriteItems.add(new FavoriteItem("Biman B.","$520","DAC","SIN","17:40","23:30"));
        mFavoriteAdapter.notifyDataSetChanged();
    }

}
