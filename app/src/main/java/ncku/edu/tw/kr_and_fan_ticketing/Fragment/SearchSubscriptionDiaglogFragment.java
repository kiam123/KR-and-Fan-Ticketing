package ncku.edu.tw.kr_and_fan_ticketing.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import ncku.edu.tw.kr_and_fan_ticketing.Activity.MainActivity;
import ncku.edu.tw.kr_and_fan_ticketing.Data.SearchCallBack;
import ncku.edu.tw.kr_and_fan_ticketing.Data.SearchItem;
import ncku.edu.tw.kr_and_fan_ticketing.R;

public class SearchSubscriptionDiaglogFragment extends DialogFragment {
    public SearchCallBack searchCallBack;
    SearchItem searchItem;

    public static SearchSubscriptionDiaglogFragment newInstance(SearchItem searchItem) {
        SearchSubscriptionDiaglogFragment searchSubscriptionDiaglogFragment = new SearchSubscriptionDiaglogFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("SearchItem", searchItem);
        searchSubscriptionDiaglogFragment.setArguments(bundle);

        return searchSubscriptionDiaglogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {



        final EditText edtFromPrice;
        final EditText edtToPrice;
        searchItem = (SearchItem) getArguments().getSerializable("SearchItem");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_search_subscription, null);
        edtFromPrice = (EditText) view.findViewById(R.id.edt_from_price);
        edtToPrice = (EditText) view.findViewById(R.id.edt_to_price);

        builder.setView(view).setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

                if(edtFromPrice.getText().toString().trim().equals("") && edtToPrice.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Please input price", Toast.LENGTH_LONG).show();
                    return;
                }

//                boolean flag = searchHelper.insertData(searchItem.getmAirName(), searchItem.getmFromCountry(), searchItem.getmToCountry(),
//                        searchItem.getmFromTime(), searchItem.getmToTime(), searchItem.getmPrice(), edtFromPrice.getText().toString(),
//                        edtToPrice.getText().toString());
                String fromPrice = edtFromPrice.getText().toString();
                String toPrice = edtToPrice.getText().toString();
                toDatabase(searchItem,fromPrice,toPrice);

//                if(flag == true) {
                Log.d("search",searchItem.getmId());
                Toast.makeText(getActivity(), "insert", Toast.LENGTH_LONG).show();
//                }
                searchCallBack.callbackFragment();
            }
        }).setNegativeButton("取消", null);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        searchCallBack = (SearchCallBack) context;
    }

    public void toDatabase(SearchItem searchItem,String fromPrice,String toPrice){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String subPath = "/user/" + MainActivity.userName + "/subscribe";
        String subId = searchItem.getmId();
        Map<String, String> query = getQuery(searchItem);
        query.put("fromPrice","$" + fromPrice);
        query.put("toPrice","$" +toPrice);
        db.collection(subPath).document(subId)
                .set(query)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("db in dia","success !");
                    }
                });
    }
    private Map<String, String> getQuery(SearchItem searchItem) {
        Map<String, String> inputQuery = new HashMap<>();
        String target = searchItem.getmFromCountry() + searchItem.getmToCountry() + searchItem.getmDate();
        inputQuery.put("date", searchItem.getmDate());
        inputQuery.put("ori", searchItem.getmFromCountry());
        inputQuery.put("dst", searchItem.getmToCountry());
        inputQuery.put("flyTime",searchItem.getmFromTime());
        inputQuery.put("landTime",searchItem.getmToTime());
        inputQuery.put("plane",searchItem.getmAirName());
        inputQuery.put("target",target);
        inputQuery.put("price",searchItem.getmPrice());
        return inputQuery;
    }
}
