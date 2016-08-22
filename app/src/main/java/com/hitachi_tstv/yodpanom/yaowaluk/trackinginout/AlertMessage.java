package com.hitachi_tstv.yodpanom.yaowaluk.trackinginout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by musz on 8/22/2016.
 */
public class AlertMessage {
    public void myDialog(Context context, String strTitle, String strMessage){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.nobita48);
        builder.setTitle(strTitle);
        builder.setMessage(strMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
            }
        });
        builder.show();
    }

}//Main class
