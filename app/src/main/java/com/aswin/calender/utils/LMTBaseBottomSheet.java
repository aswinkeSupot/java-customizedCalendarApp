package com.aswin.calender.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class LMTBaseBottomSheet extends BottomSheetDialogFragment {

    public Vibrator vibe;
    public   LMTProgress lmtProgress;
    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Window window = getDialog().getWindow();
//            window.findViewById(com.google.android.material.R.id.container).setFitsSystemWindows(false);
            // dark navigation bar icons
//            View decorView = window.getDecorView();
//            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        appPreferences = new AppPreferences(getContext(), getString(R.string.app_name));
//        appPreferencesGlobal = new AppPreferences(getContext(), getString(R.string.app_name) + "Global");
//        appPreferencesOneTime = new AppPreferences(getContext(), getString(R.string.app_name) + "OneTime");
        vibe = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        lmtProgress = new LMTProgress();
    }

    public void showProgress(){
        lmtProgress.show(getChildFragmentManager(),"LMTProgress");
    }

    public void hideProgress(){
        lmtProgress.dismiss();
    }


    public void hideKeyboard(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
