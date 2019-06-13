package ncku.edu.tw.kr_and_fan_ticketing.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import ncku.edu.tw.kr_and_fan_ticketing.R;

public class FavoriteChangeDialogFragment extends DialogFragment {
    public FavoriteCallBack favoriteCallBack;

    public void setListener(FavoriteCallBack favoriteCallBack) {
        this.favoriteCallBack = favoriteCallBack;
    }
    public static interface FavoriteCallBack {
        public void changeData(String toRangePrice, String fromRangePrice);
    }

    public static FavoriteChangeDialogFragment newInstance(String toPrice, String fromPrice) {
        FavoriteChangeDialogFragment favoriteChangeDialogFragment = new FavoriteChangeDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putString("toRangePrice", toPrice);
        bundle.putString("fromRangePrice", fromPrice);
        favoriteChangeDialogFragment.setArguments(bundle);

        return favoriteChangeDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final EditText toRangePrice;
        final EditText fromRangePrice;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.delete_dialog_favorite_subscription, null);
        toRangePrice = (EditText)view.findViewById(R.id.editToRangePrice);
        fromRangePrice = (EditText)view.findViewById(R.id.editFromRangePrice);
        toRangePrice.setText(getArguments().getString("toRangePrice"));
        fromRangePrice.setText(getArguments().getString("fromRangePrice"));


        builder.setView(view).setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                favoriteCallBack.changeData(toRangePrice.getText().toString(), fromRangePrice.getText().toString());
            }
        }).setNegativeButton("取消", null);
        return builder.create();
    }

}