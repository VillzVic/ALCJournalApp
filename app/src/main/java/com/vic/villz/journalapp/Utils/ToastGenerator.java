package com.vic.villz.journalapp.Utils;

import android.content.Context;
import android.widget.Toast;

public class ToastGenerator {

    public static void GenerateToast(Context context, String message, int duration){
        Toast.makeText(context, message, duration).show();
    }
}
