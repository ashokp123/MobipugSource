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
 * Created by Kiran on 22-09-2018.
 */

public class Customviewpager {

    public  TextView[] dots;
    public  Runnable animateViewPager;
    public  Handler handler,handler1;
    public  boolean stopSliding = false;

    ArrayList<HashMap<String, String>> images_list=new ArrayList<>();

    public   void Customviewpager(final Activity activity, final ViewPager viewpager, ViewpagerAdapter viewpageradapter, ArrayList<DumpData> datalist, ArrayList<HashMap<String, String>> prdct_details_list, LinearLayout layout, String type, int viewpagerlistitem) {// ArrayList<HashMap<String, String>> datalist

       handler = new Handler();
       handler1 = new Handler();

        viewpageradapter=new ViewpagerAdapter(activity, datalist, prdct_details_list, type, viewpagerlistitem);
        viewpager.setAdapter(viewpageradapter);

        viewpager.setCurrentItem(0);

        if(type.equalsIgnoreCase("viewpager_img")){

            try {
                viewpager.setCurrentItem(0);

                viewpager.setOnPageChangeListener( new ViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageSelected(int position) {
                        try {

                            for (int i = 0; i < 3; i++) {
                                dots[i].setTextColor(activity.getResources().getColor(R.color.white));
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
                                    if (viewpager.getCurrentItem() == 3 - 1) {
                                        viewpager.setCurrentItem(0);
                                    } else {
                                        viewpager.setCurrentItem(
                                                viewpager.getCurrentItem() + 1, true);
                                    }
                                    //handler.postDelayed(animateViewPager, Constants.ANIM_VIEWPAGER_DELAY);
                                }
                            }
                        };
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }
                //handler.postDelayed(animateViewPager,Constants.ANIM_VIEWPAGER_DELAY);

            } catch (Exception e) {
                // TODO: handle exception
            }

            setUiPageViewController(layout,3,activity,type);

        }

        if(type.equalsIgnoreCase("hmpg_viewpager")){

            try {
                viewpager.setCurrentItem(0);
                viewpager.setOnPageChangeListener( new ViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageSelected(int position) {
                        try {

                            for (int i = 0; i < 5; i++) {
                                dots[i].setTextColor(activity.getResources().getColor(R.color.white));
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
                                    if (viewpager.getCurrentItem() == 5 - 1) {
                                        viewpager.setCurrentItem(0);
                                    } else {
                                        viewpager.setCurrentItem(
                                                viewpager.getCurrentItem() + 1, true);
                                    }
                                    handler1.postDelayed(animateViewPager, Constants.ANIM_VIEWPAGER_DELAY);
                                }
                            }
                        };
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }
                handler1.postDelayed(animateViewPager,Constants.ANIM_VIEWPAGER_DELAY);

            } catch (Exception e) {
                // TODO: handle exception
            }

            setUiPageViewController1(layout,5,activity,type);

        }if(type.equalsIgnoreCase("prdct_viewpager")){

            if(!prdct_details_list.get(viewpagerlistitem).get("images").equalsIgnoreCase("[]")){
                try {
                    images_list = JsonParsing.GetInnerJsonData(prdct_details_list.get(viewpagerlistitem).get("images"));
                }catch (Exception e){
                }
            }

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

    public  void setUiPageViewController(LinearLayout layout, int size,Activity activity,String type) {

        if (type.equalsIgnoreCase("viewpager_img")){

            try {

                dots = new TextView[size];

                for (int i = 0; i < size; i++) {
                    dots[i] = new TextView(activity);
                    dots[i].setText(Html.fromHtml("&#8226;"));
                    dots[i].setTextSize(50);
                    dots[i].setTextColor(activity.getResources().getColor(R.color.white));

                    layout.addView(dots[i]);
                }

                dots[0].setTextColor(activity.getResources().getColor(R.color.blue_color));

            } catch (Exception e) {
                // TODO: handle exception
            }

        }

    }

    public  void setUiPageViewController1(LinearLayout layout, int size,Activity activity,String type) {

        if (type.equalsIgnoreCase("hmpg_viewpager")){

            try {

                dots = new TextView[size];

                for (int i = 0; i < size; i++) {
                    dots[i] = new TextView(activity);
                    dots[i].setText(Html.fromHtml("&#8226;"));
                    dots[i].setTextSize(30);
                    dots[i].setTextColor(activity.getResources().getColor(R.color.white));

                    layout.addView(dots[i]);
                }

                dots[0].setTextColor(activity.getResources().getColor(R.color.blue_color));

            } catch (Exception e) {
                // TODO: handle exception
            }

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
