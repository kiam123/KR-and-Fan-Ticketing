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

public class FavoriteDialogFragment extends DialogFragment {

    public static FavoriteDialogFragment newInstance(String toPrice, String fromPrice) {
        FavoriteDialogFragment favoriteDialogFragment = new FavoriteDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putString("toRangePrice", toPrice);
        bundle.putString("fromRangePrice", fromPrice);
        favoriteDialogFragment.setArguments(bundle);

        return favoriteDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        EditText toRangePrice;
        EditText fromRangePrice;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_favorite_subscription, null);
        toRangePrice = (EditText)view.findViewById(R.id.editToRangePrice);
        fromRangePrice = (EditText)view.findViewById(R.id.editFromRangePrice);
        toRangePrice.setText(getArguments().getString("toRangePrice"));
        fromRangePrice.setText(getArguments().getString("fromRangePrice"));


        builder.setView(view).setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        }).setNegativeButton("取消", null);
        return builder.create();
    }
}