package ncku.edu.tw.kr_and_fan_ticketing.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ncku.edu.tw.kr_and_fan_ticketing.Activity.MainActivity;
import ncku.edu.tw.kr_and_fan_ticketing.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomepageFragment extends Fragment {

    String currentName;
    EditText edit_name;
    TextView tv_current;

    public HomepageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_homepage, container, false);
        initView(viewGroup);

        return viewGroup;
    }

    public void initView(ViewGroup viewGroup){
        tv_current = viewGroup.findViewById(R.id.tv_current);
        currentName = "Current account : " + MainActivity.userName;
        tv_current.setText(currentName);

        edit_name = viewGroup.findViewById(R.id.edit_name);

        Button btn_change = viewGroup.findViewById(R.id.btn_change);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.userName = edit_name.getText().toString();
                currentName = "Current account : " + MainActivity.userName;
                tv_current.setText(currentName);
            }
        });

    }

}
