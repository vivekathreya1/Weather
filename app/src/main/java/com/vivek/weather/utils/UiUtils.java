package com.vivek.weather.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.vivek.weather.R;


public class UiUtils {


    public static void showAlertDialog(final CustomAlertDialogListener listener, Context context, String title, String message, String posText, String negText, int butBg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setPositiveButton(posText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) {
                    listener.onOkClicked();
                }

                dialog.dismiss();
            }
        });
        builder.setNegativeButton(negText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) {
                    listener.onCancelClicked();
                }
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(butBg);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(butBg);

    }


}
