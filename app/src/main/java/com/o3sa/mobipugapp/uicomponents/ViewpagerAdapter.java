package com.o3sa.mobipugapp.uicomponents;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.dumpdata.DumpData;
import com.o3sa.mobipugapp.servicesparsing.JsonParsing;
import com.o3sa.mobipugapp.storedobjects.StoredUrls;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kiran on 22-09-2018.
 */

public class ViewpagerAdapter extends PagerAdapter {

    private Activity mContext;
    //ArrayList<HashMap<String, String>> datalist;
    ArrayList<HashMap<String, String>> prdct_details_list;
    ArrayList<DumpData> datalist;
    BasicComponents object;
    String formtype;
    int viewpager_listitemm;

    ArrayList<HashMap<String, String>> images_list=new ArrayList<>();
    ArrayList<HashMap<String, String>> attrbuts_list=new ArrayList<>();
    ArrayList<HashMap<String, String>> reviews_list=new ArrayList<>();

    public ViewpagerAdapter(Activity context, ArrayList<DumpData> data_list, ArrayList<HashMap<String, String>> prdct_details_list, String type, int viewpagerlistitem) {//ArrayList<HashMap<String, String>> data_list

        this.mContext = context;
        this.datalist = data_list;
        this.prdct_details_list = prdct_details_list;
        this.formtype = type;
        this.viewpager_listitemm = viewpagerlistitem;
        this.object=new BasicComponents(context);

    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View convertView=null;
        if(formtype.equalsIgnoreCase("viewpager_img")){
        convertView	= LayoutInflater.from(mContext).inflate(viewpager_listitemm, null);
        ImageView viewpagerimg = (ImageView) convertView.findViewById(R.id.viewpagerimg);
        object.CustomizeImageview(viewpagerimg,new int[]{Constants.matchParent,Constants.matchParent},R.drawable.apple_logo, new int[]{0,0,0,0});
            viewpagerimg.setVisibility(View.GONE);
        collection.addView(convertView,Constants.matchParent, Constants.matchParent);

        }else if(formtype.equalsIgnoreCase("hmpg_viewpager")){
            convertView	= LayoutInflater.from(mContext).inflate(viewpager_listitemm, null);
            ImageView viewpagerimg = (ImageView) convertView.findViewById(R.id.viewpagerimg);
            object.CustomizeImageviewBackground(viewpagerimg,new int[]{Constants.matchParent,250},datalist.get(position).hmpg_pg_imgs, new int[]{0,0,0,0});

            collection.addView(convertView,Constants.matchParent, Constants.matchParent);
        }else if(formtype.equalsIgnoreCase("prdct_viewpager")){
            convertView	= LayoutInflater.from(mContext).inflate(viewpager_listitemm, null);
            ImageView viewpagerimg = (ImageView) convertView.findViewById(R.id.viewpagerimg);

            if(!prdct_details_list.get(viewpager_listitemm).get("images").equalsIgnoreCase("[]")){
                try {
                    images_list = JsonParsing.GetInnerJsonData(prdct_details_list.get(viewpager_listitemm).get("images"));
                }catch (Exception e){
                }
            }

            if(!prdct_details_list.get(viewpager_listitemm).get("attributes").equalsIgnoreCase("[]")){
                try {
                    attrbuts_list = JsonParsing.GetInnerJsonData(prdct_details_list.get(viewpager_listitemm).get("attributes"));
                }catch (Exception e){
                }
            }

            if(!prdct_details_list.get(viewpager_listitemm).get("reviews").equalsIgnoreCase("[]")){
                try {
                    reviews_list = JsonParsing.GetInnerJsonData(prdct_details_list.get(viewpager_listitemm).get("reviews"));
                }catch (Exception e){
                }
            }

            //viewpagerimg.setBackgroundResource(datalist.get(position).prdct_pg_imgs);
            object.CustomizeImageviewBackground(viewpagerimg,new int[]{Constants.matchParent,200},R.drawable.apple_logo, new int[]{0,0,0,0});

            if(images_list.size()>0){
                Glide.with(mContext)
                        .load(Uri.parse(StoredUrls.MainUrl+images_list.get(0).get("image"))) // add your image url
                        //.transform(new StoredObjects.CircleTransform(activity)) // applying the image transformer
                        .override(Constants.matchParent, 200)
                        .centerCrop() // scale to fill the ImageView and crop any extra
                        .fitCenter()  // scale to fit entire image within ImageView
                        .placeholder(R.drawable.imagenotfound)
                        .into(viewpagerimg);
            }else{
                viewpagerimg.setBackgroundResource(R.drawable.imagenotfound);
            }

            collection.addView(convertView,Constants.matchParent, Constants.matchParent);
        }

        return convertView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {

        //return 3;
        return datalist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }



}

