package ncku.edu.tw.kr_and_fan_ticketing.Fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ncku.edu.tw.kr_and_fan_ticketing.Activity.MainActivity;
import ncku.edu.tw.kr_and_fan_ticketing.Activity.SearchActivity;
import ncku.edu.tw.kr_and_fan_ticketing.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    Intent intent;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        initView(viewGroup);
        TextView tv_name = viewGroup.findViewById(R.id.tv_name);
        String nameFormat = "Account : " + MainActivity.userName;
        if (MainActivity.userName == "guest") {
            nameFormat = nameFormat + " (You can change it on profile page)";
        }
        tv_name.setText(nameFormat);


        return viewGroup;
    }

    public void initView(ViewGroup viewGroup){
        Button button = (Button) viewGroup.findViewById(R.id.button);
        final EditText edit_ori = viewGroup.findViewById(R.id.edit_ori);
        final EditText edit_dst = viewGroup.findViewById(R.id.edit_dst);
        final EditText edit_date = viewGroup.findViewById(R.id.edit_date);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("date",view2String(edit_date));
                intent.putExtra("ori",view2String(edit_ori));
                intent.putExtra("dst",view2String(edit_dst));
                getActivity().startActivity(intent);

            }

            public String view2String(EditText edt){
                String v2S = edt.getText().toString();
                return  v2S;
            }
        });
    }


}
