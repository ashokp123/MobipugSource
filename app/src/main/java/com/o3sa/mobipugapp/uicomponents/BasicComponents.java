package com.o3sa.mobipugapp.uicomponents;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.o3sa.mobipugapp.storedobjects.StoredObjects;

/**
 * Created by Kiran on 22-09-2018.
 */

public class BasicComponents extends AppCompatActivity {
    Activity activity;
    LinearLayout.LayoutParams params;
    FrameLayout.LayoutParams paramss;


   String bold_fonttype1="Roboto-Bold.ttf",regular_fonttype1="Roboto-Regular.ttf",semibold_fonttype1="Roboto-Medium.ttf";
 // String bold_fonttype1="Gibson-SemiBold.otf",regular_fonttype1="Gibson-Regular.ttf",semibold_fonttype1="Gibson-SemiBold.otf";

    String bold_fonttype2="OpenSans-Semibold.ttf",regular_fonttype2="OpenSans-Semibold.ttf";
    String regular_fonttype3="SFUIText-Regular.ttf";

    public BasicComponents(Activity m_activity) {
        activity=m_activity;
    }

    //Customization of text view
    public void CustomizeTextview(TextView textView, float textsize, int textcolor, String title, String contenttype, int[] marginsarray) {
        //Assigning text to text view
        textView.setText(title);
        //Assigning text size to text view
        textView.setTextSize(textsize);
        //Assigning text color to text view
        textView.setTextColor(activity.getResources().getColor(textcolor));
        //Assigning content values to text view
        //View width @ view gravity @view font type
        String[] contentvalues=contenttype.split("@");
        if(contentvalues[2].equalsIgnoreCase("Bold")&&contentvalues[3].equalsIgnoreCase("1")){
            textView.setTypeface(GetFont(bold_fonttype1,activity));
        }else if(contentvalues[2].equalsIgnoreCase("Normal")&&contentvalues[3].equalsIgnoreCase("1")){
            textView.setTypeface(GetFont(regular_fonttype1,activity));
        }else if(contentvalues[2].equalsIgnoreCase("SemiBold")&&contentvalues[3].equalsIgnoreCase("1")){
            textView.setTypeface(GetFont(semibold_fonttype1,activity));
        }else  if(contentvalues[2].equalsIgnoreCase("Bold")&&contentvalues[3].equalsIgnoreCase("2")){
            textView.setTypeface(GetFont(bold_fonttype2,activity));
        }else if(contentvalues[2].equalsIgnoreCase("Normal")&&contentvalues[3].equalsIgnoreCase("2")){
            textView.setTypeface(GetFont(regular_fonttype2,activity));
        }else if(contentvalues[2].equalsIgnoreCase("Normal")&&contentvalues[3].equalsIgnoreCase("3")){
            textView.setTypeface(GetFont(regular_fonttype3,activity));
        }else{
            textView.setTypeface(GetFont(regular_fonttype1,activity));
        }
        if(contentvalues[0].equalsIgnoreCase("Match")){
           params = new LinearLayout.LayoutParams(Constants.matchParent, Constants.wrapContent);
        }else{
            params = new LinearLayout.LayoutParams(Constants.wrapContent, Constants.wrapContent);
        }

        if(contentvalues[1].equalsIgnoreCase("Center")){
            params.gravity = Constants.center;
        }else  if(contentvalues[1].equalsIgnoreCase("Right")){
            params.gravity = Constants.right;
        }else  if(contentvalues[1].equalsIgnoreCase("Top")){
            params.gravity = Constants.top;
        }else  if(contentvalues[1].equalsIgnoreCase("Left")){
            params.gravity = Constants.left;
        }else  if(contentvalues[1].equalsIgnoreCase("Bottom")){
            params.gravity = Constants.bottom;
        }else{
            params.gravity = Constants.left;
        }
        //left,top,right,bottom
        //Assigning margins to textview
       params.setMargins(StoredObjects.pxFromDp(activity,marginsarray[0]),StoredObjects.pxFromDp(activity,marginsarray[1]),StoredObjects.pxFromDp(activity,marginsarray[2]),StoredObjects.pxFromDp(activity,marginsarray[3]));
        textView.setLayoutParams(params);
    }


    //Customization of text view
    public void CustomizeTextviewFrameLay(TextView textView, float textsize, int textcolor, String title, String contenttype, int[] marginsarray) {
        //Assigning text to text view
        textView.setText(title);
        //Assigning text size to text view
        textView.setTextSize(textsize);
        //Assigning text color to text view
        textView.setTextColor(activity.getResources().getColor(textcolor));
        //Assigning content values to text view
        //View width @ view gravity @view font type
        String[] contentvalues=contenttype.split("@");
        if(contentvalues[2].equalsIgnoreCase("Bold")&&contentvalues[3].equalsIgnoreCase("1")){
            textView.setTypeface(GetFont(bold_fonttype1,activity));
        }else if(contentvalues[2].equalsIgnoreCase("Normal")&&contentvalues[3].equalsIgnoreCase("1")){
            textView.setTypeface(GetFont(regular_fonttype1,activity));
        }else if(contentvalues[2].equalsIgnoreCase("SemiBold")&&contentvalues[3].equalsIgnoreCase("1")){
            textView.setTypeface(GetFont(semibold_fonttype1,activity));
        }else  if(contentvalues[2].equalsIgnoreCase("Bold")&&contentvalues[3].equalsIgnoreCase("2")){
            textView.setTypeface(GetFont(bold_fonttype2,activity));
        }else if(contentvalues[2].equalsIgnoreCase("Normal")&&contentvalues[3].equalsIgnoreCase("2")){
            textView.setTypeface(GetFont(regular_fonttype2,activity));
        }else if(contentvalues[2].equalsIgnoreCase("Normal")&&contentvalues[3].equalsIgnoreCase("3")){
            textView.setTypeface(GetFont(regular_fonttype3,activity));
        }else{
            textView.setTypeface(GetFont(regular_fonttype1,activity));
        }
        if(contentvalues[0].equalsIgnoreCase("Match")){
            paramss = new FrameLayout.LayoutParams(Constants.matchParent, Constants.wrapContent);
        }else{
            paramss = new FrameLayout.LayoutParams(Constants.wrapContent, Constants.wrapContent);
        }

        if(contentvalues[1].equalsIgnoreCase("Center")){
            paramss.gravity = Constants.center;
        }else  if(contentvalues[1].equalsIgnoreCase("Right")){
            paramss.gravity = Constants.right;
        }else  if(contentvalues[1].equalsIgnoreCase("Top")){
            paramss.gravity = Constants.top;
        }else  if(contentvalues[1].equalsIgnoreCase("Left")){
            paramss.gravity = Constants.left;
        }else  if(contentvalues[1].equalsIgnoreCase("Bottom")){
            paramss.gravity = Constants.bottom;
        }else{
            paramss.gravity = Constants.left;
        }
        //left,top,right,bottom
        //Assigning margins to textview
        paramss.setMargins(StoredObjects.pxFromDp(activity,marginsarray[0]),StoredObjects.pxFromDp(activity,marginsarray[1]),StoredObjects.pxFromDp(activity,marginsarray[2]),StoredObjects.pxFromDp(activity,marginsarray[3]));
        textView.setLayoutParams(paramss);
    }

    //Customization of text view with background
    public void CustomizeWithBgTextview(TextView textView, float textsize, int textcolor, String title, int drawable, String contenttype, int[] marginsarray) {
        //Assigning text to text view
        textView.setText(title);
        //Assigning text size to text view
        textView.setTextSize(textsize);
        //Assigning text color to text view
        textView.setTextColor(activity.getResources().getColor(textcolor));
        //Assigning background to text view
        textView.setBackgroundDrawable(activity.getResources().getDrawable(drawable));

        //Assigning content values to text view
        //View width @ view gravity @view font type
        String[] contentvalues=contenttype.split("@");
        if(contentvalues[2].equalsIgnoreCase("Bold")&&contentvalues[3].equalsIgnoreCase("1")){
            textView.setTypeface(GetFont(bold_fonttype1,activity));
        }else if(contentvalues[2].equalsIgnoreCase("Normal")&&contentvalues[3].equalsIgnoreCase("1")){
            textView.setTypeface(GetFont(regular_fonttype1,activity));
        }else if(contentvalues[2].equalsIgnoreCase("SemiBold")&&contentvalues[3].equalsIgnoreCase("1")){
            textView.setTypeface(GetFont(semibold_fonttype1,activity));
        }else  if(contentvalues[2].equalsIgnoreCase("Bold")&&contentvalues[3].equalsIgnoreCase("2")){
            textView.setTypeface(GetFont(bold_fonttype2,activity));
        }else if(contentvalues[2].equalsIgnoreCase("Normal")&&contentvalues[3].equalsIgnoreCase("2")){
            textView.setTypeface(GetFont(regular_fonttype2,activity));
        }else if(contentvalues[2].equalsIgnoreCase("Normal")&&contentvalues[3].equalsIgnoreCase("3")){
            textView.setTypeface(GetFont(regular_fonttype3,activity));
        }else{
            textView.setTypeface(GetFont(regular_fonttype1,activity));
        }
        if(contentvalues[0].equalsIgnoreCase("Match")){
            params = new LinearLayout.LayoutParams(Constants.matchParent, Constants.wrapContent);
        }else{
            params = new LinearLayout.LayoutParams(Constants.wrapContent, Constants.wrapContent);
        }

        if(contentvalues[1].equalsIgnoreCase("Center")){
            params.gravity = Constants.center;
        }else  if(contentvalues[1].equalsIgnoreCase("Right")){
            params.gravity = Constants.right;
        }else  if(contentvalues[1].equalsIgnoreCase("Top")){
            params.gravity = Constants.top;
        }else  if(contentvalues[1].equalsIgnoreCase("Left")){
            params.gravity = Constants.left;
        }else  if(contentvalues[1].equalsIgnoreCase("Bottom")){
            params.gravity = Constants.bottom;
        }else{
            params.gravity = Constants.left;
        }
        //left,top,right,bottom
        //Assigning margins to textview
        params.setMargins(StoredObjects.pxFromDp(activity,marginsarray[0]),StoredObjects.pxFromDp(activity,marginsarray[1]),StoredObjects.pxFromDp(activity,marginsarray[2]),StoredObjects.pxFromDp(activity,marginsarray[3]));
        textView.setLayoutParams(params);
        textView.requestLayout();
    }


    //Customization of Button view
    public void CustomizeButton(Button button, float buttontextsize, int buttontextcolor, String title, int button_bg, String contenttype,int[] sizesarray, int[] marginsarray) {
        //Assigning size to button
        button.setTextSize(buttontextsize);
        //Assigning text color to button
        button.setTextColor(activity.getResources().getColor(buttontextcolor));
        //Assigning text to button
        button.setText(title);
        //Assigning background to button
        button.setBackgroundDrawable(activity.getResources().getDrawable(button_bg));

        //Assigning content values to button
        //View width @ view gravity @view font type
        String[] contentvalues=contenttype.split("@");


        if(contentvalues[2].equalsIgnoreCase("Bold")&&contentvalues[3].equalsIgnoreCase("1")){
            button.setTypeface(GetFont(bold_fonttype1,activity));
        }else if(contentvalues[2].equalsIgnoreCase("Normal")&&contentvalues[3].equalsIgnoreCase("1")){
            button.setTypeface(GetFont(regular_fonttype1,activity));
        }else if(contentvalues[2].equalsIgnoreCase("SemiBold")&&contentvalues[3].equalsIgnoreCase("1")){
            button.setTypeface(GetFont(semibold_fonttype1,activity));
        }else  if(contentvalues[2].equalsIgnoreCase("Bold")&&contentvalues[3].equalsIgnoreCase("2")){
            button.setTypeface(GetFont(bold_fonttype2,activity));
        }else if(contentvalues[2].equalsIgnoreCase("Normal")&&contentvalues[3].equalsIgnoreCase("2")){
            button.setTypeface(GetFont(regular_fonttype2,activity));
        }else if(contentvalues[2].equalsIgnoreCase("Normal")&&contentvalues[3].equalsIgnoreCase("3")){
            button.setTypeface(GetFont(regular_fonttype3,activity));
        }else{
            button.setTypeface(GetFont(regular_fonttype1,activity));
        }
        if(contentvalues[0].equalsIgnoreCase("Match")){
            params = new LinearLayout.LayoutParams(Constants.matchParent, StoredObjects.pxFromDp(activity,sizesarray[1]));
        }else{
            params = new LinearLayout.LayoutParams(StoredObjects.pxFromDp(activity,sizesarray[0]), StoredObjects.pxFromDp(activity,sizesarray[1]));
        }
        if(contentvalues[1].equalsIgnoreCase("Center")){
            params.gravity = Constants.center;
        }else  if(contentvalues[1].equalsIgnoreCase("Right")){
            params.gravity = Constants.right;
        }else  if(contentvalues[1].equalsIgnoreCase("Top")){
            params.gravity = Constants.top;
        }else  if(contentvalues[1].equalsIgnoreCase("Left")){
            params.gravity = Constants.left;
        }else  if(contentvalues[1].equalsIgnoreCase("Bottom")){
            params.gravity = Constants.bottom;
        }else{
            params.gravity = Constants.left;
        }
        //left,top,right,bottom
        //Assigning margins to button
        params.setMargins(StoredObjects.pxFromDp(activity,marginsarray[0]),StoredObjects.pxFromDp(activity,marginsarray[1]),StoredObjects.pxFromDp(activity,marginsarray[2]),StoredObjects.pxFromDp(activity,marginsarray[3]));

        button.setLayoutParams(params);
        button.requestLayout();
    }

    //Customization of Edittext view
    public void CustomizeEditview(EditText editText, float editviewtextsize,String title, int background_bg, boolean editstatus, String contenttype, int[] marginsarray) {
        //Assigning size to edit view
        editText.setTextSize(editviewtextsize);
        //Assigning hint to edit view
        editText.setHint(title);
        //Assigning background to edit view
        if(background_bg==0){

        }else{
            editText.setBackgroundDrawable(activity.getResources().getDrawable(background_bg));
        }

        editText.setEnabled(editstatus);
        //Assigning content values to edit view
        //View width @ view gravity @view font type
        String[] contentvalues=contenttype.split("@");


        if(contentvalues[2].equalsIgnoreCase("Bold")&&contentvalues[3].equalsIgnoreCase("1")){
            editText.setTypeface(GetFont(bold_fonttype1,activity));
        }else if(contentvalues[2].equalsIgnoreCase("Normal")&&contentvalues[3].equalsIgnoreCase("1")){
            editText.setTypeface(GetFont(regular_fonttype1,activity));
        }else if(contentvalues[2].equalsIgnoreCase("SemiBold")&&contentvalues[3].equalsIgnoreCase("1")){
            editText.setTypeface(GetFont(semibold_fonttype1,activity));
        }else  if(contentvalues[2].equalsIgnoreCase("Bold")&&contentvalues[3].equalsIgnoreCase("2")){
            editText.setTypeface(GetFont(bold_fonttype2,activity));
        }else if(contentvalues[2].equalsIgnoreCase("Normal")&&contentvalues[3].equalsIgnoreCase("2")){
            editText.setTypeface(GetFont(regular_fonttype2,activity));
        }else if(contentvalues[2].equalsIgnoreCase("Normal")&&contentvalues[3].equalsIgnoreCase("3")){
            editText.setTypeface(GetFont(regular_fonttype3,activity));
        }else{
            editText.setTypeface(GetFont(regular_fonttype1,activity));
        }
        if(contentvalues[0].equalsIgnoreCase("Match")){
            params = new LinearLayout.LayoutParams(Constants.matchParent, Constants.wrapContent);
            //paramss = new FrameLayout.LayoutParams(Constants.matchParent, Constants.wrapContent);
        }else{
            params = new LinearLayout.LayoutParams(Constants.wrapContent, Constants.wrapContent);
            //paramss = new FrameLayout.LayoutParams(Constants.wrapContent, Constants.wrapContent);
        }

        if(contentvalues[1].equalsIgnoreCase("Center")){
            params.gravity = Constants.center;
        }else  if(contentvalues[1].equalsIgnoreCase("Right")){
            params.gravity = Constants.right;
        }else  if(contentvalues[1].equalsIgnoreCase("Top")){
            params.gravity = Constants.top;
        }else  if(contentvalues[1].equalsIgnoreCase("Left")){
            params.gravity = Constants.left;
        }else  if(contentvalues[1].equalsIgnoreCase("Bottom")){
            params.gravity = Constants.bottom;
        }else{
            params.gravity = Constants.left;
        }


        //left,top,right,bottom
        //Assigning margins to edit view
        params.setMargins(StoredObjects.pxFromDp(activity,marginsarray[0]),StoredObjects.pxFromDp(activity,marginsarray[1]),StoredObjects.pxFromDp(activity,marginsarray[2]),StoredObjects.pxFromDp(activity,marginsarray[3]));

        editText.setLayoutParams(params);
        editText.requestLayout();

    }


    //Customization of Edittext view
    public void CustomizeMultilineEditview(EditText editText, float editviewtextsize, String title, int background_bg, boolean editstatus, boolean single_line, String contenttype, int[] marginsarray,int numoflines) {
        //Assigning size to edit view
        editText.setTextSize(editviewtextsize);
        //Assigning hint to edit view
        editText.setHint(title);
        //Assigning background to edit view
        editText.setBackgroundDrawable(activity.getResources().getDrawable(background_bg));
        editText.setSingleLine(single_line);
        if(editstatus==false){
            editText.setFocusable(editstatus);
            editText.setFocusableInTouchMode(editstatus);
        }
        editText.setEnabled(editstatus);
        //Assigning content values to edit view
        //View width @ view gravity @view font type
        String[] contentvalues=contenttype.split("@");

        if(contentvalues[2].equalsIgnoreCase("Bold")&&contentvalues[3].equalsIgnoreCase("1")){
            editText.setTypeface(GetFont(bold_fonttype1,activity));
        }else if(contentvalues[2].equalsIgnoreCase("Normal")&&contentvalues[3].equalsIgnoreCase("1")){
            editText.setTypeface(GetFont(regular_fonttype1,activity));
        }else if(contentvalues[2].equalsIgnoreCase("SemiBold")&&contentvalues[3].equalsIgnoreCase("1")){
            editText.setTypeface(GetFont(semibold_fonttype1,activity));
        }else  if(contentvalues[2].equalsIgnoreCase("Bold")&&contentvalues[3].equalsIgnoreCase("2")){
            editText.setTypeface(GetFont(bold_fonttype2,activity));
        }else if(contentvalues[2].equalsIgnoreCase("Normal")&&contentvalues[3].equalsIgnoreCase("2")){
            editText.setTypeface(GetFont(regular_fonttype2,activity));
        }else if(contentvalues[2].equalsIgnoreCase("Normal")&&contentvalues[3].equalsIgnoreCase("3")){
            editText.setTypeface(GetFont(regular_fonttype3,activity));
        }else{
            editText.setTypeface(GetFont(regular_fonttype1,activity));
        }

        if(contentvalues[0].equalsIgnoreCase("Match")){
            params = new LinearLayout.LayoutParams(Constants.matchParent, Constants.wrapContent);
        }else{
            params = new LinearLayout.LayoutParams(Constants.wrapContent, Constants.wrapContent);
        }

        editText.setLines(numoflines);

        //left,top,right,bottom
        //Assigning margins to edit view
        params.setMargins(StoredObjects.pxFromDp(activity,marginsarray[0]),StoredObjects.pxFromDp(activity,marginsarray[1]),StoredObjects.pxFromDp(activity,marginsarray[2]),StoredObjects.pxFromDp(activity,marginsarray[3]));

        editText.setLayoutParams(params);
        editText.requestLayout();

    }

    //Customization of Image view
    public void CustomizeImageview(ImageView imageView, int[] sizesarray, int background_bg, int[] marginsarray) {
        //Assigning size to image view
        //Assigning background to image view
        imageView.setImageResource(background_bg);

        //left,top,right,bottom
        //Assigning margins to imageview
        if(sizesarray[0]==Constants.matchParent||sizesarray[0]==Constants.wrapContent){
            params= new LinearLayout.LayoutParams(sizesarray[0], StoredObjects.pxFromDp(activity,sizesarray[1]));

        }else{
            params= new LinearLayout.LayoutParams(StoredObjects.pxFromDp(activity,sizesarray[0]), StoredObjects.pxFromDp(activity,sizesarray[1]));

        }
        params.setMargins(StoredObjects.pxFromDp(activity,marginsarray[0]),StoredObjects.pxFromDp(activity,marginsarray[1]),StoredObjects.pxFromDp(activity,marginsarray[2]),StoredObjects.pxFromDp(activity,marginsarray[3]));
        imageView.setLayoutParams(params);
    }

    //Customization of Image view
    public void CustomizeImageviewBackground(ImageView imageView, int[] sizesarray, int background_bg, int[] marginsarray) {
        //Assigning size to image view
        //Assigning margins to imageview
        if(sizesarray[0]==Constants.matchParent||sizesarray[0]==Constants.wrapContent){
            params= new LinearLayout.LayoutParams(sizesarray[0], StoredObjects.pxFromDp(activity,sizesarray[1]));

        }else{
            params= new LinearLayout.LayoutParams(StoredObjects.pxFromDp(activity,sizesarray[0]), StoredObjects.pxFromDp(activity,sizesarray[1]));

        }  //Assigning background to image view
        imageView.setBackgroundDrawable(activity.getResources().getDrawable(background_bg));

        //left,top,right,bottom
        //Assigning margins to imageview
        params.setMargins(StoredObjects.pxFromDp(activity,marginsarray[0]),StoredObjects.pxFromDp(activity,marginsarray[1]),StoredObjects.pxFromDp(activity,marginsarray[2]),StoredObjects.pxFromDp(activity,marginsarray[3]));
        imageView.setLayoutParams(params);
    }

    //Customization of Image view
    public void CustomizeBottomImageview(ImageView imageView, int[] sizesarray, int[] marginsarray) {
        //Assigning size to image view
        //Assigning background to image view
        //imageView.setImageResource(background_bg);

        //left,top,right,bottom
        //Assigning margins to imageview
        if(sizesarray[0]==Constants.matchParent||sizesarray[0]==Constants.wrapContent){
            params= new LinearLayout.LayoutParams(sizesarray[0], StoredObjects.pxFromDp(activity,sizesarray[1]));

        }else{
            params= new LinearLayout.LayoutParams(StoredObjects.pxFromDp(activity,sizesarray[0]), StoredObjects.pxFromDp(activity,sizesarray[1]));

        }
        params.setMargins(StoredObjects.pxFromDp(activity,marginsarray[0]),StoredObjects.pxFromDp(activity,marginsarray[1]),StoredObjects.pxFromDp(activity,marginsarray[2]),StoredObjects.pxFromDp(activity,marginsarray[3]));
        imageView.setLayoutParams(params);
    }







    //Get Font
    public  Typeface GetFont(String font_type,Activity activity){

        Typeface fonttype= Typeface.createFromAsset(activity.getAssets(),  font_type);
        return fonttype;
    }

    //Apply Tint
    public  void ApplyTint(ImageView imageView,int tintcolor){

        imageView.setColorFilter(ContextCompat.getColor(activity, tintcolor), android.graphics.PorterDuff.Mode.SRC_IN);
    }
    //Apply Tint
    public  void SetPasswordHint(EditText editText){

        editText.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    public void SetHeight(RecyclerView recyclerView,int numberofitems,int itemHeight){
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height = itemHeight * numberofitems;
        recyclerView.requestLayout();
    }

}
