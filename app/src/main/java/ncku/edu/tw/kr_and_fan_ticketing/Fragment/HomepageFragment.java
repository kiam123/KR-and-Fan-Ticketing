package ncku.edu.tw.kr_and_fan_ticketing.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import ncku.edu.tw.kr_and_fan_ticketing.Activity.MainActivity;
import ncku.edu.tw.kr_and_fan_ticketing.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomepageFragment extends Fragment {

    String currentName;
    EditText edit_name;
    TextView tv_current,tv_changeText;

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
        tv_changeText = viewGroup.findViewById(R.id.tv_changeText);
        tv_changeText.setText("");
        currentName = "Current account : " + MainActivity.userName;
        tv_current.setText(currentName);

        edit_name = viewGroup.findViewById(R.id.edit_name);
        edit_name.setText(MainActivity.userName);

        Button btn_change = viewGroup.findViewById(R.id.btn_change);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.userName = edit_name.getText().toString();

                tv_changeText.setText("Waiting for database...");

                // check database
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> exist = new HashMap<>();
                exist.put("exist",true);
                db.collection("/user/").document(MainActivity.userName)
                        .set(exist)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                currentName = "Current account : " + MainActivity.userName;
                                tv_current.setText(currentName);
                                tv_changeText.setText("User ckecked !");
                                FavoriteFragment.removeSnapListener();
                                FavoriteFragment.dbListener();
                            }
                        });
            }
        });

    }

}
