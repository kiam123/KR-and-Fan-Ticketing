package ncku.edu.tw.kr_and_fan_ticketing.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import ncku.edu.tw.kr_and_fan_ticketing.Activity.MainActivity;
import ncku.edu.tw.kr_and_fan_ticketing.Data.FavoriteItem;
import ncku.edu.tw.kr_and_fan_ticketing.R;

public class FavoriteChangeDialogFragment extends DialogFragment {
    public FavoriteCallBack favoriteCallBack;
    FavoriteItem favorItem;

    public void setListener(FavoriteCallBack favoriteCallBack) {
        this.favoriteCallBack = favoriteCallBack;
    }
    public static interface FavoriteCallBack {
        public void changeData(String toRangePrice, String fromRangePrice);
    }

    public static FavoriteChangeDialogFragment newInstance(String toPrice, String fromPrice, FavoriteItem favorItem) {
        FavoriteChangeDialogFragment favoriteChangeDialogFragment = new FavoriteChangeDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putString("toRangePrice", toPrice);
        bundle.putString("fromRangePrice", fromPrice);
        bundle.putSerializable("favorItem",favorItem);
        favoriteChangeDialogFragment.setArguments(bundle);

        return favoriteChangeDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final EditText toRangePrice;
        final EditText fromRangePrice;

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.delete_dialog_favorite_subscription, null);
        toRangePrice = (EditText)view.findViewById(R.id.editToRangePrice);
        fromRangePrice = (EditText)view.findViewById(R.id.editFromRangePrice);

        favorItem = (FavoriteItem)getArguments().getSerializable("favorItem");
        toRangePrice.setText(getArguments().getString("toRangePrice"));
        fromRangePrice.setText(getArguments().getString("fromRangePrice"));


        builder.setView(view).setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

//                favoriteCallBack.changeData(toRangePrice.getText().toString(), fromRangePrice.getText().toString());
                String toPrice = toRangePrice.getText().toString();
                String fromPrice = fromRangePrice.getText().toString();
                toUserDatabase(favorItem, fromPrice, toPrice);


            }
        }).setNegativeButton("取消", null);
        return builder.create();
    }

    private void toUserDatabase(FavoriteItem favorItem, String fromPrice, String toPrice) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String path = getTarget(favorItem);
        Log.d("database change",path);

        db.document(path)
                .update("fromPrice",fromPrice,"toPrice",toPrice)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("database change","success");
                    }
                });

    }

    private String getTarget(FavoriteItem favorItem) {
        return "user/" + MainActivity.userName + "/subscribe/" + favorItem.getId();
    }


}