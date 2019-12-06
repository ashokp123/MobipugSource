package com.o3sa.mobipugapp.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Kiran on 02-11-2018.
 */

public class CustomEditTextGibson extends EditText {

    public CustomEditTextGibson(Context context) {

        super(context);
        init();

    }

    public CustomEditTextGibson(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public CustomEditTextGibson(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {

        //Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"ptdin.ttf");
        //Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"robolight.ttf");

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"OpenSans-Semibold.ttf");
        setTypeface(tf);

    }

}

