package ncku.edu.tw.kr_and_fan_ticketing.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ncku.edu.tw.kr_and_fan_ticketing.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_favorite, container, false);
        initView(viewGroup);

        return viewGroup;
    }

    public void initView(ViewGroup viewGroup){

    }

}
