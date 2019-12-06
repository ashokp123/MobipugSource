package com.o3sa.mobipugapp.uicomponents;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;


/**
 * Created by Kiran on 15-09-2018.
 */

public class CustomViews extends AppCompatActivity {

    TextView title1,title2,title3;
    Button button1,button2,button3;
    EditText editview1,editview2;
    ImageView imageview1,imageview2;
    StoredObjects objects;
    ViewPager viewpager;
    ViewpagerAdapter viewpageradapter;
    LinearLayout viewPagerCountDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customviewslay);
        objects=new StoredObjects(CustomViews.this);

        intialization();
        assigndata();

    }


    private void intialization() {

        title1 =(TextView) findViewById(R.id.textview1);
        title2 = (TextView) findViewById(R.id.textview2);
        title3=  (TextView)findViewById(R.id.textview3);

        button1 =  (Button) findViewById(R.id.button1);
        button2 =  (Button)  findViewById(R.id.button2);
        button3=   (Button) findViewById(R.id.button3);

        editview1 =(EditText) findViewById(R.id.editview1);
        editview2= (EditText) findViewById(R.id.editview2);
        imageview1=(ImageView) findViewById(R.id.imageview1);
        imageview2=(ImageView) findViewById(R.id.imageview2);
        viewpager=(ViewPager) findViewById(R.id.viewpager);

        /*viewPagerCountDots=(LinearLayout) findViewById(R.id.viewPagerCountDots);
        Customviewpager customviewpager=new Customviewpager();
        customviewpager.Customviewpager(CustomViews.this,viewpager,viewpageradapter,StoredObjects.datalist,viewPagerCountDots,"viewpager_img",R.layout.viewpagerlistitem);*/

    }
    private void assigndata() {

        String currentdate=objects.GetCurrentDate(Constants.DATEFORMAT2);
        String currentday=objects.GetCurrentDate(Constants.DATEFORMAT10);
        String converteddate=objects.ConvertDate(currentdate, Constants.DATEFORMAT3, Constants.DATEFORMAT4);

        BasicComponents components=new BasicComponents(CustomViews.this);
        //Assigning alignments to textview
        components.CustomizeTextview(title1,Constants.Normal,R.color.black,getApplicationContext().getResources().getString(R.string.title1),Constants.WrapCenterBold+Constants.Roboto, new int[]{30, 10, 10, 10});
        components.CustomizeTextview(title2,Constants.Normal,R.color.black,getApplicationContext().getResources().getString(R.string.title2),Constants.WrapRightNormal +Constants.Roboto, new int[]{20, 10, 10, 10});

        //Assigning alignments and background to textview
        components.CustomizeWithBgTextview(title3,Constants.Normal,R.color.black,getApplicationContext().getResources().getString(R.string.title3),R.drawable.editextview_bg,Constants.MatchCenterBold+Constants.Roboto, new int[]{10, 10, 10, 10});

        components.CustomizeButton(button1,Constants.Normal,R.color.black,getApplicationContext().getResources().getString(R.string.title4),R.drawable.editextview_bg,Constants.MatchCenterBold+Constants.Roboto, new int[]{0,100}, new int[]{20, 10, 10, 10});
        components.CustomizeButton(button2,Constants.Normal,R.color.black,getApplicationContext().getResources().getString(R.string.title5),R.drawable.editextview_bg,Constants.WrapCenterBold+Constants.Roboto, new int[]{Constants.wrapContent,50}, new int[]{20, 10, 10, 10});
        components.CustomizeButton(button3,Constants.Normal,R.color.black,getApplicationContext().getResources().getString(R.string.title6),R.drawable.editextview_bg,Constants.WrapCenterBold+Constants.Roboto,  new int[]{Constants.wrapContent,30}, new int[]{20, 10, 10, 10});

        components.CustomizeEditview(editview1,Constants.Normal,getApplicationContext().getResources().getString(R.string.title1),0,false,Constants.WrapCenterBold+Constants.Roboto, new int[]{20, 10, 10, 10});
        //components.CustomizeMultilineEditview(editview2,Constants.Normal,R.color.black,R.color.gray,getApplicationContext().getResources().getString(R.string.title2),R.drawable.editextview_bg,true,Constants.MatchCenterBold+Constants.Roboto, new int[]{20, 10, 10, 10},10);

        components.CustomizeImageview(imageview1, new int[]{Constants.wrapContent,Constants.wrapContent},R.drawable.ic_launcher, new int[]{20, 10, 10, 10});
        components.CustomizeImageview(imageview2, new int[]{50,50},R.drawable.ic_launcher, new int[]{20, 10, 10, 10});

    }

}

