package com.o3sa.mobipugapp.uicomponents;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.dumpdata.DumpData;
import com.o3sa.mobipugapp.servicesparsing.JsonParsing;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by android-4 on 11/10/2018.
 */

public class CustomviewpagerNew {

    public  TextView[] dots;
    public  Runnable animateViewPager;
    public Handler handler,handler1;
    public  boolean stopSliding = false;

    public ArrayList<HashMap<String, String>> images_list;

    public   void CustomviewpagerNew(final Activity activity, final ViewPager viewpager, ViewpagerAdapterNew viewpageradapter, ArrayList<HashMap<String, String>> data_list, LinearLayout layout, String type, int viewpagerlistitem) {

        handler = new Handler();
        handler1 = new Handler();
        images_list = data_list;

        viewpageradapter = new ViewpagerAdapterNew(activity,data_list, type, viewpagerlistitem);
        viewpager.setAdapter(viewpageradapter);

        //viewpager.setCurrentItem(0);

        if(type.equalsIgnoreCase("prdct_viewpager")){

            try {
                viewpager.setCurrentItem(0);
                viewpager.setOnPageChangeListener( new ViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageSelected(int position) {
                        try {

                            for (int i = 0; i < images_list.size(); i++) {
                                dots[i].setTextColor(activity.getResources().getColor(R.color.thik_grey));
                            }
                            dots[position].setTextColor(activity.getResources().getColor(R.color.blue_color));

                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                    }

                    @Override
                    public void onPageScrolled(int arg0, float arg1, int arg2) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int arg0) {

                    }

                });
                try {

                    if(activity!=null){

                        animateViewPager = new Runnable() {
                            public void run() {
                                if (!stopSliding) {
                                    if (viewpager.getCurrentItem() == images_list.size() - 1) {
                                        viewpager.setCurrentItem(0);
                                    } else {
                                        viewpager.setCurrentItem(
                                                viewpager.getCurrentItem() + 1, true);
                                    }
                                    handler.postDelayed(animateViewPager, Constants.ANIM_VIEWPAGER_DELAY);
                                }
                            }
                        };
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }
                handler.postDelayed(animateViewPager,Constants.ANIM_VIEWPAGER_DELAY);

            } catch (Exception e) {
                // TODO: handle exception
            }

            setUiPageViewController2(layout, images_list.size(), activity, type);

        }

    }

    public  void setUiPageViewController2(LinearLayout layout, int size,Activity activity,String type) {

        if (type.equalsIgnoreCase("prdct_viewpager")){

            try {

                dots = new TextView[size];

                for (int i = 0; i < size; i++) {
                    dots[i] = new TextView(activity);
                    dots[i].setText(Html.fromHtml("&#8226;"));
                    dots[i].setTextSize(30);
                    dots[i].setTextColor(activity.getResources().getColor(R.color.thik_grey));

                    layout.addView(dots[i]);
                }

                dots[0].setTextColor(activity.getResources().getColor(R.color.blue_color));

            } catch (Exception e) {
                // TODO: handle exception
            }

        }

    }

}
