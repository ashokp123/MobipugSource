package com.o3sa.mobipugapp.sidemenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;

import java.util.ArrayList;


@SuppressLint("ResourceAsColor")
public class NavDrawerListAdapter extends BaseAdapter {

	private Activity context;
	private ArrayList<NavDrawerItems> navDrawerItems;
	public static TextView txtTitle;
	public static LinearLayout navigation_lay;
	public static ImageView icon;
	BasicComponents components;
	public NavDrawerListAdapter(Activity context, ArrayList<NavDrawerItems> navDrawerItems){

		this.context = context;
		this.navDrawerItems = navDrawerItems;
		components = new BasicComponents(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawablelist_items, null);
        }
		 icon = (ImageView) convertView.findViewById(R.id.icon);
		 txtTitle=(TextView) convertView.findViewById(R.id.title);
		 navigation_lay = (LinearLayout) convertView.findViewById(R.id.navigation_lay);

		 icon.setImageResource(navDrawerItems.get(position).getIcon());


		if(position== StoredObjects.count){
	        navigation_lay.setBackgroundColor(context.getResources().getColor(R.color.menu_header_bg_clr));
			icon.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
			components.CustomizeTextview(txtTitle, Constants.XXNormal,R.color.white,navDrawerItems.get(position).getTitle(),Constants.WrapLeftNormal+Constants.Gibson, new int[]{0,0,0,0});
		}else{
			 navigation_lay.setBackgroundColor(context.getResources().getColor(R.color.blue_color));
			 icon.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
			components.CustomizeTextview(txtTitle, Constants.XXNormal,R.color.white,navDrawerItems.get(position).getTitle(),Constants.WrapLeftNormal+Constants.Gibson, new int[]{0,0,0,0});

	     }

	     if(StoredObjects.UserType.equalsIgnoreCase("Vendor")){
			 if (position==6||position==9){
				 navigation_lay.setVisibility(View.INVISIBLE);
				 navigation_lay.setClickable(false);
				 navigation_lay.setFocusable(false);
			 }
			 if (position==7||position==8||position==10){
				 icon.setVisibility(View.GONE);
				 components.CustomizeTextview(txtTitle, Constants.XXNormal,R.color.side_memu_nrml_txt_clr,navDrawerItems.get(position).getTitle(),Constants.WrapLeftNormal+Constants.Gibson, new int[]{0,0,0,0});

			 }
		 }else{
			 if (position==4||position==7){
				 navigation_lay.setVisibility(View.INVISIBLE);
				 navigation_lay.setClickable(false);
				 navigation_lay.setFocusable(false);
			 }
			 if (position==5||position==6||position==8){
				 icon.setVisibility(View.GONE);
				 components.CustomizeTextview(txtTitle, Constants.XXNormal,R.color.side_memu_nrml_txt_clr,navDrawerItems.get(position).getTitle(),Constants.WrapLeftNormal+Constants.Gibson, new int[]{0,0,0,0});

			 }
		 }



		return convertView;
	}


}
