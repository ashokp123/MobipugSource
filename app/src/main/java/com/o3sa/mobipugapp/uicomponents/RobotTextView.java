package com.o3sa.mobipugapp.uicomponents;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by android-4 on 1/24/2017.
 */

public class RobotTextView extends TextView {

    public RobotTextView(Context context) {

        super(context);
        init();

    }

    public RobotTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public RobotTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {

        //Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"ptdin.ttf");
        //Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"robolight.ttf");

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"Roboto-Medium.ttf");
        setTypeface(tf);

    }

}
