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
 * Created by android-4 on 11/10/2018.
 */

public class ViewpagerAdapterNew extends PagerAdapter {

    private Activity mContext;
    ArrayList<HashMap<String, String>> datalist;
    BasicComponents object;
    String formtype;
    int viewpager_listitemm;

    public ViewpagerAdapterNew(Activity context, ArrayList<HashMap<String, String>> details_list, String type, int viewpagerlistitem) {

        this.mContext = context;
        this.datalist = details_list;
        this.formtype = type;
        this.viewpager_listitemm = viewpagerlistitem;
        this.object=new BasicComponents(context);

    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View convertView=null;

        if(formtype.equalsIgnoreCase("prdct_viewpager")){
            convertView	= LayoutInflater.from(mContext).inflate(viewpager_listitemm, null);
            ImageView viewpagerimg = (ImageView) convertView.findViewById(R.id.viewpagerimg);

                Glide.with(mContext)
                        .load(Uri.parse(StoredUrls.MainUrl+datalist.get(position).get("image"))) // add your image url
                        //.transform(new StoredObjects.CircleTransform(activity)) // applying the image transformer
                        //.override(Constants.matchParent, 200)
                        .centerCrop() // scale to fill the ImageView and crop any extra
                        .fitCenter()  // scale to fit entire image within ImageView
                        .placeholder(R.drawable.imagenotfound)
                        .into(viewpagerimg);

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
