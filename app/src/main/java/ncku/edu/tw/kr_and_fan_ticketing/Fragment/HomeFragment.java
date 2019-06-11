package ncku.edu.tw.kr_and_fan_ticketing.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import ncku.edu.tw.kr_and_fan_ticketing.Activity.SearchActivity;
import ncku.edu.tw.kr_and_fan_ticketing.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        initView(viewGroup);

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
//                Intent intent = new Intent(getActivity(), SearchActivity.class);
//                getActivity().startActivity(intent);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String,String> query = new HashMap<>();
//                String date = "2019-06-30";
//                String ori = "TPE";
//                String dst = "GUM";
                String date = edit_date.getText().toString();
                String ori = edit_ori.getText().toString();
                String dst = edit_dst.getText().toString();
                query.put("date", date);
                query.put("ori", ori);
                query.put("dst", dst);
                String user = "developer";
                String path = "/user/" + user + "/query/";
                String id = ori + dst + date;
                String msg = "run : " + id;
                Log.d("state",msg);
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
        });
    }

}
