package com.o3sa.mobipugapp.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Kiran on 01-11-2018.
 */

public class CustomEditText extends EditText {

    public CustomEditText(Context context) {

        super(context);
        init();

    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {

        //Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"ptdin.ttf");
        //Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"robolight.ttf");

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"Roboto-Regular.ttf");
        setTypeface(tf);

    }

}

