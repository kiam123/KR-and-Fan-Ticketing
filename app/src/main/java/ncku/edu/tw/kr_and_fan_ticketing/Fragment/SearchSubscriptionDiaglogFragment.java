package ncku.edu.tw.kr_and_fan_ticketing.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ncku.edu.tw.kr_and_fan_ticketing.Data.SearchCallBack;
import ncku.edu.tw.kr_and_fan_ticketing.Data.SearchItem;
import ncku.edu.tw.kr_and_fan_ticketing.R;
import ncku.edu.tw.kr_and_fan_ticketing.Service.SearchHelper;

public class SearchSubscriptionDiaglogFragment extends DialogFragment {
    public SearchCallBack searchCallBack;

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
        final SearchItem searchItem = (SearchItem) getArguments().getSerializable("SearchItem");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_search_subscription, null);
        edtFromPrice = (EditText) view.findViewById(R.id.edt_from_price);
        edtToPrice = (EditText) view.findViewById(R.id.edt_to_price);

        builder.setView(view).setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                SearchHelper searchHelper = new SearchHelper(getActivity());

                if(edtFromPrice.getText().toString().trim().equals("") && edtToPrice.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Please input price", Toast.LENGTH_LONG).show();
                    return;
                }

                boolean flag = searchHelper.insertData(searchItem.getmAirName(), searchItem.getmFromCountry(), searchItem.getmToCountry(),
                        searchItem.getmFromTime(), searchItem.getmToTime(), searchItem.getmPrice(), edtFromPrice.getText().toString(),
                        edtToPrice.getText().toString());

                if(flag == true) {
                    Toast.makeText(getActivity(), "insert", Toast.LENGTH_LONG).show();
                }
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
}
