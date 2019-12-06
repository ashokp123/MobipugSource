package com.o3sa.mobipugapp.servicesparsing;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.uicomponents.RobotTextView;

public class CustomProgressbar {

	public static Dialog dialog;


	public static void Progressbarshow(Activity activity) {

		dialog = new Dialog(activity);
		dialog.getWindow();
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.customprogressbar);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#59FFFFFF")));
		//Display display = activity.getWindowManager().getDefaultDisplay();
		//int width = display.getWidth();


		dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		dialog.show();

	}
	public static void Progressbarcancel(Context context) {

		if (dialog != null) {
			dialog.dismiss();
		}

	}



}
